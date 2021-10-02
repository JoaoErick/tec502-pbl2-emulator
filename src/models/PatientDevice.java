package models;

import java.io.Serializable;

/**
 * Classe do paciente.
 *
 * @author Allan Capistrano
 */
public class PatientDevice implements Serializable {

    private String name;
    private final String deviceId;
    private float bodyTemperature;
    private int respiratoryFrequency;
    private float bloodOxygenation;
    private int bloodPressure;
    private int heartRate;
    private boolean isSeriousCondition;
    private final String isSeriousConditionLabel;
    private final float patientSeverityLevel;

    /**
     * Método construtor.
     *
     * @param name String - Nome do paciente.
     * @param bodyTemperature float - Valor da temperatura corporal registrada
     * pelo sensor.
     * @param respiratoryFrequency int - Valor da frequência respiratória
     * registrada pelo sensor.
     * @param bloodOxygenation float - Nível de oxigênio no sangue registrado
     * pelo sensor.
     * @param bloodPressure int - Pressão arterial registrada pelo sensor.
     * @param heartRate int - Frequência cardíaca registrada pelo sensor.
     * @param deviceId String - Identificador do dispositivo do paciente.
     */
    public PatientDevice(
            String name,
            float bodyTemperature,
            int respiratoryFrequency,
            float bloodOxygenation,
            int bloodPressure,
            int heartRate,
            String deviceId
    ) {
        this.name = name;
        this.bodyTemperature = bodyTemperature;
        this.respiratoryFrequency = respiratoryFrequency;
        this.bloodOxygenation = bloodOxygenation;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.deviceId = deviceId;

        this.isSeriousCondition = this.checkPatientCondition();
        this.patientSeverityLevel = this.calculatePatientSeverityLevel();

        this.isSeriousConditionLabel = this.checkPatientCondition() ? "Sim" : "Não";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public float getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(float bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public float getRespiratoryFrequency() {
        return respiratoryFrequency;
    }

    public void setRespiratoryFrequency(int respiratoryFrequency) {
        this.respiratoryFrequency = respiratoryFrequency;
    }

    public float getBloodOxygenation() {
        return bloodOxygenation;
    }

    public void setBloodOxygenation(float bloodOxygenation) {
        this.bloodOxygenation = bloodOxygenation;
    }

    public float getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public float getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public boolean isIsSeriousCondition() {
        return isSeriousCondition;
    }

    public void setIsSeriousCondition(boolean isSeriousCondition) {
        this.isSeriousCondition = isSeriousCondition;
    }

    public float getPatientSeverityLevel() {
        return patientSeverityLevel;
    }

    public String getIsSeriousConditionLabel() {
        return isSeriousConditionLabel;
    }

    /**
     * Verifica se o paciente está em um estado grave com base nos dados dos
     * sensores.
     *
     * @return boolean
     */
    public boolean checkPatientCondition() {
        return (this.bodyTemperature > (float) 38.6)
                || (this.respiratoryFrequency >= 21)
                || (this.bloodOxygenation < (float) 96)
                || (this.bloodPressure <= 100)
                || (this.heartRate >= 111);
    }

    /**
     * Média ponderada para determinar o nível de gravidade dos pacientes que
     * estão em um estado crítico.
     *
     * @return float
     */
    public float calculatePatientSeverityLevel() {
        if (this.isSeriousCondition) {
            /* Se todos os valores forem 0, retorna a maior pontuação. */
            if (this.bodyTemperature == 0
                    && this.respiratoryFrequency == 0
                    && this.bloodOxygenation == 0
                    && this.bloodPressure == 0
                    && this.heartRate == 0) {
                return 150;
            }

            /**
             * bodyTemperature -> peso 2 | respiratoryFrequency -> peso 2
             * bloodOxygenation -> peso 4 | bloodPressure peso -> 1 | heartRate
             * -> peso 1
             */
            {
                return (this.bodyTemperature * 2
                        + this.respiratoryFrequency * 2
                        + (100 - this.bloodOxygenation)
                        + (190 - this.bloodPressure)
                        + this.heartRate) / (2 + 2 + 4 + 1 + 1);
            }
        }

        return 0;
    }
}
