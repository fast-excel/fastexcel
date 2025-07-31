package cn.idev.excel.write.handler;

import cn.idev.excel.annotation.validation.AutoIncrementId;
import cn.idev.excel.metadata.Head;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动递增ID处理器
 * 处理带有@AutoIncrementId注解的字段，自动生成递增序号
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
public class AutoIncrementIdHandler implements RowWriteHandler {

    private static final Logger logger = LoggerFactory.getLogger(AutoIncrementIdHandler.class);

    private int currentIndex = 0;

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                               Row row, Integer relativeRowIndex, Boolean isHead) {
        // 只处理数据行（表头之后的行）
        if (isHead != null && !isHead) {

            // 查找带有@AutoIncrementId注解的列
            for (int columnIndex = 0; columnIndex < Math.max(row.getLastCellNum(), 10); columnIndex++) {
                Head head = writeSheetHolder.getExcelWriteHeadProperty().getHeadMap().get(columnIndex);
                if (head != null && head.getField() != null && head.getField().isAnnotationPresent(AutoIncrementId.class)) {
                    AutoIncrementId annotation = head.getField().getAnnotation(AutoIncrementId.class);

                    // 重置计数器（第一行数据时）
                    if (relativeRowIndex != null && relativeRowIndex == 0 && annotation.start() != -1) {
                        currentIndex = annotation.start() - 1;
                    }

                    // 设置序号并递增
                    currentIndex++;

                    // 获取或创建单元格
                    Cell cell = row.getCell(columnIndex);
                    if (cell == null) {
                        cell = row.createCell(columnIndex);
                    }
                    cell.setCellValue(currentIndex);

                    logger.debug("为第{}行第{}列设置自动递增ID: {}",
                               row.getRowNum(), columnIndex, currentIndex);
                    break; // 每行只需处理第一个找到的序号字段
                }
            }
        }
    }
}
