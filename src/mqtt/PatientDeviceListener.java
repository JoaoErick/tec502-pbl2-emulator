
package mqtt;

import main.Emulator;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

/**
 * Responsável por receber o tópico adequado para o envio dos dados.
 * @author Allan Capistrano e João Erick Barbosa
 */
public class PatientDeviceListener implements IMqttMessageListener {
    
    private final MQTTClient clientMQTT;

    /**
     * O cliente realiza uma assinatura a partir do tópico e da qualidade de 
     * serviço passados por parâmetro.
     * 
     * @param clientMQTT MQTTClient
     * @param topic String
     * @param qos int
     */
    public PatientDeviceListener(MQTTClient clientMQTT, String topic, int qos) {
        this.clientMQTT = clientMQTT;
        
        /* Se inscreve no tópico */
        this.clientMQTT.subscribe(qos, this, topic);
    }
    
    /**
     * Este método é chamado quando chega uma mensagem do servidor.
     * 
     * @param topic String - Tópico em que a mensagem foi publicada.
     * @param msg MqttMessage - Mensagem.
     * @throws Exception - Em caso de erro, o cliente será encerrado.
     */
    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
        /**
         * Caso tenha recebido a mensagem certa, responde com o tópico que o 
         * dispositivo deverá publicar.
         */
        if (topic.contains("tec502/pbl2/patientDevice")) {
            
            this.clientMQTT.unsubscribe(topic);
            
            JSONObject json = new JSONObject(new String(msg.getPayload()));
            
            Emulator.topic = json.getString("topic");
            
            System.out.println("");
            System.out.println(json);
            System.out.println("");
        } else {
            // TODO
        }
    }
    
}
