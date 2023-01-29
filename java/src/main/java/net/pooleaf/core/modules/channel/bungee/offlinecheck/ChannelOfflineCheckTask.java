package net.pooleaf.core.modules.channel.bungee.offlinecheck;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.commonscheduler.common.CommonSchedulerTask;
import lombok.Cleanup;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.pooleaf.core.modules.channel.ChannelModule;

import java.net.InetSocketAddress;
import java.net.Socket;

public class ChannelOfflineCheckTask extends CommonSchedulerTask {

    public ChannelOfflineCheckTask() {
        super(Core.getPlugin());
    }


    @Override
    public void run() {
        for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
            Channel channel = ChannelModule.getChannel(serverInfo.getName());
            if (channel == null) {
                continue;
            }

            String ip = serverInfo.getAddress().getAddress().getHostAddress();
            int port = serverInfo.getAddress().getPort();

            try {
                @Cleanup Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port));

                // 오프라인 상태였으면 온라인으로 바꿔줌
                if (!channel.isOnline()) {
                    channel.setOnline(true);
                    channel.save();
                }
            } catch (Exception e) {
                // 온라인 상태였으면 오프라인으로 바꿔줌
                if (channel.isOnline()) {
                    channel.setOnline(false);
                    channel.save();
                }
            }
        }
    }

}
