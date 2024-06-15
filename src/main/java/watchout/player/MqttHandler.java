package watchout.player;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import watchout.MQTTConfig;

public class MqttHandler implements MqttCallback {

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("MQTT connection lost: " + throwable.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        switch (topic) {
            case MQTTConfig.GAME_START_TOPIC: {
                EventHandlers.onGameStart();
            } break;
            case MQTTConfig.CUSTOM_MESSAGE_TOPIC: {
                String message = new String(mqttMessage.getPayload());
                EventHandlers.onAdminClientMessage(message);
            } break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // NOTE: unused
    }
}
