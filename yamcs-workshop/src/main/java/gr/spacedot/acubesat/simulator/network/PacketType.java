package gr.spacedot.acubesat.simulator.network;

public enum PacketType {
    TM(0),
    TC(1);
    
    private int value;
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    PacketType(final int value) {
    }
    
    public static PacketType of(int x) {
        switch(x) {
            case 0:
                return TM;
            case 1:
                return TC;
        }
        return null;
    }
}
