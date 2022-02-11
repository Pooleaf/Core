package net.pooleaf.core.modules.playerinfo.sql;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.playerinfo.PlayerInfo;
import net.pooleaf.core.modules.sqlib.SqlDao;
import net.pooleaf.core.modules.sqlib.SqlTable;

import java.util.UUID;

public class PlayerInfoDao extends SqlDao {

    private SqlTable playerInfoTable;


    public PlayerInfoDao() {
        super(Core.getCoreSqlManager());
    }

    @Override
    public void onConnected() {
        playerInfoTable = new SqlTable(sqlManager, "core_player_infos"
                , "uuid VARCHAR(36) PRIMARY KEY"
                , "name VARCHAR(16)"
                , "display_name VARCHAR(255)"
                , "ip VARCHAR(15)"
                , "last_login DATETIME").create();
    }


    public PlayerInfo selectPlayerInfoByUuid(UUID uuid) {
        return (PlayerInfo) playerInfoTable.select()
                .where("uuid = ?")
                .parameters(uuid)
                .execute(PlayerInfo.class).get(0);
    }

    public PlayerInfo selectPlayerInfoByName(String name) {
        return (PlayerInfo) playerInfoTable.select()
                .where("name = ?")
                .parameters(name)
                .execute(PlayerInfo.class).get(0);
    }

    public PlayerInfo selectPlayerInfoByDisplayName(String displayName) {
        return (PlayerInfo) playerInfoTable.select()
                .where("REGEXP_REPLACE(display_name, '§(?i)[0-9|a-f|k-o|r]', '') = ?") // TODO
                .parameters(displayName)
                .execute(PlayerInfo.class).get(0);
    }

    public void insertPlayerInfo(PlayerInfo playerInfo) {
        playerInfoTable.insertInto()
                .valuesByObject(playerInfo)
                .onDuplicateKeyUpdate()
                .execute();
    }

}
