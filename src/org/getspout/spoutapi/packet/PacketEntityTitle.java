package org.getspout.spoutapi.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketEntityTitle implements SpoutPacket{
	public String title;
	public int entityId;
	public PacketEntityTitle() {
		
	}
	
	public PacketEntityTitle(int entityId, String title) {
		this.entityId = entityId;
		this.title = title;
	}
	@Override
	public int getNumBytes() {
		return 4 + PacketUtil.getNumBytes(title);
	}

	@Override
	public void readData(DataInputStream input) throws IOException {
		entityId = input.readInt();
		title = PacketUtil.readString(input);
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(entityId);
		PacketUtil.writeString(output, title);
	}

	@Override
	public void run(int id) {
		
	}

	@Override
	public void failure(int id) {
		
	}
	
	@Override
	public PacketType getPacketType() {
		return PacketType.PacketEntityTitle;
	}
	
	@Override
	public int getVersion() {
		return 0;
	}

}
