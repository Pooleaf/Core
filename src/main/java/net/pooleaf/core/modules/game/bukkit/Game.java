package net.pooleaf.core.modules.game.bukkit;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Game<T extends GamePlayer> {

    private Long id;
    private String name;

    private List<T> joinedPlayers = new ArrayList<>();

    private LocalDateTime gameStartTime;

}
