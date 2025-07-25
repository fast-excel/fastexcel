package cn.idev.excel.write.style.column;

import cn.idev.excel.metadata.Head;

/**
 * All the columns are the same width
 *
 *
 */
public class SimpleColumnWidthStyleStrategy extends AbstractHeadColumnWidthStyleStrategy {
    private final Integer columnWidth;

    /**
     *
     * @param columnWidth
     */
    public SimpleColumnWidthStyleStrategy(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Override
    protected Integer columnWidth(Head head, Integer columnIndex) {
        return columnWidth;
    }
}
