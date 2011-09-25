package org.getspout.spoutapi.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketCustomBlockOverride implements SpoutPacket {
	
	private int x;
	private int y;
	private int z;
	private int blockId;
	private int metaData;
	
	public PacketCustomBlockOverride() {
	}
	
	public PacketCustomBlockOverride(int x, int y, int z, Integer blockId, Integer metaData) {
		this.x = x;
		this.y = y;
		this.z = z;
		setBlockId(blockId);
		setMetaData(metaData);
	}
	
	private void setBlockId(Integer blockId) {
		if (blockId == null) {
			this.blockId = -1;
		} else {
			this.blockId = blockId;
		}
	}
	
	private void setMetaData(Integer metaData) {
		if (metaData == null) {
			this.metaData = 0;
		} else {
			this.metaData = metaData;
		}
	}
	
	protected Integer getBlockId() {
		return blockId == -1 ? null : blockId;
	}
	
	protected Integer getMetaData() {
		return blockId == -1 ? null : metaData;
	}
	
	@Override
	public int getNumBytes() {
		return 12;
	}

	@Override
	public void readData(DataInputStream input) throws IOException {
		x = input.readInt();
		y = input.readByte() & 0xFF;
		z = input.readInt();
		setBlockId((int)input.readShort());
		setMetaData(input.readByte() & 0xFF);
	}
	
	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(x);
		output.writeByte(y);
		output.writeInt(z);
		output.writeShort(blockId);
		output.writeByte(metaData);
	}
	

	@Override
	public void run(int PlayerId) {
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PacketCustomBlockOverride;
	}
	
	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public void failure(int playerId) {
	}

	
}
