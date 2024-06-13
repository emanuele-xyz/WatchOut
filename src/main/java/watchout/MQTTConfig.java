package watchout;

public class MQTTConfig {
    public static final String BROKER_ADDRESS = "tcp://localhost:1883";
    public static final String GAME_START_TOPIC = "game/start";
    public static final String CUSTOM_MESSAGE_TOPIC = "message";
    public static final int GAME_START_QOS = 2; // TODO: do you need 2 or can we just use 1?
    public static final int CUSTOM_MESSAGE_QOS = 2;
}
