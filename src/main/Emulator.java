
package main;

import com.github.javafaker.Faker;
import java.util.List;
import mqtt.MQTTClient;
import mqtt.PatientDeviceListener;
import org.json.JSONObject;
import utils.IdGenerate;
import utils.RandomUtil;
import utils.RandomSensorsValues;

/**
 * Classe principal responsável por emular um dispositivo de sensores.
 * 
 * @author Allan Capistrano e João Erick Barbosa
 */
public class Emulator {

    /* ------------------------- Constantes ----------------------------------*/
    private static final String MQTT_ADDRESS = "tcp://broker.mqttdashboard.com:1883";
    private static final String DEFAULT_PUBLISH_TOPIC = "tec502/pbl2/fog";
    private static final String DEFAULT_SUBSCRIBE_TOPIC = "tec502/pbl2/patientDevice";
    private static final int QOS = 0;
    /* ------------------------- Constantes ----------------------------------*/
    
    public static String topic;
    
    public static void main(String[] args) throws InterruptedException {
        
        /**
         * Realiza a conexão com o servidor MQTT.
         */
        MQTTClient client = new MQTTClient(MQTT_ADDRESS, null, null);
        client.connect();
        
        /**
         * Publica uma mensagem vazia em tópico genérico com o objetivo de 
         * receber o tópico adequado para o envio dos dados.
         */
        client.publish(DEFAULT_PUBLISH_TOPIC, "".getBytes(), QOS);
        
        /**
         * Responsável por receber o nome do tópico em que os dados serão 
         * enviados e realizar a assinatura neste.
         */
        new PatientDeviceListener(client, DEFAULT_SUBSCRIBE_TOPIC, QOS);
        
        String deviceId = new IdGenerate(12, ":").generate("XX.XX");
        String userName = new Faker().name().fullName();
        boolean tendency = RandomUtil.generateBoolean();
        
        System.out.println("\n----------------------");
        System.out.println("Tendência: " + (tendency ? "Grave" : "Normal"));
        System.out.println("----------------------");
        
        
        while(true){
            Thread.sleep(1000);
            
            if(!topic.equals("")){
                RandomSensorsValues sensors = new RandomSensorsValues(tendency);
                List<Float> data = sensors.generate();
                
                JSONObject json = new JSONObject();
                
                json.put("id", deviceId);
                json.put("userName", userName);
                json.put("temperature", data.get(0));
                json.put("respiratoryFrequency", Math.round(data.get(1)));
                json.put("bloodOxygen", data.get(2));
                json.put("bloodPressure", Math.round(data.get(3)));
                json.put("heartRate", Math.round(data.get(4)));
                
                /**
                 * Publicando os dados do dispositivo para o tópico específico.
                 */
                client.publish(topic, json.toString().getBytes(), QOS);

            }
        
        }
        
    }
    
}
