package cn.idev.excel.write.handler;

import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.Head;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteTableHolder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;

/**
 * A cell write handler that escapes _x[0-9A-Fa-f]{4}_ format strings to prevent POI from automatically decoding them.
 * <p>
 * In Office Open XML, _xHHHH_ format is used to encode special characters. For example, _x000D_ represents the Unicode
 * character 0D (carriage return).
 * <p>
 * To store the literal _xHHHH_ sequence without it being decoded by POI, we need to escape the initial underscore by
 * replacing _x with _x005F_x.
 */
public class EscapeHexCellWriteHandler implements CellWriteHandler {

    private static final Pattern HEX_PATTERN = Pattern.compile("_x([0-9A-Fa-f]{4})_");

    @Override
    public void afterCellDataConverted(
            WriteSheetHolder writeSheetHolder,
            WriteTableHolder writeTableHolder,
            WriteCellData<?> cellData,
            Cell cell,
            Head head,
            Integer relativeRowIndex,
            Boolean isHead) {
        // Only process cell data of string type
        if (cellData != null && cellData.getType() == CellDataTypeEnum.STRING) {
            String originalString = cellData.getStringValue();
            if (originalString != null && HEX_PATTERN.matcher(originalString).find()) {
                String escapedString = escapeHex(originalString);
                cellData.setStringValue(escapedString);
            }
        }
    }

    /**
     * Escapes hexadecimal-encoded strings
     * Replaces _xHHHH_ with _x005F_xHHHH_ to prevent POI from decoding them
     */
    private String escapeHex(String originalString) {
        Matcher matcher = HEX_PATTERN.matcher(originalString);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            // Replace _xHHHH_ with _x005F_xHHHH_
            matcher.appendReplacement(sb, "_x005F_x" + matcher.group(1) + "_");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    @Override
    public void afterCellCreate(
            WriteSheetHolder writeSheetHolder,
            WriteTableHolder writeTableHolder,
            Cell cell,
            Head head,
            Integer relativeRowIndex,
            Boolean isHead) {}

    @Override
    public void afterCellDispose(
            WriteSheetHolder writeSheetHolder,
            WriteTableHolder writeTableHolder,
            List<WriteCellData<?>> cellDataList,
            Cell cell,
            Head head,
            Integer relativeRowIndex,
            Boolean isHead) {}
}
