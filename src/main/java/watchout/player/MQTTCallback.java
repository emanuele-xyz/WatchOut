package watchout.player;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import watchout.MQTTConfig;

public class MQTTCallback implements MqttCallback {
    private final Callback onGameStartCallback;

    public MQTTCallback(Callback onGameStartCallback) {
        this.onGameStartCallback = onGameStartCallback;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("MQTT connection lost: " + throwable.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        switch (topic) {
            case MQTTConfig.GAME_START_TOPIC: {
                System.out.println("Game started!");
                onGameStartCallback.apply();
            } break;
            case MQTTConfig.CUSTOM_MESSAGE_TOPIC: {
                String message = new String(mqttMessage.getPayload());
                System.out.println("Received message from admin client: " + message);
            } break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // NOTE: unused
    }
}
