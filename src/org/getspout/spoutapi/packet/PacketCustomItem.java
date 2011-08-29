package org.getspout.spoutapi.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketCustomItem implements SpoutPacket {
	
	private int itemId;
	private Integer blockId;
	private String textureURL;
	
	public PacketCustomItem() {
	}
	
	public PacketCustomItem(int itemId, Integer blockId, String textureURL) {
		this.itemId = itemId;
		this.blockId = blockId;
		this.textureURL = textureURL;
	}
	
	@Override
	public int getNumBytes() {
		return 8 + PacketUtil.getNumBytes(textureURL);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		itemId = input.readInt();
		blockId = input.readInt();
		if (blockId == -1) {
			blockId = null;
		}
		textureURL = PacketUtil.readString(input, 256);
		if (textureURL.equals("")) {
			textureURL = null;
		}
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(itemId);
		output.writeInt(blockId == null ? -1 : blockId);
		PacketUtil.writeString(output, textureURL == null ? "" : textureURL);
	}
	
	@Override
	public void run(int PlayerId) {
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PacketCustomItem;
	}
	
	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public void failure(int playerId) {
		
	}


}
