package net.pooleaf.core.modules.channel.common.channelgroup;

import net.pooleaf.core.modules.channel.common.channel.Channel;

import java.util.Collections;
import java.util.List;

public class LobbyChannelGroup extends ChannelGroup {

    public LobbyChannelGroup() {
        super("lobby");
        setType(KnownChannelGroupType.LOBBY);
    }


    /**
     * 가장 사람이 적은 로비를 반환합니다.
     * 없을 경우 null을 반환합니다.
     * @param excludeChannel 제외할 채널
     * @return 그룹에서 온라인이고 가장 사람이 적은 채널, 없을 경우 null
     */
    @Override
    public Channel getFastJoinChannel(Channel excludeChannel) {
        List<Channel> channels = getChannels();
        Collections.shuffle(channels);

        // 채널이 없을 경우 null 반환
        if (channels.size() < 1) {
            return null;
        }

        // 온라인이고 제외할 채널에 해당되지 않을 경우 반환
        return channels.stream()
                .filter(targetLobbyChannel -> targetLobbyChannel.isOnline()
                        && !targetLobbyChannel.equals(excludeChannel)
                        && targetLobbyChannel.getPlayerCount() < targetLobbyChannel.getMaxPlayerCount()
                        && targetLobbyChannel.isAllowFastJoin())
                .findFirst().orElse(null);
    }

}