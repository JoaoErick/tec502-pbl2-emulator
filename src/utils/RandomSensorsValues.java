package utils;

import java.util.ArrayList;

/**
 * Gerador de valores dos sensores aleatórios
 *
 * @author Allan Capistrano
 */
public class RandomSensorsValues {

    /* ------------------------- Constantes ----------------------------------*/
    private static final float NORMAL_BODY_TEMPERATURE_START = (float) 35.8;
    private static final int NORMAL_RESPIRATORY_FREQUENCY_START = 8;
    private static final float NORMAL_BLOOD_OXYGENATION_START = (float) 95.9;
    private static final int NORMAL_BLOOD_PRESSURE_START = 99;
    private static final int NORMAL_HEART_RATE_START = 49;

    private static final float NORMAL_BODY_TEMPERATURE_END = (float) 37.1;
    private static final int NORMAL_RESPIRATORY_FREQUENCY_END = 15;
    private static final float NORMAL_BLOOD_OXYGENATION_END = (float) 99.99;
    private static final int NORMAL_BLOOD_PRESSURE_END = 151;
    private static final int NORMAL_HEART_RATE_END = 101;

    private static final float CRITICAL_BODY_TEMPERATURE_START = (float) 38.4;
    private static final int CRITICAL_RESPIRATORY_FREQUENCY_START = 20;
    private static final float CRITICAL_BLOOD_OXYGENATION_START = (float) 80;
    private static final int CRITICAL_BLOOD_PRESSURE_START = 70;
    private static final int CRITICAL_HEART_RATE_START = 110;

    private static final float CRITICAL_BODY_TEMPERATURE_END = (float) 41.1;
    private static final int CRITICAL_RESPIRATORY_FREQUENCY_END = 31;
    private static final float CRITICAL_BLOOD_OXYGENATION_END = (float) 95.8;
    private static final int CRITICAL_BLOOD_PRESSURE_END = 81;
    private static final int CRITICAL_HEART_RATE_END = 131;
    /* -----------------------------------------------------------------------*/

    private final boolean seriousCondition;
    private final ArrayList<Float> sensors;
    private float changeTendency;

    /**
     * Método construtor.
     *
     * @param seriousCondition boolean - Condição atual do paciente (Grave: true
     * | Normal: false)
     */
    public RandomSensorsValues(boolean seriousCondition) {
        this.seriousCondition = seriousCondition;
        this.sensors = new ArrayList<>();
    }

    /**
     * Gera os valores dos sensores com base na tendência do paciente. Existe
     * uma probilidade de 10% da tendência do paciente mudar.
     *
     * @return ArrayList<Float>
     */
    public ArrayList<Float> generate() {
        this.changeTendency = RandomNumbers.generateFloat(0, (float) 1.1);

        if ((this.seriousCondition && this.changeTendency < 0.9)
                || (!this.seriousCondition && this.changeTendency >= 0.9)) {
            this.sensors.add(RandomNumbers.generateFloat(
                    CRITICAL_BODY_TEMPERATURE_START, CRITICAL_BODY_TEMPERATURE_END)
            );
            this.sensors.add(
                    (float) RandomNumbers.generateInt(CRITICAL_RESPIRATORY_FREQUENCY_START, CRITICAL_RESPIRATORY_FREQUENCY_END)
            );
            this.sensors.add(
                    RandomNumbers.generateFloat(CRITICAL_BLOOD_OXYGENATION_START, CRITICAL_BLOOD_OXYGENATION_END)
            );
            this.sensors.add(
                    (float) RandomNumbers.generateInt(CRITICAL_BLOOD_PRESSURE_START, CRITICAL_BLOOD_PRESSURE_END)
            );
            this.sensors.add(
                    (float) RandomNumbers.generateInt(CRITICAL_HEART_RATE_START, CRITICAL_HEART_RATE_END)
            );
        } else if ((!this.seriousCondition && this.changeTendency < 0.9)
                || (this.seriousCondition && this.changeTendency >= 0.9)) {
            this.sensors.add(
                    RandomNumbers.generateFloat(NORMAL_BODY_TEMPERATURE_START, NORMAL_BODY_TEMPERATURE_END)
            );
            this.sensors.add(
                    (float) RandomNumbers.generateInt(NORMAL_RESPIRATORY_FREQUENCY_START, NORMAL_RESPIRATORY_FREQUENCY_END)
            );
            this.sensors.add(
                    RandomNumbers.generateFloat(NORMAL_BLOOD_OXYGENATION_START, NORMAL_BLOOD_OXYGENATION_END)
            );
            this.sensors.add(
                    (float) RandomNumbers.generateInt(NORMAL_BLOOD_PRESSURE_START, NORMAL_BLOOD_PRESSURE_END)
            );
            this.sensors.add(
                    (float) RandomNumbers.generateInt(NORMAL_HEART_RATE_START, NORMAL_HEART_RATE_END)
            );
        }

        return this.sensors;
    }

}
