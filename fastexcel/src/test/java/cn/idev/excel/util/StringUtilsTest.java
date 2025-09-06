package cn.idev.excel.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    void stripTest() {
        Assertions.assertNull(StringUtils.strip(null));
        Assertions.assertEquals("", StringUtils.strip(""));
        Assertions.assertEquals("", StringUtils.strip("   "));
        Assertions.assertEquals("abc", StringUtils.strip("abc"));
        Assertions.assertEquals("abc", StringUtils.strip("  abc"));
        Assertions.assertEquals("abc", StringUtils.strip("abc  "));
        Assertions.assertEquals("abc", StringUtils.strip(" abc "));
        Assertions.assertEquals("abc", StringUtils.strip("　abc　"));
        Assertions.assertEquals("abc", StringUtils.strip(" abc　"));
        Assertions.assertEquals("ab　c", StringUtils.strip(" ab　c　"));
        Assertions.assertEquals("ab c", StringUtils.strip(" ab c "));
    }

    @Test
    void isBlankCharTest() {
        Assertions.assertTrue(StringUtils.isBlankChar(' '));
        Assertions.assertTrue(StringUtils.isBlankChar('　'));
        Assertions.assertTrue(StringUtils.isBlankChar('\ufeff'));
        Assertions.assertTrue(StringUtils.isBlankChar('\u202a'));
        Assertions.assertTrue(StringUtils.isBlankChar('\u3164'));
        Assertions.assertTrue(StringUtils.isBlankChar('\u2800'));
        Assertions.assertTrue(StringUtils.isBlankChar('\u2800'));
        Assertions.assertTrue(StringUtils.isBlankChar('\u180e'));
    }
}
