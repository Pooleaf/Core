package net.pooleaf.core.modules.annoconfig.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigSerialize {

  Class<? extends ConfigSerializer> value();

  interface ConfigSerializer<T> {
    String serialize(T value);
    T deserialize(String value);
  }

}