package watchout.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class PlayerList {
    private List<Player> players;

    public PlayerList() {
        this(new ArrayList<>());
    }

    public PlayerList(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        players.forEach(p -> sb.append(p.toString()).append("\n"));
        return sb.toString();
    }
}
