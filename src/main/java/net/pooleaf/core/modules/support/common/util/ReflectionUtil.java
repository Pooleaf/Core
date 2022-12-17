package net.pooleaf.core.modules.support.common.util;

import com.google.common.base.Preconditions;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.pooleaf.core.plugin.CorePlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        for (Field field : targetClass.getDeclaredFields()) {
            if (field.getName().equals(name)) {
                return field;
            }
        }

        return null;
    }

    /**
     * 해당 Class 및 부모 Class에서 해당 이름을 가진 Field를 반환합니다.
     * Object의 Field는 제외합니다.
     *
     * @deprecated {@link #getFieldFromAll(Class, String)}를 사용하세요.
     * @param targetClass Class
     * @param name 찾을 Field 이름
     * @return 찾은 Field
     */
    @Deprecated
    @SneakyThrows(Exception.class)
    public static Field getFieldAll(Class<?> targetClass, String name) {
        return getFieldFromAll(targetClass, name);
    }

    /**
     * 해당 Class 및 부모 Class에서 해당 이름을 가진 Field를 반환합니다.
     * Object의 Field는 제외합니다.
     * @param targetClass Class
     * @param name 찾을 Field 이름
     * @return 찾은 Field
     */
    @SneakyThrows(Exception.class)
    public static Field getFieldFromAll(Class<?> targetClass, String name) {
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
     * 해당 오브젝트의 필드 값을 변경합니다.
     * private 필드에도 사용할 수 있습니다.
     * @param targetObject 필드 값을 설정할 오브젝트
     * @param fieldName 필드 이름
     * @param value 변경할 값
     */
    @SneakyThrows
    public static void setFieldValue(Object targetObject, String fieldName, Object value) {
        Preconditions.checkNotNull(targetObject);

        Field field = getFieldFromAll(targetObject.getClass(), fieldName);
        Preconditions.checkNotNull(field);

        field.setAccessible(true);
        field.set(targetObject, value);
    }

    /**
     * 해당 클래스의 static 필드 값을 변경합니다.
     * private 필드에도 사용할 수 있습니다.
     * @param targetClass 필드 값을 설정할 클래스
     * @param fieldName 필드 이름
     * @param value 변경할 값
     */
    @SneakyThrows
    public static void setStaticFieldValue(Class<?> targetClass, String fieldName, Object value) {
        Preconditions.checkNotNull(targetClass);

        Field field = getFieldFromAll(targetClass, fieldName);
        Preconditions.checkNotNull(field);

        field.setAccessible(true);
        field.set(null, value);
    }

    /**
     * 해당 Class의 모든 Method를 소스 코드에 기재된 순서대로 불러옵니다.
     * 속도는 빠르나 잘못된 순서로 불러오거나 불러오지 못할 수 있습니다.
     *
     * Companion Class가 있을 경우 Companion Class의 Method도 함께 불러옵니다. (Kotlin companion object를 위해)
     * Companion Class의 Method는 해당 Class의 Method보다 먼저 배치됩니다.
     * @param targetClass 메소드를 불러올 Class
     * @return 소스 코드 순서대로 정렬된 Method 목록
     */
    @SneakyThrows(Exception.class)
    public static List<Method> getMethodsInOrderLightly(Class targetClass) {
        List<Method> methods = new ArrayList<>();

        try {
            // Companion Class 메소드 불러오기
            String companionClassName = targetClass.getName().replace(".", "/") + "$Companion";
            methods.addAll(getMethodsInOrderLightly(companionClassName, targetClass.getClassLoader()));

            // 해당 Class 메소드 불러오기
            methods.addAll(getMethodsInOrderLightly(targetClass.getName(), targetClass.getClassLoader()));
        } catch (NullPointerException e) {
        } catch (NoClassDefFoundError e) {
        }

        return methods;
    }

    /**
     * 해당 Class의 모든 Method를 소스 코드에 기재된 순서대로 불러옵니다.
     * 속도는 빠르나 잘못된 순서로 불러오거나 불러오지 못할 수 있습니다.
     * @param targetClassName 메소드를 불러올 Class 이름 (.class 미포함)
     * @return 소스 코드 순서대로 정렬된 Method 목록
     */
    @SneakyThrows(Exception.class)
    public static List<Method> getMethodsInOrderLightly(String targetClassName, ClassLoader classLoader) {
        Map<Integer, Method> methods = new TreeMap<>();

        try {
            Class targetClass = Class.forName(targetClassName);
            targetClassName = targetClassName.replace(".", "/") + ".class";

            // 클래스 읽어오기
            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(targetClassName)));

            String classData = "";
            String line;
            while ((line = reader.readLine()) != null) {
                classData += line;
            }

            // 메소드 첫번째 위치 찾기 (첫번째 위치를 찾기 때문에 메소드 안에서 메소드를 사용했을 경우 순서가 이상할 수 있음)
            for (Method method : targetClass.getDeclaredMethods()) {
                methods.put(classData.indexOf(method.getName()), method);
            }
        } catch (Exception e) {
        } catch (Error e) {
        }

        return methods.values().stream().collect(Collectors.toList());
    }

    @SneakyThrows
    public static Class<?> parseType(String className) {
        switch (className) {
            case "boolean":
                return boolean.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "char":
                return char.class;
            case "void":
                return void.class;
            default:
                return Class.forName(className);
        }
    }

    /**
     * 해당 파일의 모든 Class를 반환합니다.
     * @param file File
     * @return 모든 Class
     */
    @SneakyThrows
    public static List<Class> getClasses(File file) {
        List<Class> classes = new ArrayList<>();

        ZipInputStream jarStream = new ZipInputStream(new FileInputStream(file));
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
     * 해당 CorePlugin의 모든 Class를 반환합니다.
     * @param plugin Plugin
     * @return 모든 Class
     */
    @SneakyThrows
    public static List<Class> getClasses(CorePlugin plugin) {
        return getClasses(plugin.getFile());
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
     * 플러그인의 File을 반환합니다.
     * @return 플러그인의 File
     */
    @SneakyThrows
    public static File getFile(Object plugin) {
        Field fileField = getFieldAll(plugin.getClass(), "file");
        fileField.setAccessible(true);
        return (File) fileField.get(plugin);
    }

    /**
     * from 객체의 변수 값들을 to 객체로 복사합니다.
     * @return to
     */
    @SneakyThrows
    public static <T> T copyTo(T from, T to) {
        for (Field field : ReflectionUtil.getAllField(to.getClass())) {
            field.setAccessible(true);

            Field fromField = getField(from.getClass(), field.getName());
            if (fromField == null) {
                continue;
            }
            fromField.setAccessible(true);

            Object value = fromField.get(from);
            if (value != null && Cloneable.class.isAssignableFrom(value.getClass())) {
                Method cloneMethod = getMethod(value.getClass(), "clone");
                value = cloneMethod.invoke(value);
            }

            field.set(to, value);
        }

        return to;
    }

    /**
     * 해당 필드의 final 상태를 해제합니다.
     * @param field final을 해제할 Field
     */
    @SneakyThrows
    public static Field removeFinal(Field field) {
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);

        return field;
    }

}
