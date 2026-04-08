package gr.spacedot.acubesat.simulator.enums;

public enum ServiceType {
    CHECK(1), REPORT_PARAMS(2)  ,SET_PARAMS(3), SCIENCE(4), MODE(5);


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int value;
    ServiceType(int value) {
        this.value = value;
    }

    public static ServiceType of(int value) {
        switch (value) {
            case 1: {
                return CHECK;
            }
            case 2:
                return REPORT_PARAMS;

            case 3:
                return SET_PARAMS;

            case 4:
                return SCIENCE;

            case 5:
                return MODE;

            default:
                throw new RuntimeException("No ServiceType found for value " + value);

        }
    }
}
