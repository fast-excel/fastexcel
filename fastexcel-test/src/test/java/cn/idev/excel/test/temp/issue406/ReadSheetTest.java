package cn.idev.excel.test.temp.issue406;

import cn.idev.excel.read.metadata.ReadSheet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ReadSheetTest {

    @Test
    void testAll() {
        testEquals_SameInstance();
        testEquals_Null();
        testEquals_DifferentClass();
        testEquals_EqualObjects();

        testHashCode_Consistency();
        testHashCode_EqualityForEqualObjects();

        // If @EqualsAndHashCode is not implemented, the following error will occur
        testEquals_DifferentSheetNo();
        testEquals_DifferentSheetName();
        testEquals_DifferentNumRows();
        testEquals_DifferentHiddenState();
        testEquals_DifferentVeryHiddenState();
    }


    @Test
    void testEquals_SameInstance() {
        ReadSheet sheet = new ReadSheet(1, "Sheet1");
        Assertions.assertTrue(sheet.equals(sheet));
    }

    @Test
    void testEquals_Null() {
        ReadSheet sheet = new ReadSheet(1, "Sheet1");
        assertFalse(sheet.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        ReadSheet sheet = new ReadSheet(1, "Sheet1");
        assertFalse(sheet.equals("Not a ReadSheet object"));
    }

    @Test
    void testEquals_EqualObjects() {
        ReadSheet sheet1 = new ReadSheet(1, "Sheet1");
        sheet1.setNumRows(100);
        sheet1.setHidden(false);
        sheet1.setVeryHidden(true);

        ReadSheet sheet2 = new ReadSheet(1, "Sheet1");
        sheet2.setNumRows(100);
        sheet2.setHidden(false);
        sheet2.setVeryHidden(true);

        Assertions.assertTrue(sheet1.equals(sheet2));
    }

    @Test
    void testEquals_DifferentSheetNo() {
        ReadSheet sheet1 = new ReadSheet(1, "Sheet1");
        ReadSheet sheet2 = new ReadSheet(2, "Sheet1");
        Assertions.assertFalse(sheet1.equals(sheet2));
    }

    @Test
    void testEquals_DifferentSheetName() {
        ReadSheet sheet1 = new ReadSheet(1, "Sheet1");
        ReadSheet sheet2 = new ReadSheet(1, "Sheet2");
        Assertions.assertFalse(sheet1.equals(sheet2));
    }

    @Test
    void testEquals_DifferentNumRows() {
        ReadSheet sheet1 = new ReadSheet(1, "Sheet1");
        sheet1.setNumRows(100);
        ReadSheet sheet2 = new ReadSheet(1, "Sheet1");
        sheet2.setNumRows(200);
        Assertions.assertFalse(sheet1.equals(sheet2));
    }

    @Test
    void testEquals_DifferentHiddenState() {
        ReadSheet sheet1 = new ReadSheet(1, "Sheet1");
        sheet1.setHidden(true);
        ReadSheet sheet2 = new ReadSheet(1, "Sheet1");
        sheet2.setHidden(false);
        Assertions.assertFalse(sheet1.equals(sheet2));
    }

    @Test
    void testEquals_DifferentVeryHiddenState() {
        ReadSheet sheet1 = new ReadSheet(1, "Sheet1");
        sheet1.setVeryHidden(true);
        ReadSheet sheet2 = new ReadSheet(1, "Sheet1");
        sheet2.setVeryHidden(false);
        Assertions.assertFalse(sheet1.equals(sheet2));
    }

    @Test
    void testHashCode_Consistency() {
        ReadSheet sheet = new ReadSheet(1, "Sheet1");
        int initialHashCode = sheet.hashCode();
        Assertions.assertEquals(initialHashCode, sheet.hashCode());
    }

    @Test
    void testHashCode_EqualityForEqualObjects() {
        ReadSheet sheet1 = new ReadSheet(1, "Sheet1");
        sheet1.setNumRows(100);
        sheet1.setHidden(false);
        sheet1.setVeryHidden(true);

        ReadSheet sheet2 = new ReadSheet(1, "Sheet1");
        sheet2.setNumRows(100);
        sheet2.setHidden(false);
        sheet2.setVeryHidden(true);

        Assertions.assertEquals(sheet1.hashCode(), sheet2.hashCode());
    }

}