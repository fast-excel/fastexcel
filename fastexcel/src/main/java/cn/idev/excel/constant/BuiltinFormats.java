package cn.idev.excel.constant;

import cn.idev.excel.util.MapUtils;
import cn.idev.excel.util.StringUtils;
import java.util.Locale;
import java.util.Map;

/**
 * Excel's built-in format conversion.Currently only supports Chinese.
 *
 * <p>
 * If it is not Chinese, it is recommended to directly modify the builtinFormats, which will better support
 * internationalization in the future.
 *
 * <p>
 * Specific correspondence please see:
 * https://docs.microsoft.com/en-us/dotnet/api/documentformat.openxml.spreadsheet.numberingformat?view=openxml-2.8.1
 *
 *
 **/
public class BuiltinFormats {

    private static final String RESERVED = "reserved-";

    public static short GENERAL = 0;

    public static final String[] BUILTIN_FORMATS_ALL_LANGUAGES = {
        // 0
        "General",
        // 1
        "0",
        // 2
        "0.00",
        // 3
        "#,##0",
        // 4
        "#,##0.00",
        // 5
        "\"￥\"#,##0_);(\"￥\"#,##0)",
        // 6
        "\"￥\"#,##0_);[Red](\"￥\"#,##0)",
        // 7
        "\"￥\"#,##0.00_);(\"￥\"#,##0.00)",
        // 8
        "\"￥\"#,##0.00_);[Red](\"￥\"#,##0.00)",
        // 9
        "0%",
        // 10
        "0.00%",
        // 11
        "0.00E+00",
        // 12
        "# ?/?",
        // 13
        "# ??/??",
        // 14
        // The official documentation shows "m/d/yy", but the actual test is "yyyy/m/d".
        "yyyy/m/d",
        // 15
        "d-mmm-yy",
        // 16
        "d-mmm",
        // 17
        "mmm-yy",
        // 18
        "h:mm AM/PM",
        // 19
        "h:mm:ss AM/PM",
        // 20
        "h:mm",
        // 21
        "h:mm:ss",
        // 22
        // The official documentation shows "m/d/yy h:mm", but the actual test is "yyyy-m-d h:mm".
        "yyyy-m-d h:mm",
        // 23-36 No specific correspondence found in the official documentation.
        // 23
        null,
        // 24
        null,
        // 25
        null,
        // 26
        null,
        // 27
        null,
        // 28
        null,
        // 29
        null,
        // 30
        null,
        // 31
        null,
        // 32
        null,
        // 33
        null,
        // 34
        null,
        // 35
        null,
        // 36
        null,
        // 37
        "#,##0_);(#,##0)",
        // 38
        "#,##0_);[Red](#,##0)",
        // 39
        "#,##0.00_);(#,##0.00)",
        // 40
        "#,##0.00_);[Red](#,##0.00)",
        // 41
        "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)",
        // 42
        "_(\"￥\"* #,##0_);_(\"￥\"* (#,##0);_(\"￥\"* \"-\"_);_(@_)",
        // 43
        "_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)",
        // 44
        "_(\"￥\"* #,##0.00_);_(\"￥\"* (#,##0.00);_(\"￥\"* \"-\"??_);_(@_)",
        // 45
        "mm:ss",
        // 46
        "[h]:mm:ss",
        // 47
        "mm:ss.0",
        // 48
        "##0.0E+0",
        // 49
        "@",
    };

