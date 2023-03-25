package net.pooleaf.core.modules.support.common.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractPlayer<T> {

    protected UUID uuid;

    public CommonPlayer<T> getCommonPlayer() {
        return CommonSenderModule.getPlayer(uuid);
    }

    public T getPlayer() {
        return getCommonPlayer().getPlatformSender();
    }

    public String getName() {
        return getCommonPlayer().getName();
    }

    public String getDisplayName() {
        return getCommonPlayer().getDisplayName();
    }

    public boolean isOnline() {
        return getCommonPlayer() != null && getCommonPlayer().isOnline();
    }

}
