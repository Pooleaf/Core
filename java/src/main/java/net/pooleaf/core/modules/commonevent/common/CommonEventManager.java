package net.pooleaf.core.modules.commonevent.common;

import net.pooleaf.core.modules.support.common.AutoRegisterExclude;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import net.pooleaf.core.plugin.CorePlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommonEventManager {

    private List<CommonEventMethod> eventMethods = new ArrayList<>();


    public void registerListener(CorePlugin plugin, CommonEventListener listener) {
        for (Method method : listener.getClass().getMethods()) {
            // CommonEventHandler 확인
            CommonEventHandler eventHandler = method.getAnnotation(CommonEventHandler.class);
            if (eventHandler == null) {
                continue;
            }

            CommonEventMethod eventMethod = new CommonEventMethod();
            eventMethod.setPlugin(plugin);
            eventMethod.setListener(listener);
            eventMethod.setMethod(method);
            eventMethod.setPriority(eventHandler.priority());

            eventMethods.add(eventMethod);
        }

        Collections.sort(eventMethods);
    }

    public void registerListeners(CorePlugin plugin) {
        for (Class targetClass : ReflectionUtil.getClasses(plugin)) {
            try {
                // Listener 클래스인지 확인
                if (!(CommonEventListener.class.isAssignableFrom(targetClass))) {
                    continue;
                }

                CommonEventListener listener = (CommonEventListener) targetClass.newInstance();

                // 자동 등록 제외 Listener
                if (listener.getClass().getAnnotation(AutoRegisterExclude.class) != null) {
                    continue;
                }

                registerListener(plugin, listener);
            } catch (Exception e) {
            } catch (Error e) {
            }
        }
    }

    public List<CommonEventMethod> getEventMethods(CommonEvent event) {
        List<CommonEventMethod> methods = new ArrayList<>();

        for (CommonEventMethod eventMethod : eventMethods) {
            if (eventMethod.getMethod().getParameterCount() == 1 && event.getClass().isAssignableFrom(eventMethod.getMethod().getParameterTypes()[0])) {
                methods.add(eventMethod);
            }
        }

        return methods;
    }

    public void callEvent(CommonEvent event) {
        // 우선순위 정렬
        List<CommonEventMethod> eventMethods = getEventMethods(event);
        eventMethods.sort(Comparator.comparingInt(CommonEventMethod::getPriority));

        // 이벤트 호출
        for (CommonEventMethod eventMethod : eventMethods) {
            if (eventMethod.getPlugin().isEnabled()) {
                eventMethod.invoke(event);
            }
        }
    }

}
