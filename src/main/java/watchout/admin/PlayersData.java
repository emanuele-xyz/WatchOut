package watchout.admin;

import watchout.Pitch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayersData {
    private static PlayersData instance = null;

    public synchronized static PlayersData getInstance() {
        if (instance == null) {
            instance = new PlayersData();
        }
        return instance;
    }

    private final List<PlayerData> playersData;

    private PlayersData() {
        playersData = new ArrayList<>();
    }

    public synchronized List<PlayerData> getPlayersData() {
        return new ArrayList<>(playersData);
    }

    public synchronized boolean registerPlayer(int id, String address, int port) {
        // NOTE: a player can be added only if there are no other players with the same ID
        boolean anyPlayersWithSameID = playersData.stream().anyMatch(p -> p.getId() == id);
        if (!anyPlayersWithSameID) {
            playersData.add(new PlayerData(id, address, port, Pitch.getRandomStartingPitchCoordinate(), Pitch.getRandomStartingPitchCoordinate()));
            return true;
        } else {
            return false;
        }
    }
}
