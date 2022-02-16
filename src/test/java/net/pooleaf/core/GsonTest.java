package net.pooleaf.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GsonTest {

    @Test
    public void test() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Map.class, new MapDeserializer()).serializeNulls().create();
//        Gson gson = new Gson();

        TestObject test = new TestObject("name123", "testdata", 123, 1000.24, new HashMap<>());
        test.testMap.put("testInt", 1000);
        test.testMap.put("testInt2", 9999);
        test.testMap.put("testDouble", 99.12);
        test.testMap.put("testDouble2", 99.0);
        System.out.println("test: " + test);
        String testJson = gson.toJson(test);
        System.out.println("testJson: " + testJson);

        TestObject loadedTest = gson.fromJson(testJson, TestObject.class);
        System.out.println("loadedTest: " + loadedTest);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("testInt", 100);
        System.out.println("jsonObject toString: " + jsonObject.toString());
        System.out.println("jsonObject toJson: " + gson.toJson(jsonObject));
    }


    @Data
    @AllArgsConstructor
    class TestObject {

        private String name;
        private String data;
        private int count;
        private double money;

        Map<String, Object> testMap = new HashMap<>();

    }

}
