package net.pooleaf.core;

import net.pooleaf.core.modules.game.bukkit.map.GameMap;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class MapSortTest {

    @Test
    public void test() {
        Map<UUID, GameMap> votedTo = new HashMap<>();

        GameMap map1 = new GameMap();
        map1.setName("map1");
        GameMap map2 = new GameMap();
        map2.setName("map2");
        GameMap map3 = new GameMap();
        map3.setName("map3");

        votedTo.put(UUID.randomUUID(), map1);
        votedTo.put(UUID.randomUUID(), map1);
        votedTo.put(UUID.randomUUID(), map2);
        votedTo.put(UUID.randomUUID(), map2);
        votedTo.put(UUID.randomUUID(), map2);
        votedTo.put(UUID.randomUUID(), map2);
        votedTo.put(UUID.randomUUID(), map2);
        votedTo.put(UUID.randomUUID(), map2);
        votedTo.put(UUID.randomUUID(), map3);
        votedTo.put(UUID.randomUUID(), map3);
        votedTo.put(UUID.randomUUID(), map3);

        Map<GameMap, Integer> amounts = new HashMap<>();
        for (Map.Entry<UUID, GameMap> entry : votedTo.entrySet()) {
            amounts.put(entry.getValue(), amounts.computeIfAbsent(entry.getValue(), (map) -> 0) + 1);
        }

        List<Map.Entry<GameMap, Integer>> list = amounts.entrySet().stream().sorted(new Comparator<Map.Entry<GameMap, Integer>>() {
            @Override
            public int compare(Map.Entry<GameMap, Integer> o1, Map.Entry<GameMap, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        }).collect(Collectors.toList());

        System.out.println(list);
    }

}
