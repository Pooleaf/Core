package net.pooleaf.core.modules.commonsender.common.sql;

import java.util.UUID;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.sqllib.common.AbstractSqlManager;
import net.pooleaf.core.modules.sqllib.common.SqlDao;
import net.pooleaf.core.modules.sqllib.common.SqlTable;

public class CommonPlayerDao extends SqlDao {

    private SqlTable playerInfoTable;


    public CommonPlayerDao(AbstractSqlManager sqlManager) {
        super(sqlManager);
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


    public CommonPlayer selectPlayerInfoByUuid(UUID uuid) {
        return (CommonPlayer) playerInfoTable.select()
                .where("uuid = ?")
                .parameters(uuid)
                .execute(CommonPlayer.class);
    }

    public CommonPlayer selectPlayerInfoByName(String name) {
        return (CommonPlayer) playerInfoTable.select()
                .where("name = ?")
                .parameters(name)
                .execute(CommonPlayer.class);
    }

    public CommonPlayer selectPlayerInfoByDisplayName(String displayName) {
        return (CommonPlayer) playerInfoTable.select()
                .where("REGEXP_REPLACE(display_name, '§(?i)[0-9|a-f|k-o|r]', '') = ?")
                .parameters(displayName)
                .execute(CommonPlayer.class);
    }

    public void insertPlayerInfo(CommonPlayer playerInfo) {
        playerInfoTable.insertInto()
                .valuesByObject(playerInfo)
                .onDuplicateKeyUpdate()
                .execute();
    }

}
