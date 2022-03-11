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
    private int channelStatus;

    private int playerCount;
    private int maxPlayerCount;

    private List<String> playerNames = new ArrayList<>();
    private List<UUID> playerUuids = new ArrayList<>();

    private LinkedTreeMap<String, Object> datas;


    public boolean hasDisplayName() {
        return displayName != null;
    }

    /**
     * 채널의 표기 이름을 반환합니다.
     * 설정된 표기 이름이 없을 경우 채널의 진짜 이름을 반환합니다.
     */
    public String getDisplayName() {
        return hasDisplayName() ? displayName : name;
    }

    public boolean hasGroup() {
        return groupName != null;
    }

    public ChannelGroup getGroup() {
        return hasGroup() ? ChannelModule.getChannelGroupManager().get(groupName) : null;
    }

    /**
     * 채널에 해당 플레이어가 접속 중인지 여부를 반환합니다.
     */
    public boolean hasPlayer(String playerName) {
        for (String name : playerNames) {
            if (playerName.equals(name)) {
                return true;
            }
        }

         return false;
    }

    /**
     * 채널에 해당 플레이어가 접속 중인지 여부를 반환합니다.
     */
    public boolean hasPlayer(UUID uuid) {
        return playerUuids.contains(uuid);
    }

    /**
     * 채널 접속 가능 여부를 반환합니다.
     */
    public boolean canJoin() {
        return online && playerCount < maxPlayerCount;
    }

    /**
     * 채널에 데이터를 저장합니다.
     */
    public void setData(String key, Object value) {
        datas.put(key, value);
    }

    /**
     * 채널에 해당 데이터가 존재하는지 확인합니다.
     */
    public boolean existsData(String key) {
        return datas.containsKey(key);
    }

    /**
     * 채널에서 해당 데이터를 불러옵니다.
     */
    public Object getData(String key) {
        return datas.get(key);
    }

    /**
     * 채널 정보를 Json으로 반환합니다.
     */
    public String toJson() {
        return GsonUtil.getGson().toJson(this);
    }

    /**
     * Json에서 채널 정보를 불러옵니다.
     */
    public Channel loadFromJson(String jsonString) {
        return (Channel) GsonUtil.loadFromJson(jsonString, this);
    }


    /**
     * Redis에서 채널 정보를 불러옵니다.
     */
    public void load() {
        ChannelModule.getRedisManager().channel().loadChannel(name);
    }

    /**
     * Redis에 채널 정보를 저장합니다.
     */
    public void save() {
        ChannelModule.getRedisManager().channel().saveChannel(this);
    }

    /**
     * 플레이어를 이 채널로 이동시킵니다.
     * @param playerName 이동시킬 플레이어 이름
     * @return 이동 성공 여부 (번지코드)
     */
    public boolean join(String playerName) {
        return ChannelModule.getChannelAdapter().join(name, playerName);
    }

    /**
     * 플레이어를 이 채널로 이동시킵니다.
     * @param uuid 이동시킬 플레이어 UUID
     * @return 이동 성공 여부 (번지코드)
     */
    public boolean join(UUID uuid) {
        return ChannelModule.getChannelAdapter().join(name, uuid);
    }

    /**
     * 해당 채널에 원격 명령어를 보냅니다.
     * @param commandLine 명령어
     */
    public void remoteCommand(String senderName, String commandLine) {
        ChannelModule.getChannelAdapter().remoteCommand(name, senderName, commandLine);
    }

    /**
     * 해당 채널에 데이터를 보냅니다.
     * @param task 할 일
     * @param datas 데이터
     */
    public void sendData(String task, Object... datas) {
        ChannelModule.getChannelAdapter().sendData(name, task, datas);
    }

}
