package net.pooleaf.core.test;

import lombok.Data;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.annoconfig.common.SimpleAnnoConfig;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigName;

import java.io.File;
import java.util.*;

@Data
public class TestConfig extends SimpleAnnoConfig {

    @ConfigName("테스트이름")
    private String testName = "기본이름";

    private List<String> testList = new ArrayList<>();

    public TestConfig() {
        super(new File(Core.getPlugin().getDataFolder(), "config.yml"));

        testList.add("ㅇㅇㅇ123lsit");
    }

}
