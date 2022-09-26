# SqlLibModule

DB에 간편하게 데이터를 저장하고 불러올 수 있는 모듈입니다.

## 예시
### TestPlugin
```
public class SqlManager extends JavaPlugin {
    
    private TestSqlManager sqlManager;
    
    
    @Override
    public void onEnable() {
        sqlManager = new TestSqlManager(this);
        
        sqlManager.connect();
    }
    
    @Override
    public void onDisable() {
        sqlManager.close();
    }
    
}
```
### SqlManager
```
public class TestSqlManager extends AbstractSqlManager {

  private PlayerInfoDao playerInfoDao = new PlayerInfoDao(this);


  public TestSqlManager(CorePlugin plugin) {
    super(plugin);
  }


  public PlayerInfoDao playerInfo() {
    return player;
  }

}
```
### PlayerInfoDao
```
public class PlayerInfoDao extends SqlDao {

    private SqlTable playerInfoTable;


    public CommonPlayerDao(AbstractSqlManager sqlManager) {
        super(sqlManager);
    }
    

    @Override
    public void onConnected() {
        playerInfoTable = new SqlTable(sqlManager, "players"
                , "uuid VARCHAR(36) PRIMARY KEY"
                , "name VARCHAR(16)"
                , "ip VARCHAR(15)"
                , "last_login DATETIME").create();
    }


    public PlayerInfo selectPlayerInfo(UUID uuid) {
        return playerInfoTable.select()
                .where("uuid = ?")
                .parameters(uuid)
                .execute(PlayerInfo.class);
    }

    public void insertPlayerInfo(PlayerInfo playerInfo) {
        playerInfoTable.insertInto()
                .valuesByObject(playerInfo)
                .onDuplicateKeyUpdate()
                .execute();
    }

}
```