package org.getspout.spoutapi.event.inventory;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class InventoryListener extends CustomEventListener implements Listener{
	
	public InventoryListener() {

	}

	public void onInventoryClose(InventoryCloseEvent event) {

	}

	public void onInventoryOpen(InventoryOpenEvent event) {

	}

	public void onInventoryClick(InventoryClickEvent event) {

	}

	public void onInventoryCraft(InventoryCraftEvent event) {

	}

	@Override
	public void onCustomEvent(Event event) {
		if (event instanceof InventoryCloseEvent) {
			onInventoryClose((InventoryCloseEvent)event);
		}
		else if (event instanceof InventoryOpenEvent) {
			onInventoryOpen((InventoryOpenEvent)event);
		}
		else if (event instanceof InventoryClickEvent) {
			onInventoryClick((InventoryClickEvent)event);
		}
		else if (event instanceof InventoryCraftEvent) {
			onInventoryCraft((InventoryCraftEvent)event);
		}
	}
	

}
