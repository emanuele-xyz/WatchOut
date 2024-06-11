package watchout.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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

    @XmlElement
    private List<Player> players;

    private Players() {
        this.players = new ArrayList<>();
    }

    public synchronized List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public synchronized void setPlayers(List<Player> players) {
        this.players = players;
    }

    public synchronized boolean isPlayerRegistered(int id) {
        return players.stream().anyMatch(p -> p.getId() == id);
    }

    public synchronized boolean registerPlayer(int id, String address, int port) {
        boolean isPlayerRegistered = isPlayerRegistered(id);
        if (!isPlayerRegistered) {
            players.add(new Player(id, address, port));
            return true;
        } else {
            return false;
        }
    }
}
