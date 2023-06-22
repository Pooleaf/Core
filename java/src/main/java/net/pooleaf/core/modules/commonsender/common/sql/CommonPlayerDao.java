package net.pooleaf.core.modules.commonsender.common.sql;

import lombok.Getter;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.sqllib.common.AbstractSqlManager;
import net.pooleaf.core.modules.sqllib.common.SqlDao;
import net.pooleaf.core.modules.sqllib.common.SqlTable;

import java.util.List;
import java.util.UUID;

@Getter
public class CommonPlayerDao extends SqlDao {

    private SqlTable playerTable;


    public CommonPlayerDao(AbstractSqlManager sqlManager) {
        super(sqlManager);
    }

    @Override
    public void onConnected() {
        playerTable = new SqlTable(sqlManager, "core_players"
                , "uuid VARCHAR(36) PRIMARY KEY"
                , "name VARCHAR(16)"
                , "display_name VARCHAR(255)"
                , "ip VARCHAR(15)"
                , "last_login DATETIME"
                , "last_online DATETIME").create();
    }


    public <T extends CommonPlayer> T selectPlayerInfoByUuid(UUID uuid, Class<T> playerClass) {
        return playerTable.select()
                .where("uuid = ?")
                .parameters(uuid)
                .execute(playerClass);
    }

    public <T extends CommonPlayer> T selectPlayerInfoByName(String name, Class<T> playerClass) {
        return playerTable.select()
                .where("name = ?")
                .parameters(name)
                .execute(playerClass);
    }

    public <T extends CommonPlayer> T selectPlayerInfoByDisplayName(String displayName, Class<T> playerClass) {
        return playerTable.select()
                .where("REGEXP_REPLACE(display_name, '§(?i)[0-9|a-f|k-o|r]', '') = ?")
                .parameters(displayName)
                .execute(playerClass);
    }

    public <T extends CommonPlayer> List<T> selectPlayerInfosByIp(String ip, Class<T> playerClass) {
        return playerTable.select()
                .where("ip = ?")
                .parameters(ip)
                .executeList(playerClass);
    }

    public void insertPlayerInfo(CommonPlayer commonPlayer) {
        playerTable.insertInto()
                .valuesByObject(commonPlayer)
                .onDuplicateKeyUpdate()
                .execute();
    }

}
