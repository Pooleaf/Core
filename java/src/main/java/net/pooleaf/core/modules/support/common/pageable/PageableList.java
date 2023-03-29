package net.pooleaf.core.modules.support.common.pageable;

import com.google.common.base.Preconditions;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class PageableList<T> {

    private int valueCount; // 값 개수
    private int countPerPage; // 한 페이지당 값 개수
    private int maxPage; // 마지막 페이지


    public PageableList(int valueCount, int countPerPage) {
        Preconditions.checkArgument(countPerPage > 0, "countPerPage는 1보다 작을 수 없습니다.");

        this.valueCount = valueCount;
        this.countPerPage = countPerPage;
        this.maxPage = (int) Math.ceil((float) valueCount / countPerPage);
    }


    /**
     * 해당 페이지에 해당하는 값들을 불러옵니다.
     * @param page 불러올 페이지
     * @return 해당 페이지에 해당하는 값
     */
    public List<T> getPage(int page) {
        List<T> pageValues = new ArrayList<>();

        // maxPage보다 큰 page일 경우 빈 List를 반환
        if (page > getMaxPage()) {
            return pageValues;
        }

        for (int i = (page - 1) * countPerPage; i < page * countPerPage; i++) {
            if (i >= getValueCount()) {
                break;
            }

            pageValues.add(getValue(i));
        }

        return pageValues;
    }

    /**
     * 해당 페이지에 해당하는 Index들을 불러옵니다.
     * @param page 불러올 페이지
     * @return 해당 페이지에 해당하는 Index
     */
    public List<Integer> getPageIndexes(int page) {
        List<Integer> pageIndexes = new ArrayList<>();

        // maxPage보다 큰 page일 경우 빈 List를 반환
        if (page > getMaxPage()) {
            return pageIndexes;
        }

        for (int i = (page - 1) * countPerPage; i < page * countPerPage; i++) {
            if (i >= getValueCount()) {
                break;
            }

            pageIndexes.add(i);
        }

        return pageIndexes;
    }

    /**
     * index번째 값을 반환합니다.
     * @param index 번쨰
     * @return index번째 값
     */
    public abstract T getValue(int index);

}