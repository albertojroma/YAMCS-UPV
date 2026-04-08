package gr.spacedot.acubesat.simulator;

import gr.spacedot.acubesat.simulator.network.Packet;
import gr.spacedot.acubesat.simulator.network.PacketManager;
import gr.spacedot.acubesat.simulator.network.PacketType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApplication {

    private static final Spacecraft acubesat = new Spacecraft().init();

    private static final PacketManager packetManager = new PacketManager();

    private static final int TC_PORT = 10025;

    private static final int TM_PORT = 10015;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocketTC = new ServerSocket(TC_PORT);
            ServerSocket serverSocketTM = new ServerSocket(TM_PORT);
            Socket tcSocket = serverSocketTC.accept();
            Socket tmSocket = serverSocketTM.accept();
            System.out.println("Simulator connected successfully");

            while (true) {
                byte[] data = new byte[16];

                if (tcSocket.getInputStream().read(data) == -1)
                    continue;

                printDataArray(data, "Received ");
                Packet request = packetManager.parse(data, PacketType.TC);
                Packet response = packetManager.createResponsePacket(request, acubesat);

                if(response.getMessage()==null)
                    continue;
                byte[] responseData = packetManager.packetToByteArray(response);
                try {
                    tmSocket.getOutputStream().write(responseData);
                    printDataArray(responseData, "Sent ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void printDataArray(byte[] data, String prefix) {
        StringBuilder builder = new StringBuilder();
        for (byte dataByte : data)
            builder.append(dataByte).append(" ");
        System.out.println(prefix + builder);
    }

}


