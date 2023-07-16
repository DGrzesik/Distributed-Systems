package conditions;

import gen.Sky;
import gen.Slope;

public class ResortConditions {
    private String resort;
    private float temperature;
    private float windSpeed;
    private boolean isNight;
    private Sky sky;
    private Slope slope;

    public ResortConditions(String resort, float temperature, float wind_speed, boolean is_night, Sky sky, Slope slope) {
        this.resort=resort;
        this.temperature = temperature;
        this.windSpeed = wind_speed;
        this.isNight = is_night;
        this.sky = sky;
        this.slope = slope;
    }

    public String getResort() {
        return resort;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public boolean getIsNight() {
        return isNight;
    }

    public Sky getSky() {
        return sky;
    }

    public Slope getSlope() {
        return slope;
    }
}
