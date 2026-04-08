package gr.spacedot.acubesat.simulator.network;


public class Packet {

    private int versionNumber; // 4bits

    private PacketType packetType = PacketType.TM; // 4bits

    private int length; // 16bits

    private byte[] data;

    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(final int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(final PacketType packetType) {
        this.packetType = packetType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }



}
