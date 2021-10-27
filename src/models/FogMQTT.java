package models;

/**
 * Classe responsável pelas informações da Fog que o emulador irá se comunicar.
 * 
 * @author Allan Capistrano e João Erick Barbosa
 */
public class FogMQTT {
    
    private String mqttAddress;
    private String region;

    public FogMQTT(String mqttAddress, String region) {
        this.mqttAddress = mqttAddress;
        this.region = region;
    }

    public String getMqttAddress() {
        return mqttAddress;
    }

    public String getRegion() {
        return region;
    }
}
