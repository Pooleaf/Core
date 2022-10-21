package net.pooleaf.core.modules.support.common.util;

import com.google.common.base.Preconditions;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
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
import java.util.*;
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
        return targetClass.getDeclaredField(name);
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
     * @param targetClass 메소드를 불러올 Class
     * @return 소스 코드 순서대로 정렬된 Method 목록
     */
    @SneakyThrows(Exception.class)
    public static Collection<Method> getMethodsInOrderLightly(Class targetClass) {
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
        } catch (NullPointerException e) {
        } catch (NoClassDefFoundError e) {
        }

        return methods.values();
    }


    private static ClassPool classPool;

    /**
     * Javassist를 사용하여 해당 Class의 모든 Method를 소스 코드에 기재된 순서대로 불러옵니다.
     * 속도는 느리나 정확한 순서대로 불러올 수 있습니다.
     * @param targetClass 메소드를 불러올 Class
     * @return 소스 코드 순서대로 정렬된 Method 목록
     */
    @SneakyThrows
    public static List<Method> getMethodsInOrder(File targetFile, Class targetClass) {
        List<Method> methods = new ArrayList<>();

        if (classPool == null) {
            classPool = ClassPool.getDefault();
            classPool.appendClassPath(targetFile.getAbsolutePath()); // TODO 고쳐야함
        }
        for (CtMethod method : classPool.get(targetClass.getCanonicalName()).getDeclaredMethods()) {
            String methodName = method.getName();

            List<Class> parameterTypes = new ArrayList<>();
            for (CtClass parameterType : method.getParameterTypes()) {
                String parameterClassName = parameterType.getName();
                Class parameterClass = parseType(parameterClassName);
                parameterTypes.add(parameterClass);
            }

            Method targetMethod = targetClass.getMethod(methodName, parameterTypes.toArray(new Class[0]));
            methods.add(targetMethod);
        }

        return methods;
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

            Object value = field.get(from);
            if (Cloneable.class.isAssignableFrom(value.getClass())) {
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
