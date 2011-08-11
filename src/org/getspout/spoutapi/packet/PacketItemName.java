package org.getspout.spoutapi.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketItemName implements SpoutPacket{
	private int id;
	private short data;
	private String name;
	public PacketItemName() {
		
	}
	
	public PacketItemName(int id, short data, String name) {
		this.id = id;
		this.data = data;
		this.name = name;
	}

	@Override
	public int getNumBytes() {
		return 6 + PacketUtil.getNumBytes(name);
	}

	@Override
	public void readData(DataInputStream input) throws IOException {
		id = input.readInt();
		data = input.readShort();
		name = PacketUtil.readString(input);
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(id);
		output.writeShort(data);
		PacketUtil.writeString(output, name);
	}

	@Override
	public void run(int PlayerId) {

	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PacketItemName;
	}
	
	@Override
	public int getVersion() {
		return 0;
	}

}
