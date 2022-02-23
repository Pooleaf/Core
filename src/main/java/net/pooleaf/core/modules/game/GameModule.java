package net.pooleaf.core.modules.game;

import lombok.Getter;
import lombok.Setter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.game.bukkit.map.GameMap;
import net.pooleaf.core.modules.game.bukkit.map.GameMapManager;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayerManager;
import net.pooleaf.core.plugin.CorePlugin;

public class GameModule extends CoreModule {

  @Setter
  @Getter
  private static GamePlayerManager<GamePlayer> gamePlayerManager = new GamePlayerManager<>();

  @Setter
  @Getter
  private static GameMapManager<GameMap> gameMapManager = new GameMapManager<>();


  @Override
  public String getName() {
    return "Game";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {
  }

}
