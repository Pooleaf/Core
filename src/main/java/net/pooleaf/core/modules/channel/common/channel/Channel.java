package net.pooleaf.core.modules.channel.common.channel;

import com.google.gson.internal.LinkedTreeMap;
import lombok.Data;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.support.common.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Channel {

    private final String name;
    private String displayName;
    private String groupName;

    private boolean online;
    private boolean allowFastJoin;

    private int playerCount;
    private int maxPlayerCount;

    private List<String> playerNames = new ArrayList<>();
    private List<UUID> playerUuids = new ArrayList<>();

    private LinkedTreeMap<String, Object> datas;


    public boolean hasDisplayName() {
        return displayName != null;
    }

    public String getDisplayName() {
        return hasDisplayName() ? displayName : name;
    }

    public boolean hasGroup() {
        return groupName != null;
    }

    public ChannelGroup getGroup() {
        return hasGroup() ? ChannelModule.getChannelGroupManager().get(groupName) : null;
    }

    public boolean hasPlayer(String playerName) {
        for (String name : playerNames) {
            if (playerName.equals(name)) {
                return true;
            }
        }

         return false;
    }

    public boolean hasPlayer(UUID uuid) {
        return playerUuids.contains(uuid);
    }

    public void setData(String key, Object value) {
        datas.put(key, value);
    }

    public boolean existsData(String key) {
        return datas.containsKey(key);
    }

    public Object getData(String key) {
        return datas.get(key);
    }

    public String toJson() {
        return GsonUtil.getGson().toJson(this);
    }

    public Channel loadFromJson(String jsonString) {
        return (Channel) GsonUtil.loadFromJson(jsonString, this);
    }


    /**
     * Redis에서 채널 정보를 불러옵니다.
     */
    public void load() {
        // TODO
    }

    /**
     * Redis에 채널 정보를 저장합니다.
     */
    public void save() {
        // TODO
    }

    /**
     * 플레이어를 이 채널로 이동시킵니다.
     * @param playerName 이동시킬 플레이어
     * @return 이동 성공 여부 (번지코드)
     */
    public boolean join(String playerName) {
        // TODO
        return false;
    }

    /**
     * 해당 채널에 원격 명령어를 보냅니다.
     * @param command 명령어
     */
    public void remoteCommand(String sender, String command) {
        // TODO
    }

}
