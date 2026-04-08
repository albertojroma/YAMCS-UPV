package gr.spacedot.acubesat.simulator.network;

import gr.spacedot.acubesat.simulator.Spacecraft;
import gr.spacedot.acubesat.simulator.enums.MessageType;
import gr.spacedot.acubesat.simulator.enums.ServiceType;
import gr.spacedot.acubesat.simulator.enums.SpacecraftMode;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class PacketManager {

    private static final int HEADER_SIZE_TC = 2;

    private static final int HEADER_SIZE_TM = 4;

    public Packet parse(byte[] data, PacketType type) {
        Message message = new Message();
        Packet packet = new Packet();
        if (Objects.requireNonNull(type) == PacketType.TM) {
            packet.setData(Arrays.copyOfRange(data, HEADER_SIZE_TM, data.length));
        } else if (type == PacketType.TC) {
            packet.setData(Arrays.copyOfRange(data, HEADER_SIZE_TC, data.length));
        }

        int currentByte = 0;
        packet.setVersionNumber((data[currentByte] >> 4) & 0xF);
        packet.setPacketType(PacketType.of(data[currentByte] & 0x0F));
        currentByte++;

        ServiceType serviceType = ServiceType.of((data[currentByte] >> 4) & 0xF);
        message.setMessageType(MessageType.of(serviceType, data[currentByte] & 0x0F));
        message.setServiceType(serviceType);

        packet.setMessage(message);
        return packet;
    }

    public Packet createResponsePacket(Packet inputPacket, Spacecraft spacecraft) {
        MessageType messageType = inputPacket.getMessage().getMessageType();
        Message response = new Message();
        Packet packet = new Packet();
        System.out.println("Received "+inputPacket.getMessage());
        switch (messageType) {
            case ARE_YOU_ALIVE: {
                response.setServiceType(ServiceType.CHECK);
                response.setMessageType(MessageType.I_AM_ALIVE);
                packet.setMessage(response);

                break;
            }
            case REPORT_BATTERY: {
                response.setServiceType(ServiceType.REPORT_PARAMS);
                response.setMessageType(MessageType.BATTERY_REPORT);
                packet.setMessage(response);

                ByteBuffer buffer = ByteBuffer.allocate(8);
                buffer.putFloat(spacecraft.getBatteryLevel());
                buffer.putFloat(spacecraft.getBatteryChargeRate());

                packet.setData(buffer.array());
                break;
            }

            case REPORT_TEMPERATURE: {
                response.setServiceType(ServiceType.REPORT_PARAMS);
                response.setMessageType(MessageType.TEMPERATURE_REPORT);
                packet.setMessage(response);

                ByteBuffer buffer = ByteBuffer.allocate(8);
                buffer.putFloat(spacecraft.getTemperature());
                buffer.putFloat(spacecraft.getTemperatureGrad());

                packet.setData(buffer.array());
                break;
            }
            case REPORT_ANGLES: {
                response.setServiceType(ServiceType.REPORT_PARAMS);
                response.setMessageType(MessageType.ANGLES_REPORT);
                packet.setMessage(response);

                ByteBuffer buffer = ByteBuffer.allocate(8);
                buffer.putFloat(spacecraft.getAngleX());
                buffer.putFloat(spacecraft.getAngleY());

                packet.setData(buffer.array());
                break;
            }
            case REPORT_SOLAR: {
                response.setServiceType(ServiceType.REPORT_PARAMS);
                response.setMessageType(MessageType.SOLAR_REPORT);
                packet.setMessage(response);

                ByteBuffer buffer = ByteBuffer.allocate(4);
                buffer.putFloat(spacecraft.calculateSolarPanelOutput());

                packet.setData(buffer.array());
                break;
            }
            case SET_HEATER: {
                response.setServiceType(ServiceType.SET_PARAMS);
                response.setMessageType(MessageType.HEATER_SET_REPORT);
                packet.setMessage(response);

                byte[] dataIn = inputPacket.getData(); //heater percentage
                ByteBuffer bufferIn = ByteBuffer.wrap(dataIn); // wrap the byte array in a ByteBuffer
                float heaterPercentage = bufferIn.getFloat(); // get the float value from the ByteBuffer
                spacecraft.setHeaterPower(heaterPercentage / 100);

                ByteBuffer bufferOut = ByteBuffer.allocate(4);
                bufferOut.putFloat(spacecraft.getHeaterPower());

                packet.setData(bufferOut.array());
                break;
            }
            case REQUEST_HEATER_REPORT: {
                response.setServiceType(ServiceType.SET_PARAMS);
                response.setMessageType(MessageType.HEATER_SET_REPORT);
                packet.setMessage(response);

                ByteBuffer buffer = ByteBuffer.allocate(4);
                buffer.putFloat(spacecraft.getHeaterPower());

                packet.setData(buffer.array());
                break;
            }
            case SET_MAGNETORQUERS: {
                response.setServiceType(ServiceType.REPORT_PARAMS);
                response.setMessageType(MessageType.ANGLES_REPORT);
                packet.setMessage(response);

                byte[] dataIn = inputPacket.getData(); //heater percentage
                ByteBuffer bufferIn = ByteBuffer.wrap(dataIn); // wrap the byte array in a ByteBuffer
                float angleX = bufferIn.getFloat(); // get the float value from the ByteBuffer
                float angleY = bufferIn.getFloat();
                spacecraft.setMagnetorquerValues((int) angleX, (int) angleY);

                ByteBuffer buffer = ByteBuffer.allocate(8);
                buffer.putFloat(spacecraft.getAngleX());
                buffer.putFloat(spacecraft.getAngleY());

                packet.setData(buffer.array());
                break;
            }
            case CAPTURE_PHOTO: {
                if (!spacecraft.capturePhoto())
                    break;
                response.setServiceType(ServiceType.SCIENCE);
                response.setMessageType(MessageType.PHOTO_CAPTURED);
                packet.setMessage(response);
                break;
            }
            case REPORT_SCIENCE_TIME: {
                response.setServiceType(ServiceType.SCIENCE);
                response.setMessageType(MessageType.SCIENCE_TIME_REPORT);
                packet.setMessage(response);
                int seconds = spacecraft.getTimeInScience();
                packet.setData(new byte[]{(byte) seconds});
                break;
            }
            case REPORT_MODE: {
                response.setServiceType(ServiceType.MODE);
                response.setMessageType(MessageType.MODE_REPORT);
                packet.setMessage(response);
                packet.setData(new byte[]{(byte) spacecraft.getMode().getValue()});
                break;
            }
            case SET_MODE: {
                response.setServiceType(ServiceType.MODE);
                response.setMessageType(MessageType.MODE_REPORT);
                packet.setMessage(response);

                ByteBuffer bufferIn = ByteBuffer.wrap(inputPacket.getData()); // wrap the byte array in a ByteBuffer
                int mode = bufferIn.get();

                SpacecraftMode state = SpacecraftMode.of(mode);
                spacecraft.enterMode(state);

                packet.setData(new byte[]{(byte) spacecraft.getMode().getValue()});
                break;
            }
            default: {
                throw new RuntimeException("Unknown response to " + messageType);
            }
        }
        return packet;

    }

    public byte[] packetToByteArray(Packet packet) {
        byte[] packetData = packet.getData();
        int headerSize = 2;
        int packetType = 1;
        if (packet.getPacketType() == PacketType.TM) {
            headerSize = HEADER_SIZE_TM;
            packetType = 0;
        }

        int length = headerSize;
        if (packetData != null)
            length += packetData.length;
        byte[] data = new byte[length];
        data[0] = (byte) (packetType << 4 | 5);// packet version number and packet type
        data[1] = (byte) (length << 8);
        data[2] = (byte) (length);
        data[3] = (byte) (packet.getMessage().getServiceType().getValue() << 4 | packet.getMessage().getMessageType().getValue());

        if (packetData != null)
            System.arraycopy(packetData, 0, data, headerSize, data.length - headerSize);
        return data;
    }

}
