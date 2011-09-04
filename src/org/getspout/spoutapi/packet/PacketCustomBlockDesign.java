package org.getspout.spoutapi.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.getspout.spoutapi.inventory.SpoutCustomBlockDesign;

public class PacketCustomBlockDesign implements SpoutPacket {

	private Integer blockId;
	private Integer metaData;

	private SpoutCustomBlockDesign design;
	
	public PacketCustomBlockDesign() {
	}
	
	public PacketCustomBlockDesign(Integer blockId, Integer metaData, SpoutCustomBlockDesign design) {
		this.design = design;
		this.blockId = blockId;
		this.metaData = metaData;
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
	
	private Integer getBlockId() {
		return blockId == -1 ? null : blockId;
	}
	
	private Integer getMetaData() {
		return blockId == -1 ? null : metaData;
	}
	
	public int getNumBytes() {
		int designBytes = (design == null) ? SpoutCustomBlockDesign.getResetNumBytes() : design.getNumBytes();
		return 8 + designBytes;
	}
	
	public void readData(DataInputStream input) throws IOException {
		setBlockId(input.readInt());
		setMetaData(input.readInt());
		design = new SpoutCustomBlockDesign();
		design.read(input);
	}
	
	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(blockId == null ? -1 : blockId);
		output.writeInt(metaData == null ? 0 : metaData);
		if (design != null) {
			design.write(output);
		} else {
			SpoutCustomBlockDesign.writeReset(output);
		}
	}
	
	public void run(int id) {
	}

	@Override
	public void failure(int id) {
		
	}

	public PacketType getPacketType() {
		return PacketType.PacketCustomBlockDesign;
	}

	@Override
	public int getVersion() {
		return SpoutCustomBlockDesign.getVersion();
	}

}
