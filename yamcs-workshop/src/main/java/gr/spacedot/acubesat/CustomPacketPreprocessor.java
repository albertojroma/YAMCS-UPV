package gr.spacedot.acubesat;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.*;

import org.yamcs.TmPacket;
import org.yamcs.YConfiguration;
import org.yamcs.tctm.AbstractPacketPreprocessor;


public class CustomPacketPreprocessor extends AbstractPacketPreprocessor {
    private static final Logger LOGGER = Logger.getLogger(CustomPacketPreprocessor.class.getName());
    private final Map<Integer, AtomicInteger> seqCounts = new HashMap<>();

    // Constructor used when this preprocessor is used without YAML configuration
    public CustomPacketPreprocessor(String yamcsInstance) {
        this(yamcsInstance, YConfiguration.emptyConfig());
    }

    // Constructor used when this preprocessor is used with YAML configuration
    // (packetPreprocessorClassArgs)
    public CustomPacketPreprocessor(String yamcsInstance, YConfiguration config) {
        super(yamcsInstance, config);
    }

    @Override
    public TmPacket process(TmPacket packet) {

        LOGGER.info("Received Packet!");
        byte[] bytes = packet.getPacket();


        if (bytes.length < 4) {
            eventProducer.sendWarning("SHORT_PACKET",
                    "Short packet received, length: " + bytes.length + "; minimum required length is 4 bytes.");
            // If we return null, the packet is dropped.
            return null;
        }

        // Verify continuity for a given APID based on the CCSDS sequence counter

        int packetVersion =  ByteBuffer.wrap(bytes).get(0) & 0x0F;
        int packetType =  (ByteBuffer.wrap(bytes).get(0) >>4) & 0x0F;
        int datalength = ByteBuffer.wrap(bytes).getShort(1) ; // get 2 bytes
        int serviceType = ByteBuffer.wrap(bytes).get(3) &  0x0F;
        int messageType = (ByteBuffer.wrap(bytes).get(3) >>4) & 0x0F;

        StringBuilder stringBuilder = new StringBuilder();
        String newline = System.getProperty("line.separator");
        stringBuilder.append("New packet received!").append(newline);

        stringBuilder.append("Packet Version:").append(packetVersion).append(newline);
        stringBuilder.append("Packet Type:").append(packetType).append(newline);
        stringBuilder.append("ServiceType:").append(serviceType).append(newline);
        stringBuilder.append("MessageType:").append(messageType).append(newline);
        stringBuilder.append("Packet data length:").append(datalength).append(newline);
        stringBuilder.append("----").append(newline);
        LOGGER.info(stringBuilder.toString());

        packet.setGenerationTime(System.currentTimeMillis());
        return packet;
    }

}
