package cn.idev.excel.write.handler.impl;

import cn.idev.excel.util.StringUtils;
import cn.idev.excel.write.handler.TemplateStringParseHandler;
import cn.idev.excel.write.metadata.fill.TemplateStringPart;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Default template string parse handler
 */
public class DefaultTemplateStringParseHandler implements TemplateStringParseHandler {
    private static final String ESCAPE_FILL_PREFIX = "\\\\\\{";
    private static final String ESCAPE_FILL_SUFFIX = "\\\\\\}";
    private static final String FILL_PREFIX = "{";
    private static final String FILL_SUFFIX = "}";
    private static final char IGNORE_CHAR = '\\';
    private static final String COLLECTION_PREFIX = ".";

    private static final List<TemplateStringPart> EMPTY_PART_LIST = Collections.emptyList();
    private static volatile DefaultTemplateStringParseHandler instance;

    public static synchronized DefaultTemplateStringParseHandler getInstance() {
        if (instance == null) {
            synchronized (DefaultTemplateStringParseHandler.class) {
                if (instance == null) {
                    instance = new DefaultTemplateStringParseHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public Collection<TemplateStringPart> parse(String value) {
        if (StringUtils.isEmpty(value)) {
            return EMPTY_PART_LIST;
        }
        List<TemplateStringPart> partList = new ArrayList<>();
        int length = value.length();
        int startIndex = 0, lastPartIndex = 0;
        out:
        while (startIndex < length) {
            int prefixIndex = value.indexOf(FILL_PREFIX, startIndex);
            if (prefixIndex < 0) {
                break;
            }
            startIndex = prefixIndex + 1;
            if (prefixIndex != 0) {
                char prefixPrefixChar = value.charAt(prefixIndex - 1);
                if (prefixPrefixChar == IGNORE_CHAR) {
                    continue;
                }
            }
            int suffixIndex = -1;
            while (suffixIndex == -1) {
                if (startIndex >= length) {
                    break out;
                }
                suffixIndex = value.indexOf(FILL_SUFFIX, startIndex);
                if (suffixIndex < 0) {
                    break out;
                }
                startIndex = suffixIndex + 1;
                char prefixSuffixChar = value.charAt(suffixIndex - 1);
                if (prefixSuffixChar == IGNORE_CHAR) {
                    suffixIndex = -1;
                }
            }
            String variable = value.substring(prefixIndex + 1, suffixIndex);
            if (StringUtils.isEmpty(variable)) {
                continue;
            }
            // Add the text part in the gap between the current variable and the previous variable
            TemplateStringPart variableGapPart = lastPartIndex == prefixIndex
                    ? TemplateStringPart.emptyText()
                    : TemplateStringPart.text(processEscape(value.substring(lastPartIndex, prefixIndex)));
            lastPartIndex = suffixIndex + 1;
            partList.add(variableGapPart);
            // Add the current variable part
            int collectPrefixIndex = variable.indexOf(COLLECTION_PREFIX);
            if (collectPrefixIndex < 0) {
                partList.add(TemplateStringPart.commonVariable(variable));
                continue;
            }
            String truncatedVariable = variable.substring(collectPrefixIndex + 1);
            // In order to adapt to the original filling effect
            // The symbol If there is no actual variable after, it will be regarded as a common variable
            if (StringUtils.isEmpty(truncatedVariable)) {
                partList.add(TemplateStringPart.commonVariable(variable));
                continue;
            }
            String collectionName = collectPrefixIndex == 0 ? null : variable.substring(0, collectPrefixIndex);
            partList.add(TemplateStringPart.collectionVariable(collectionName, truncatedVariable));
        }
        // Add the trailing text part
        TemplateStringPart trailingPart = lastPartIndex == length
                ? TemplateStringPart.emptyText()
                : TemplateStringPart.text(processEscape(value.substring(lastPartIndex)));
        partList.add(trailingPart);
        return partList;
    }

    private String processEscape(String stringValue) {
        stringValue = stringValue.replaceAll(ESCAPE_FILL_PREFIX, FILL_PREFIX);
        stringValue = stringValue.replaceAll(ESCAPE_FILL_SUFFIX, FILL_SUFFIX);
        return stringValue;
    }
}
