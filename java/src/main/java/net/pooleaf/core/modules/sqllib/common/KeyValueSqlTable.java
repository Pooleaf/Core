package net.pooleaf.core.modules.sqllib.common;

public class KeyValueSqlTable extends SqlTable {

    public KeyValueSqlTable(AbstractSqlManager sqlManager, String name) {
        super(sqlManager, name,
                "name VARCHAR(255) PRIMARY KEY"
                , "value TEXT");
    }


    @Override
    public KeyValueSqlTable create() {
        return (KeyValueSqlTable) super.create();
    }

    public void set(String key, Object value) {
        if (value == null) {
            delete(key);
            return;
        }

        insertInto().values(key, value)
                .onDuplicateKeyUpdate()
                .execute();
    }

    public void delete(String key) {
        delete().where("name = ?")
                .parameters(key)
                .execute();
    }

    public void deleteAll() {
        delete().execute();
    }

    public Object get(String key) {
        CachedResult result = select().where("name = ?")
                .parameters(key)
                .execute();

        if (result.getRows().isEmpty()) {
            return null;
        }

        return result.getRow(0).get("value");
    }

    public String getString(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return String.valueOf(value);
    }

    public boolean getBoolean(String key) {
        String value = getString(key);

        if (value == null) {
            return false;
        }

        return Boolean.valueOf(value);
    }

    public int getInt(String key) {
        String value = getString(key);

        if (value == null) {
            return 0;
        }

        return Integer.valueOf(value);
    }

    public float getFloat(String key) {
        String value = getString(key);

        if (value == null) {
            return 0.0F;
        }

        return Float.valueOf(value);
    }

    public double getDouble(String key) {
        String value = getString(key);

        if (value == null) {
            return 0.0;
        }

        return Double.valueOf(value);
    }

}
