package cn.idev.excel.metadata.data;

import java.util.List;

import cn.idev.excel.util.ListUtils;
import cn.idev.excel.write.metadata.style.WriteFont;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * rich text string
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RichTextStringData {
    private String textString;
    private WriteFont writeFont;
    private List<IntervalFont> intervalFontList;

    public RichTextStringData(String textString) {
        this.textString = textString;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class IntervalFont {
        private Integer startIndex;
        private Integer endIndex;
        private WriteFont writeFont;
    }

    /**
     * Applies a font to the specified characters of a string.
     *
     * @param startIndex The start index to apply the font to (inclusive)
     * @param endIndex   The end index to apply to font to (exclusive)
     * @param writeFont  The font to use.
     */
    public void applyFont(int startIndex, int endIndex, WriteFont writeFont) {
        if (intervalFontList == null) {
            intervalFontList = ListUtils.newArrayList();
        }
        intervalFontList.add(new IntervalFont(startIndex, endIndex, writeFont));
    }

    /**
     * Sets the font of the entire string.
     *
     * @param writeFont The font to use.
     */
    public void applyFont(WriteFont writeFont) {
        this.writeFont = writeFont;
    }

}
