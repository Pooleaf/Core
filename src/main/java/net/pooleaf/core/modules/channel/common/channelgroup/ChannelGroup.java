package net.pooleaf.core.modules.channel.common.channelgroup;

import lombok.Data;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.support.common.util.GsonUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class ChannelGroup {

    private final String name;
    private String displayName;


    public boolean hasDisplayName() {
        return displayName != null;
    }

    public String getDisplayName() {
        return hasDisplayName() ? displayName : name;
    }

    public boolean hasChannel(String name) {
        for (Channel channel : getChannels()) {
            if (channel.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasChannel(Channel channel) {
        return getChannels().contains(channel);
    }

    public List<Channel> getChannels() {
        return ChannelModule.getChannels().stream()
                .filter(channel -> channel.hasGroup() && channel.getGroupName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Channel> getOnlineChannels() {
        return ChannelModule.getChannels().stream()
                .filter(channel -> channel.hasGroup() && channel.getGroupName().equals(name) && channel.isOnline())
                .collect(Collectors.toList());
    }

    /**
     * 접속 가능한 채널 목록을 반환합니다.
     */
    public List<Channel> getChannelsCanJoin() {
        return ChannelModule.getChannels().stream()
                .filter(channel -> channel.hasGroup() && channel.getGroupName().equals(name) && channel.isOnline() && channel.getPlayerCount() < channel.getMaxPlayerCount())
                .collect(Collectors.toList());
    }

    public boolean hasPlayer(String playerName) {
        for (Channel channel : getChannels()) {
            if (channel.hasPlayer(playerName)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasPlayer(UUID uuid) {
        for (Channel channel : getChannels()) {
            if (channel.hasPlayer(uuid)) {
                return true;
            }
        }

        return false;
    }

    public Set<String> getPlayerNames() {
        Set<String> names = new HashSet<>();

        for (Channel channel : getChannels()) {
            if (channel.isOnline()) {
                names.addAll(channel.getPlayerNames());
            }
        }

        return names;
    }

    public int getPlayerCount() {
        return getPlayerNames().size();
    }

    /**
     * 채널 그룹 정보를 Json으로 반환합니다.
     */
    public String toJson() {
        return GsonUtil.getGson().toJson(this);
    }

    /**
     * Json에서 채널 그룹 정보를 불러옵니다.
     */
    public ChannelGroup loadFromJson(String jsonString) {
        return (ChannelGroup) GsonUtil.loadFromJson(jsonString, this);
    }

    /**
     * Redis에서 채널 정보를 불러옵니다.
     */
    public void load() {
        ChannelModule.getRedisManager().channelGroup().loadGroup(name);
    }

    /**
     * Redis에 채널 정보를 저장합니다.
     */
    public void save() {
        ChannelModule.getRedisManager().channelGroup().saveGroup(this);
    }

    /**
     * 해당 그룹에서 온라인이고 가장 사람이 적은 채널을 반환합니다.
     * @return 그룹에서 온라인이고 가장 사람이 적은 채널
     */
    public Channel getFastJoinChannel() {
        Channel fastJoinChannel = null;

        for (Channel onlineChannel : getChannelsCanJoin()) {
            if (!onlineChannel.isAllowFastJoin()) {
                continue;
            }

            if (fastJoinChannel == null) {
                fastJoinChannel = onlineChannel;
            } else if (onlineChannel.getPlayerCount() < fastJoinChannel.getPlayerCount()) {
                fastJoinChannel = onlineChannel;
            }
        }

        return fastJoinChannel;
    }

    /**
     * 플레이어를 해당 그룹의 온라인이고 가장 사람이 적은 채널에 입장시킵니다.
     * @param playerName 플레이어 이름
     * @return 입장 성공 여부
     */

    public Channel fastJoin(String playerName) {
        Channel channel = getFastJoinChannel();
        if (channel != null) {
            channel.join(playerName);
        }

        return channel;
    }

    /**
     * 플레이어를 해당 그룹의 온라인이고 가장 사람이 적은 채널에 입장시킵니다.
     * @param uuid 플레이어 UUID
     * @return 입장 성공 여부
     */
    public Channel fastJoin(UUID uuid) {
        Channel channel = getFastJoinChannel();
        if (channel != null) {
            channel.join(uuid);
        }

        return channel;
    }

    /**
     * 해당 그룹의 모든 채널에 원격 명령어를 보냅니다.
     * @param senderName 보내는사람 이름
     * @param commandLine 명령어
     */
    public void remoteCommand(String senderName, String commandLine) {
        for (Channel onlineChannel : getOnlineChannels()) {
            onlineChannel.remoteCommand(senderName, commandLine);
        }
    }

}
