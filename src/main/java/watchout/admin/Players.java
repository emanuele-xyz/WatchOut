package watchout.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Players {
    private static Players instance = null;

    public synchronized static Players getInstance() {
        if (instance == null) {
            instance = new Players();
        }
        return instance;
    }

    private final List<Player> players;

    private Players() {
        players = new ArrayList<Player>();
    }
}
