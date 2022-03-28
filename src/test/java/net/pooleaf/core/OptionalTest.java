package net.pooleaf.core;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionalTest {

    @Test
    public void test() {
        Map<Integer, TestObject> testObjects = new HashMap<>();

        System.out.println(Optional.ofNullable(testObjects.get(1))
                .map(TestObject::getName)
                .orElse(null));
    }


    @Data
    public class TestObject {

        private String name;

    }

}
