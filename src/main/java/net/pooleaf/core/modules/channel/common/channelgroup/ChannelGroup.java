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

    public String toJson() {
        return GsonUtil.getGson().toJson(this);
    }

    public ChannelGroup loadFromJson(String jsonString) {
        return (ChannelGroup) GsonUtil.loadFromJson(jsonString, this);
    }

    public void load() {
        // TODO
    }

    public void save() {
        // TODO
    }

    public Channel getFastJoinChannel(String playerName) {
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

    public Channel fastJoin(String playerName) {
        // TODO
        return null;
    }

    public void remoteCommand(String sender, String command) {
        // TODO
    }

}
