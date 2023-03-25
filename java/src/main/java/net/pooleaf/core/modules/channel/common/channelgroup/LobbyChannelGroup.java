package net.pooleaf.core.modules.channel.common.channelgroup;

import net.pooleaf.core.modules.channel.common.channel.Channel;

import java.util.Collections;
import java.util.List;

public class LobbyChannelGroup extends ChannelGroup {

    public final static double MIN_MAIN_LOBBY_FAST_JOIN_TPS = 17;


    public LobbyChannelGroup() {
        super("lobby");
    }


    /**
     * 첫 번째 로비의 TPS가 MIN_MAIN_LOBBY_FAST_JOIN_TPS 보다 높고, excludeChannel이 첫 번째 로비가 아닐 경우 첫 번째 로비를,
     * 낮을 경우 가장 사람이 적은 로비를 반환합니다.
     * @param excludeChannel 제외할 채널
     * @return 그룹에서 온라인이고 가장 사람이 적은 채널
     */
    @Override
    public Channel getFastJoinChannel(Channel excludeChannel) {
        List<Channel> channels = getChannels();
        Collections.sort(channels);

        // 채널이 없을 경우 null 반환
        if (channels.size() < 1) {
            return null;
        }

        Channel mainLobbyChannel = channels.get(0);
        // 메인 로비 채널 TPS가 최소 TPS보다 높을 경우 메인 로비 반환
        if (!mainLobbyChannel.equals(excludeChannel)
                && mainLobbyChannel.canJoin()
                && mainLobbyChannel.isAllowFastJoin()
                && mainLobbyChannel.getTps() > MIN_MAIN_LOBBY_FAST_JOIN_TPS) {
            return mainLobbyChannel;
        }
        // 로비 채널이 없으면 null 반환
        else if (channels.size() <= 1) {
            return null;
        }
        // 아닐 경우 다른 로비 중 가장 사람이 적은 로비 반환
        else {
            return getFastJoinChannel(excludeChannel);
        }
    }

}