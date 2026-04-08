package gr.spacedot.acubesat.simulator.enums;

import static gr.spacedot.acubesat.simulator.enums.ServiceType.MODE;
import static gr.spacedot.acubesat.simulator.enums.ServiceType.CHECK;
import static gr.spacedot.acubesat.simulator.enums.ServiceType.SET_PARAMS;
import static gr.spacedot.acubesat.simulator.enums.ServiceType.REPORT_PARAMS;
import static gr.spacedot.acubesat.simulator.enums.ServiceType.SCIENCE;

public enum MessageType {
    //SERVICE 1
    ARE_YOU_ALIVE(CHECK, 1),
    I_AM_ALIVE(CHECK, 2),

    //SERVICE 2
    REPORT_BATTERY(REPORT_PARAMS, 1),
    BATTERY_REPORT(REPORT_PARAMS, 2),
    REPORT_TEMPERATURE(REPORT_PARAMS, 3),
    TEMPERATURE_REPORT(REPORT_PARAMS, 4),
    REPORT_ANGLES(REPORT_PARAMS, 5),
    ANGLES_REPORT(REPORT_PARAMS, 6),
    REPORT_SOLAR(REPORT_PARAMS, 7),
    SOLAR_REPORT(REPORT_PARAMS, 8),

    //SERVICE 3

    REQUEST_HEATER_REPORT(SET_PARAMS, 1),
    HEATER_SET_REPORT(SET_PARAMS, 2),

    SET_HEATER(SET_PARAMS, 3),
    SET_MAGNETORQUERS(SET_PARAMS, 4),

    //SERVICE 4

    CAPTURE_PHOTO(SCIENCE, 1),
    PHOTO_CAPTURED(SCIENCE, 2),
    REPORT_SCIENCE_TIME(SCIENCE, 3),
    SCIENCE_TIME_REPORT(SCIENCE, 4),

    //SERVICE 5

    REPORT_MODE(MODE, 1),
    MODE_REPORT(MODE, 2),
    SET_MODE(MODE, 3);

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private ServiceType serviceType;

    private int value;

    MessageType(ServiceType serviceType, int value) {
        this.serviceType = serviceType;
        this.value = value;
    }

    public static MessageType of(ServiceType serviceType, int value) {
        for (MessageType message : MessageType.values()) {
            if (message.serviceType == serviceType && message.value == value)
                return message;
        }
        throw new RuntimeException("MessageType for serviceType: " + serviceType.getValue() + " and value " + value + " not found");
    }

    @Override
    public String toString() {
        return " MessageType(" + serviceType.getValue() + "," + value + ")";
    }
}
