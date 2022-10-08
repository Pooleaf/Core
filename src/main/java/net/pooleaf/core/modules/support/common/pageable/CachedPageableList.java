package net.pooleaf.core.modules.support.common.pageable;

import lombok.Data;

import java.util.List;

@Data
public class CachedPageableList<T> extends PageableList<T> {

    private List<T> values; // 페이지에 들어갈 값들


    public CachedPageableList(List<T> values, int countPerPage) {
        super(values.size(), countPerPage);

        this.values = values;
    }

    @Override
    public T getValue(int index) {
        return values.get(index);
    }

}