package gr.spacedot.acubesat.simulator.enums;

public enum SpacecraftMode {
    NOMINAL_MODE(1),

    SCIENCE_MODE(2),
    SAFE_MODE(3);


    SpacecraftMode(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }

    public static SpacecraftMode of(int value){
        switch (value){
            case 1 : return NOMINAL_MODE;
            case 2 : return SCIENCE_MODE;
            default: return SAFE_MODE;
        }
    }
}
