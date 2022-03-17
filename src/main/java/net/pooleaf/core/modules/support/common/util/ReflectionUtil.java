package net.pooleaf.core.modules.support.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.pooleaf.core.plugin.CorePlugin;

@UtilityClass
public class ReflectionUtil {

    /**
     * 해당 Class에서 해당 이름을 가진 Method를 반환합니다.
     * @param targetClass Class
     * @param name 찾을 Method 이름
     * @return 찾은 Method
     */
    public static Method getMethod(Class<?> targetClass, String name) {
        for (Method method : targetClass.getDeclaredMethods()) {
            if (method.getName().equals(name)) return method;
        }

        return null;
    }

    /**
     * 해당 Class 및 부모 Class에서 해당 이름을 가진 Method를 반환합니다.
     * Object의 Method는 제외합니다.
     * @param targetClass Class
     * @param name 찾을 Method 이름
     * @return 찾은 Method
     */
    public static Method getMethodAll(Class<?> targetClass, String name) {
        Class findClass = targetClass;
        while (findClass != null && findClass != Object.class) {
            for (Method method : findClass.getDeclaredMethods()) {
                if (method.getName().equals(name)) return method;
            }

            findClass = findClass.getSuperclass();
        }

        return null;
    }

    /**
     * 해당 Class에서 해당 이름을 가진 Field를 반환합니다.
     * @param targetClass Class
     * @param name 찾을 Field 이름
     * @return 찾은 Field
     */
    @SneakyThrows(Exception.class)
    public static Field getField(Class<?> targetClass, String name) {
        return targetClass.getDeclaredField(name);
    }

    /**
     * 해당 Class 및 부모 Class에서 해당 이름을 가진 Field를 반환합니다.
     * Object의 Field는 제외합니다.
     * @param targetClass Class
     * @param name 찾을 Field 이름
     * @return 찾은 Field
     */
    @SneakyThrows(Exception.class)
    public static Field getFieldAll(Class<?> targetClass, String name) {
        Class findClass = targetClass;
        while (findClass != null && findClass != Object.class) {
            for (Field field : findClass.getDeclaredFields()) {
                if (field.getName().equals(name)) return field;
            }

            findClass = findClass.getSuperclass();
        }

        return null;
    }

    /**
     * 해당 Class의 부모 Class를 포함한 모든 Field를 반환합니다.
     * Object의 Field는 제외합니다.
     * @param targetClass Class
     * @return Field 목록
     */
    public static List<Field> getAllField(Class<?> targetClass) {
        List<Field> fields = new ArrayList<>();

        while (targetClass != null && targetClass != Object.class) {
            for (Field field : targetClass.getDeclaredFields()) {
                fields.add(field);
            }

            targetClass = targetClass.getSuperclass();
        }

        return fields;
    }

    /**
     * 해당 Class의 모든 Method를 소스 코드에 기재된 순서대로 불러옵니다.
     * @param targetClass Class
     * @return 소스 코드 순서대로 정렬된 Method 목록
     */
    @SneakyThrows(Exception.class)
    public static Collection<Method> getMethodsInOrder(Class targetClass) {
        Map<Integer, Method> methods = new TreeMap<>();

        try {
            String className = targetClass.getName().replace(".", "/") + ".class";
            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(targetClass.getClassLoader().getResourceAsStream(className)));

            String classData = "";
            String line;
            while ((line = reader.readLine()) != null) {
                classData += line;
            }

            for (Method method : targetClass.getDeclaredMethods()) {
                methods.put(classData.indexOf(method.getName()), method);
            }
        } catch (NoClassDefFoundError e) {
        }

        return methods.values();
    }

    /**
     * 해당 Plugin의 모든 Class를 반환합니다.
     * @param plugin Plugin
     * @return 모든 Class
     */
    @SneakyThrows
    public static List<Class> getClasses(CorePlugin plugin) {
        List<Class> classes = new ArrayList<>();

        ZipInputStream jarStream = new ZipInputStream(new FileInputStream(plugin.getFile()));
        ZipEntry item = null;

        while ((item = jarStream.getNextEntry()) != null) {
            if (item.isDirectory() || !item.getName().endsWith(".class")) continue;

            String className = item.getName().replace("/", ".").substring(0, item.getName().length() - 6);

            try {
                Class targetClass = Class.forName(className);
                classes.add(targetClass);
            } catch (Exception e) {
            } catch (Error e) {
            }
        }

        return classes;
    }

    /**
     * 해당 이름의 클래스의 존재 여부를 반환합니다.
     * @param className 존재 여부를 확인할 클래스명
     * @return 클래스 존재 여부
     */
    public static boolean existsClass(String className) {
        try {
            Class.forName(className);

            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * from 객체의 변수 값들을 to 객체로 복사합니다.
     * @return to
     */
    @SneakyThrows
    public static <T> T copyTo(T from, T to) {
        for (Field field : ReflectionUtil.getAllField(to.getClass())) {
            field.setAccessible(true);

            Object value = field.get(from);
            field.set(to, value);
        }

        return to;
    }

}