    public static final String[] BUILTIN_FORMATS_CN = {
        // 0
        "General",
        // 1
        "0",
        // 2
        "0.00",
        // 3
        "#,##0",
        // 4
        "#,##0.00",
        // 5
        "\"￥\"#,##0_);(\"￥\"#,##0)",
        // 6
        "\"￥\"#,##0_);[Red](\"￥\"#,##0)",
        // 7
        "\"￥\"#,##0.00_);(\"￥\"#,##0.00)",
        // 8
        "\"￥\"#,##0.00_);[Red](\"￥\"#,##0.00)",
        // 9
        "0%",
        // 10
        "0.00%",
        // 11
        "0.00E+00",
        // 12
        "# ?/?",
        // 13
        "# ??/??",
        // 14
        // The official documentation shows "m/d/yy", but the actual test is "yyyy/m/d".
        "yyyy/m/d",
        // 15
        "d-mmm-yy",
        // 16
        "d-mmm",
        // 17
        "mmm-yy",
        // 18
        "h:mm AM/PM",
        // 19
        "h:mm:ss AM/PM",
        // 20
        "h:mm",
        // 21
        "h:mm:ss",
        // 22
        // The official documentation shows "m/d/yy h:mm", but the actual test is "yyyy-m-d h:mm".
        "yyyy-m-d h:mm",
        // 23-26 No specific correspondence found in the official documentation.
        // 23
        null,
        // 24
        null,
        // 25
        null,
        // 26
        null,
        // 27
        "yyyy\"年\"m\"月\"",
        // 28
        "m\"月\"d\"日\"",
        // 29
        "m\"月\"d\"日\"",
        // 30
        "m-d-yy",
        // 31
        "yyyy\"年\"m\"月\"d\"日\"",
        // 32
        "h\"时\"mm\"分\"",
        // 33
        "h\"时\"mm\"分\"ss\"秒\"",
        // 34
        "上午/下午h\"时\"mm\"分\"",
        // 35
        "上午/下午h\"时\"mm\"分\"ss\"秒\"",
        // 36
        "yyyy\"年\"m\"月\"",
        // 37
        "#,##0_);(#,##0)",
        // 38
        "#,##0_);[Red](#,##0)",
        // 39
        "#,##0.00_);(#,##0.00)",
        // 40
        "#,##0.00_);[Red](#,##0.00)",
        // 41
        "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)",
        // 42
        "_(\"￥\"* #,##0_);_(\"￥\"* (#,##0);_(\"￥\"* \"-\"_);_(@_)",
        // 43
        "_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)",
        // 44
        "_(\"￥\"* #,##0.00_);_(\"￥\"* (#,##0.00);_(\"￥\"* \"-\"??_);_(@_)",
        // 45
        "mm:ss",
        // 46
        "[h]:mm:ss",
        // 47
        "mm:ss.0",
        // 48
        "##0.0E+0",
        // 49
        "@",
        // 50
        "yyyy\"年\"m\"月\"",
        // 51
        "m\"月\"d\"日\"",
        // 52
        "yyyy\"年\"m\"月\"",
        // 53
        "m\"月\"d\"日\"",
        // 54
        "m\"月\"d\"日\"",
        // 55
        "上午/下午h\"时\"mm\"分\"",
        // 56
        "上午/下午h\"时\"mm\"分\"ss\"秒\"",
        // 57
        "yyyy\"年\"m\"月\"",
        // 58
        "m\"月\"d\"日\"",
        // 59
        "t0",
        // 60
        "t0.00",
        // 61
        "t#,##0",
        // 62
        "t#,##0.00",
        // 63-66 No specific correspondence found in the official documentation.
        // 63
        null,
        // 64
        null,
        // 65
        null,
        // 66
        null,
        // 67
        "t0%",
        // 68
        "t0.00%",
        // 69
        "t# ?/?",
        // 70
        "t# ??/??",
        // 71
        "ว/ด/ปปปป",
        // 72
        "ว-ดดด-ปป",
        // 73
        "ว-ดดด",
        // 74
        "ดดด-ปป",
        // 75
        "ช:นน",
        // 76
        "ช:นน:ทท",
        // 77
        "ว/ด/ปปปป ช:นน",
        // 78
        "นน:ทท",
        // 79
        "[ช]:นน:ทท",
        // 80
        "นน:ทท.0",
        // 81
        "d/m/bb",
        // end
    };

