
package mqtt;

import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * Classe responsável por oferecer os serviços necessários 
 * para a utilização do protocolo MQTT.
 * 
 * @author João Erick Barbosa
 */
public class MQTTClient implements MqttCallbackExtended {

    private final String serverURI;
    private MqttClient client;
    private final MqttConnectOptions mqttOptions;

    /**
     * Estabelece as configurações do cliente.
     * 
     * @param serverURI String - URI do servidor MQTT.
     * @param userName String - Nome de usuário do cliente.
     * @param password String - Senha do cliente.
     */
    public MQTTClient(String serverURI, String userName, String password) {
        this.serverURI = serverURI;

        mqttOptions = new MqttConnectOptions();
        mqttOptions.setMaxInflight(200);
        mqttOptions.setConnectionTimeout(3);
        mqttOptions.setKeepAliveInterval(10);
        mqttOptions.setAutomaticReconnect(true);
        mqttOptions.setCleanSession(false);

        if (userName != null && password != null) {
            mqttOptions.setUserName(userName);
            mqttOptions.setPassword(password.toCharArray());
        }
    }
    
    /**
     * O cliente conecta-se a um servidor MQTT usando as opções especificadas.
     */
    public void connect() {
        try {
            System.out.println("Conectando no broker MQTT em " + serverURI);
            client = new MqttClient(serverURI, String.format("cliente_java_%d", System.currentTimeMillis()), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
            client.setCallback(this);
            client.connect(mqttOptions);
        } catch (MqttException ex) {
            System.out.println("Erro ao se conectar ao broker mqtt " + serverURI + " - " + ex);
        }
    }

    /**
     * O cliente desconecta-se do servidor.
     */
    public void disconnect() {
        if (client == null || !client.isConnected()) {
            return;
        }
        try {
            client.disconnect();
            client.close();
        } catch (MqttException ex) {
            System.out.println("Erro ao desconectar do broker mqtt - " + ex);
        }
    }
    
    /**
     * O cliente realiza a assinatura nos tópicos e na qualidade do serviço 
     * passados por parâmetro.
     * 
     * @param qos int - Qualidade do serviço.
     * @param listener IMqttMessageListener - Retorno da chamada para lidar 
     * com as mensagens recebidas.
     * @param topics String... Tópicos em que o cliente deve assinar.
     * 
     * @return IMqttToken - Mecanismo para rastrear o término de uma tarefa 
     * assíncrona.
     */
    public IMqttToken subscribe(int qos, IMqttMessageListener listener, String... topics) {
        if (client == null || topics.length == 0) {
            return null;
        }
        
        int[] qualityOfServices = new int[topics.length];
        IMqttMessageListener[] listeners = new IMqttMessageListener[topics.length];

        for (int i = 0; i < topics.length; i++) {
            qualityOfServices[i] = qos;
            listeners[i] = listener;
        }
        try {
            return client.subscribeWithResponse(topics, qualityOfServices, listeners);
        } catch (MqttException ex) {
            System.out.println(String.format("Erro ao se inscrever nos tópicos %s - %s", Arrays.asList(topics), ex));
            return null;
        }
    }

    /**
     * Cancela a assinatura do cliente em um ou mais tópicos.
     * 
     * @param topics String... - Tópicos para cancelar a assinatura.
     */
    public void unsubscribe(String... topics) {
        if (client == null || !client.isConnected() || topics.length == 0) {
            return;
        }
        try {
            client.unsubscribe(topics);
        } catch (MqttException ex) {
            System.out.println(String.format("Erro ao se desinscrever no tópico %s - %s", Arrays.asList(topics), ex));
        }
    }
    
    /**
     * Repassa informações para a publicação de uma mensagem em um tópico 
     * no servidor.
     * 
     * @param topic String - Tópico em que será publicada a mensagem.
     * @param payload byte[] - Matriz de bytes da mensagem.
     * @param qos int - Qualidade do serviço.
     */
    public void publish(String topic, byte[] payload, int qos) {
        publish(topic, payload, qos, false);
    }

    /**
     * Publica uma mensagem em um tópico no servidor e retorna a mensagem 
     * aos assinantes assim que for entregue.
     * 
     * @param topic String - Tópico em que será publicada a mensagem.
     * @param payload byte[] - Matriz de bytes da mensagem.
     * @param qos int - Qualidade do serviço.
     * @param retained boolean - Determina se a mensagem deve ou não 
     * ser retida no servidor.
     */
    public synchronized void publish(String topic, byte[] payload, int qos, boolean retained) {
        try {
            if (client.isConnected()) {
                client.publish(topic, payload, qos, retained);
                System.out.println(String.format("Tópico %s publicado. %dB", topic, payload.length));
            } else {
                System.out.println("Cliente desconectado, não foi possível publicar o tópico " + topic);
            }
        } catch (MqttException ex) {
            System.out.println("Erro ao publicar " + topic + " - " + ex);
        }
    }

    /**
     * Chamado quando a conexão com o servidor for perdida.
     * 
     * @param thrwbl Throwable - Mensagem da exceção.
     */
    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Conexão com o broker perdida -" + thrwbl);
    }

    /**
     * Chamado quando a conexão com o servidor é concluída com êxito.
     * 
     * @param reconnect boolean - Se verdadeiro, a conexão foi resultado de 
     * reconexão automática.
     * @param serverURI String - O URI do servidor ao qual a conexão foi feita.
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("Cliente MQTT " + (reconnect ? "reconectado" : "conectado") + " com o broker " + serverURI);
    }

    /**
     * Chamado quando a entrega de uma mensagem foi concluída e todas as 
     * confirmações foram recebidas.
     * 
     * @param imdt IMqttDeliveryToken - O token de entrega associado à mensagem.
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
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
    }
    
}
