package conditions;

import gen.Sky;
import gen.Slope;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Forecast {
    private final Random random = new Random();
    private final float[] temperatureRange = {-30,30};
    private final float maxWindSpeed = 7;
    public Forecast(){}
    public ResortConditions getSkiConditions(String resort){
            return new ResortConditions(resort,getTemperature(),getWindSpeed(),
                    random.nextBoolean(),
                    Sky.newBuilder().setCloudsValue(random.nextInt(4))
                            .setPrecipitationValue(random.nextInt(3)).build(),
                    Slope.newBuilder().setSnowLevel(random.nextInt(100)).setSnowValue(random.nextInt(4)).build());
    }
    public float getTemperature(){
        float temp = random.nextFloat()*(temperatureRange[1]-temperatureRange[0])-temperatureRange[0];
        return Math.round(temp*100)/100f;
    }
    public float getWindSpeed(){
        return Math.round(random.nextFloat()*maxWindSpeed*100)/100f;
    }
}
