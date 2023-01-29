package net.pooleaf.core.modules.commonevent.common;

import lombok.Data;
import lombok.SneakyThrows;
import net.pooleaf.core.plugin.CorePlugin;

import java.lang.reflect.Method;

@Data
public class CommonEventMethod implements Comparable<CommonEventMethod> {

    private CorePlugin plugin; // 이벤트를 등록한 플러그인

    private CommonEventListener listener; // 이벤트 Listener 객체
    private Method method; // 이벤트 메소드

    private byte priority; // 이벤트 우선순위


    @SneakyThrows
    public void invoke(CommonEvent event) {
        method.invoke(listener, event);
    }

    @Override
    public int compareTo(CommonEventMethod o) {
        return Byte.compare(this.priority, o.priority);
    }

}
