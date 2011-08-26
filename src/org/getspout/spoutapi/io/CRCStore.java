/*
 * This file is part of Spout (http://wiki.getspout.org/).
 * 
 * Spout is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spout is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spoutapi.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.util.config.Configuration;

public class CRCStore {

	private static Configuration urlCRCStore;
	private final static Object urlCRCStoreSync = new Object();

	public static void setConfigFile(Configuration config) {
		synchronized(urlCRCStoreSync) {
			urlCRCStore = config;
			urlCRCStore.load();
		}
	}
	
	private static String encodeURL(String urlString) {
		return urlString.replace(".", "*");
	}

	public static Long getCRC(String urlString, byte[] buffer) {
		if (urlString == null) {
			return null;
		}
		
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException mue) {
			return null;
		}
		
		String key = encodeURL(url.toString());
		String info;
		long modified = 0;
		long crc = 0;

		synchronized(urlCRCStoreSync) {
			info = urlCRCStore.getString(key);
			if (info != null) {

				String[] split = info.split(":");
				if (split.length == 2) {
					try {
						modified = Long.parseLong(split[0]);
						crc = Long.parseLong(split[1]);
					} catch (NumberFormatException nfe) {
					}
				}
			}
		}

		URLConnection urlConn = null;
		InputStream in = null;
		try {
			urlConn = url.openConnection();
		} catch (IOException ioe) {
			return null;
		}
		
		try {
			in = urlConn.getInputStream();

			long urlLastModified = urlConn.getLastModified();
			if (urlLastModified == 0) {
				return 0L;
			} else if (urlLastModified == modified) {
				System.out.println("Cached");
				return crc;
			} else {
				crc = FileUtil.getCRC(in, buffer);
				info = urlLastModified + ":" + crc;
				synchronized(urlCRCStoreSync) {
					urlCRCStore.setProperty(key, info);
					urlCRCStore.save();
				}
				return crc;
			}
		} catch (IOException ioe) {
			crc = FileUtil.getCRC(in, buffer);
			synchronized(urlCRCStoreSync) {
				urlCRCStore.removeProperty(key);
				urlCRCStore.save();
			}
			return crc;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

	}
	
	private static ConcurrentHashMap<String,Thread> CRCDownloads = new ConcurrentHashMap<String,Thread>();
	
	public static class URLCheck extends Thread {
		
		final String url;
		final CRCStoreRunnable runnable;
		final byte[] buffer;
		
		public URLCheck(String url, byte[] buffer, CRCStoreRunnable runnable) {
			this.url = url;
			this.runnable = runnable;
			this.buffer = buffer;
		}

		public void run() {
			
			Thread downloadThread = CRCDownloads.get(url);
			
			if (downloadThread == null) {
				Thread old = CRCDownloads.putIfAbsent(url, this);
				if (old != null) {
					downloadThread = old;
				} else {
					downloadThread = this;
				}
			}
			
			if (downloadThread != this) {
				try {
					downloadThread.join();
				} catch (InterruptedException e) {
				}
			}
			
			Long crc = null;
			crc = CRCStore.getCRC(url, buffer);

			if (crc == null) {
				crc = 0L;
			}
			
			CRCDownloads.remove(url, this);
			
			if (runnable != null) {
				runnable.setCRC(crc);
				runnable.run();
			}
		}
		
	}

	
}
