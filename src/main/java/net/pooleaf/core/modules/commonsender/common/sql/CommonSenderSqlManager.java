package net.pooleaf.core.modules.commonsender.common.sql;

import java.io.File;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.sqllib.common.AbstractSqlManager;

public class CommonSenderSqlManager extends AbstractSqlManager {

  private CommonPlayerDao commonPlayerDao = new CommonPlayerDao(this);


  public CommonSenderSqlManager() {
    super(Core.getPlugin());

    getConfig().setFile(new File(Core.getPlugin().getDataFolder(), "commonsender-sql-config.yml"));
  }


  public CommonPlayerDao commonPlayer() {
    return commonPlayerDao;
  }

}
