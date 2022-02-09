package net.pooleaf.core.modules.sqlib;

import lombok.Data;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CachedResult {

    private List<Map<String, Object>> results = new ArrayList<>();


    @SneakyThrows
    public CachedResult(ResultSet resultSet) {
        while (resultSet.next()) {
            Map<String, Object> resultRow = new HashMap<>();

            for (int column = 0; column < resultSet.getMetaData().getColumnCount(); column++) {
                resultRow.put(resultSet.getMetaData().getColumnName(column), resultSet.getObject(column + 1));
            }

            results.add(resultRow);
        }
    }

    public Map<String, Object> getRow(int index) {
        return results.get(index);
    }

}