    public static final String[] BUILTIN_FORMATS_US = {
        // 0
        "General",
        // 1
        "0",
        // 2
        "0.00",
        // 3
        "#,##0",
        // 4
        "#,##0.00",
        // 5
        "\"$\"#,##0_);(\"$\"#,##0)",
        // 6
        "\"$\"#,##0_);[Red](\"$\"#,##0)",
        // 7
        "\"$\"#,##0.00_);(\"$\"#,##0.00)",
        // 8
        "\"$\"#,##0.00_);[Red](\"$\"#,##0.00)",
        // 9
        "0%",
        // 10
        "0.00%",
        // 11
        "0.00E+00",
        // 12
        "# ?/?",
        // 13
        "# ??/??",
        // 14
        // The official documentation shows "m/d/yy", but the actual test is "yyyy/m/d".
        "yyyy/m/d",
        // 15
        "d-mmm-yy",
        // 16
        "d-mmm",
        // 17
        "mmm-yy",
        // 18
        "h:mm AM/PM",
        // 19
        "h:mm:ss AM/PM",
        // 20
        "h:mm",
        // 21
        "h:mm:ss",
        // 22
        // The official documentation shows "m/d/yy h:mm", but the actual test is "yyyy-m-d h:mm".
        "yyyy-m-d h:mm",
        // 23-26 No specific correspondence found in the official documentation.
        // 23
        null,
        // 24
        null,
        // 25
        null,
        // 26
        null,
        // 27
        "yyyy\"年\"m\"月\"",
        // 28
        "m\"月\"d\"日\"",
        // 29
        "m\"月\"d\"日\"",
        // 30
        "m-d-yy",
        // 31
        "yyyy\"年\"m\"月\"d\"日\"",
        // 32
        "h\"时\"mm\"分\"",
        // 33
        "h\"时\"mm\"分\"ss\"秒\"",
        // 34
        "上午/下午h\"时\"mm\"分\"",
        // 35
        "上午/下午h\"时\"mm\"分\"ss\"秒\"",
        // 36
        "yyyy\"年\"m\"月\"",
        // 37
        "#,##0_);(#,##0)",
        // 38
        "#,##0_);[Red](#,##0)",
        // 39
        "#,##0.00_);(#,##0.00)",
        // 40
        "#,##0.00_);[Red](#,##0.00)",
        // 41
        "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)",
        // 42
        "_(\"$\"* #,##0_);_(\"$\"* (#,##0);_(\"$\"* \"-\"_);_(@_)",
        // 43
        "_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)",
        // 44
        "_(\"$\"* #,##0.00_);_(\"$\"* (#,##0.00);_(\"$\"* \"-\"??_);_(@_)",
        // 45
        "mm:ss",
        // 46
        "[h]:mm:ss",
        // 47
        "mm:ss.0",
        // 48
        "##0.0E+0",
        // 49
        "@",
        // 50
        "yyyy\"年\"m\"月\"",
        // 51
        "m\"月\"d\"日\"",
        // 52
        "yyyy\"年\"m\"月\"",
        // 53
        "m\"月\"d\"日\"",
        // 54
        "m\"月\"d\"日\"",
        // 55
        "上午/下午h\"时\"mm\"分\"",
        // 56
        "上午/下午h\"时\"mm\"分\"ss\"秒\"",
        // 57
        "yyyy\"年\"m\"月\"",
        // 58
        "m\"月\"d\"日\"",
        // 59
        "t0",
        // 60
        "t0.00",
        // 61
        "t#,##0",
        // 62
        "t#,##0.00",
        // 63-66 No specific correspondence found in the official documentation.
        // 63
        null,
        // 64
        null,
        // 65
        null,
        // 66
        null,
        // 67
        "t0%",
        // 68
        "t0.00%",
        // 69
        "t# ?/?",
        // 70
        "t# ??/??",
        // 71
        "ว/ด/ปปปป",
        // 72
        "ว-ดดด-ปป",
        // 73
        "ว-ดดด",
        // 74
        "ดดด-ปป",
        // 75
        "ช:นน",
        // 76
        "ช:นน:ทท",
        // 77
        "ว/ด/ปปปป ช:นน",
        // 78
        "นน:ทท",
        // 79
        "[ช]:นน:ทท",
        // 80
        "นน:ทท.0",
        // 81
        "d/m/bb",
        // end
    };

    public static final Map<String, Short> BUILTIN_FORMATS_MAP_CN = buildMap(BUILTIN_FORMATS_CN);
    public static final Map<String, Short> BUILTIN_FORMATS_MAP_US = buildMap(BUILTIN_FORMATS_US);
    public static final short MIN_CUSTOM_DATA_FORMAT_INDEX = 82;

    public static String getBuiltinFormat(Short index, String defaultFormat, Locale locale) {
        if (index == null || index <= 0) {
            return defaultFormat;
        }

        // Give priority to checking if it is the default value for all languages
        if (index < BUILTIN_FORMATS_ALL_LANGUAGES.length) {
            String format = BUILTIN_FORMATS_ALL_LANGUAGES[index];
            if (format != null) {
                return format;
            }
        }

        // In other cases, give priority to using the externally provided format
        if (!StringUtils.isEmpty(defaultFormat) && !defaultFormat.startsWith(RESERVED)) {
            return defaultFormat;
        }

        // Finally, try using the built-in format
        String[] builtinFormat = switchBuiltinFormats(locale);
        if (index >= builtinFormat.length) {
            return defaultFormat;
        }
        return builtinFormat[index];
    }

    public static String[] switchBuiltinFormats(Locale locale) {
        if (locale != null && Locale.US.getCountry().equals(locale.getCountry())) {
            return BUILTIN_FORMATS_US;
        }
        return BUILTIN_FORMATS_CN;
    }

    public static Map<String, Short> switchBuiltinFormatsMap(Locale locale) {
        if (locale != null && Locale.US.getCountry().equals(locale.getCountry())) {
            return BUILTIN_FORMATS_MAP_US;
        }
        return BUILTIN_FORMATS_MAP_CN;
    }

    private static Map<String, Short> buildMap(String[] builtinFormats) {
        Map<String, Short> map = MapUtils.newHashMapWithExpectedSize(builtinFormats.length);
        for (int i = 0; i < builtinFormats.length; i++) {
            map.put(builtinFormats[i], (short) i);
        }
        return map;
    }
}
