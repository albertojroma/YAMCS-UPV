package gr.spacedot.acubesat.simulator.network;

import gr.spacedot.acubesat.simulator.enums.MessageType;
import gr.spacedot.acubesat.simulator.enums.ServiceType;

public class Message {

    private MessageType messageType; //4 bits

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    private ServiceType serviceType; //4 bits

    public boolean equals(Message otherMessage){
        return this.messageType == otherMessage.messageType && this.serviceType == otherMessage.serviceType;
    }

    @Override
    public String toString(){
        return "MessageType is "+messageType+" and service type is "+serviceType;
    }
}
