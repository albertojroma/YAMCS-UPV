package gr.spacedot.acubesat.simulator;

import gr.spacedot.acubesat.simulator.enums.SpacecraftMode;

import java.util.Random;
import java.util.Objects;

/**
 * Each time we read/update any value, all values should be updated too.
 */
public class Spacecraft {

    private static final long UPDATE_PERIOD = 100; // milliseconds
    private static final float BASE_DISCHARGE_RATE = -0.2f;

    private static final float BASE_DISCHARGE_RATE_SAFE = -0.1f;

    private static final float HEATER_MULTIPLIER = 0.03f;

    private static final float BASE_TEMPERATURE_DROP = -0.01f;

    private static final float ANGLE_OFFSET = new Random().nextInt(60) - 30f;

    private long scienceModeTimestamp = 0;

    private float temperature = 32;

    private float temperatureGrad = BASE_TEMPERATURE_DROP;

    private float heaterPower = 0f;

    private float batteryLevel = 30;

    private float batteryChargeRate = BASE_DISCHARGE_RATE;

    private float angleX = 90 + (float) new Random().nextInt(90) - 45f;

    private float angleY = 90 + (float) new Random().nextInt(90) - 45f;

    private SpacecraftMode mode = SpacecraftMode.NOMINAL_MODE;

    public float getTemperature() {
        return temperature;
    }

    public float getBatteryLevel() {
        return batteryLevel;
    }

    public int getTimeInScience() {
        int time = 0;
        if(this.scienceModeTimestamp != 0)
            time = (int)millisToSeconds(System.currentTimeMillis() - this.scienceModeTimestamp);
        return time;
    }

    public float getTemperatureGrad() {
        return temperatureGrad;
    }

    public float getHeaterPower() {
        return heaterPower;
    }

    public float getBatteryChargeRate() {
        return batteryChargeRate;
    }

    public float getAngleX() {
        return angleX;
    }

    public float getAngleY() {
        return angleY;
    }
    
    public Spacecraft init() {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(UPDATE_PERIOD);
                    updateStats();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return this;
    }


    public float calculateSolarPanelOutput() {
        double powerX = Math.sin(Math.toRadians(angleX - ANGLE_OFFSET));
        double powerY = Math.sin(Math.toRadians(angleY + ANGLE_OFFSET));
        powerX = Math.max(0, powerX);
        powerY = Math.max(0, powerY);
        return (float) (powerX * powerY);
    }

    private void calculateTemperature() {
        float seconds = millisToSeconds(UPDATE_PERIOD);
        float finalTemp = this.temperature + this.temperatureGrad * seconds;
        this.temperature = clamp(finalTemp, 100, -275.15f);
    }

    private void calculateBatteryLevel() {
        float seconds = millisToSeconds(UPDATE_PERIOD);
        float finalBattery = this.batteryLevel + this.batteryChargeRate * seconds;
        this.batteryLevel = clamp(finalBattery, 100, 0);
    }

    /**
     * This function acts as a setter for the x and y angles.
     *
     * @param x the desired x angle
     * @param y the desirded y angle
     */
    public void setMagnetorquerValues(int x, int y) {
        this.angleX = x;
        this.angleY = y;
    }

    public void setHeaterPower(float power) {
        if(this.mode != SpacecraftMode.SAFE_MODE)
            this.heaterPower = clamp(power, 1, 0);
        else
            this.heaterPower = 0;
    }

    public void enterMode(SpacecraftMode mode) {
        if (mode == checkMode()) this.mode = mode;
        if (this.mode == SpacecraftMode.SCIENCE_MODE) this.scienceModeTimestamp = System.currentTimeMillis();
        else this.scienceModeTimestamp = 0;
    }

    public boolean capturePhoto() {
        if (this.mode == SpacecraftMode.SCIENCE_MODE) {
            return getTimeInScience() > 60;
        } else return false;
    }

    /**
     * @return the possible mode the spacecraft can enter.
     */
    public SpacecraftMode checkMode() {
        if (this.batteryLevel < 20) return SpacecraftMode.SAFE_MODE;
        else if (this.batteryLevel > 25 && this.temperature > 36 && this.temperature < 38)
            return SpacecraftMode.SCIENCE_MODE;
        else return SpacecraftMode.NOMINAL_MODE;
    }
    
    /**
     * @return the current mode of the spacecraft.
     */
    public SpacecraftMode getMode() {
        return this.mode;
    }

    /**
     * Sets the battery charge/discharge rate depending on the current mode and the
     * solar panel output.
     */
    private void setBatteryChargeRate() {
        float power = calculateSolarPanelOutput();
        if (Objects.requireNonNull(this.mode) == SpacecraftMode.SAFE_MODE) {
            this.batteryChargeRate = (BASE_DISCHARGE_RATE_SAFE + power - heaterPower) / 10;
        } else {
            this.batteryChargeRate = (BASE_DISCHARGE_RATE + power - heaterPower) / 10;
        }
    }

    private void setTemperatureGrad() {
        this.temperatureGrad = this.heaterPower * HEATER_MULTIPLIER + BASE_TEMPERATURE_DROP;
    }

    private void updateStats() {
        calculateTemperature();
        calculateBatteryLevel();
        calculateSolarPanelOutput();
        setBatteryChargeRate();
        setTemperatureGrad();
        setHeaterPower(this.heaterPower);
        SpacecraftMode bestMode = checkMode();
        if(this.mode == SpacecraftMode.NOMINAL_MODE && bestMode == SpacecraftMode.SAFE_MODE)
            this.mode = bestMode;
        else if(this.mode == SpacecraftMode.SCIENCE_MODE && bestMode != SpacecraftMode.SCIENCE_MODE)
            this.mode = bestMode;

    }

    @Override
    public String toString() {
        updateStats();
        return "~~Spacecraft status:~~\n" + "Temperature: " + this.temperature + "C , change: " + this.temperatureGrad +
                "C/sec, heater power:" + this.heaterPower * 100 + "% \n" +
                "Mode is " + this.mode + " (check mode is " + checkMode() + "), time in science:" +
                millisToSeconds(System.currentTimeMillis() - this.scienceModeTimestamp)+ "\n" +
                "Battery level: " + this.batteryLevel + "%, battery charge rate: " + this.batteryChargeRate + "%/sec \n" +
                "Angle offset: " + ANGLE_OFFSET + ", AngleX: " + (int) this.angleX + ", AngleY: " + (int) this.angleY + ", panelOutput: " + this.calculateSolarPanelOutput() + "\n\n";

    }

    private float clamp(float target, float upper, float lower) {
        if (target <= lower) target = lower;
        else if (target >= upper) target = upper;
        return target;
    }

    private float millisToSeconds(long millis) {
        return (float) millis / 1000;
    }


}
