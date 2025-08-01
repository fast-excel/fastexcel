package cn.idev.excel.write.metadata.fill;

import cn.idev.excel.enums.TemplateStringPartType;
import cn.idev.excel.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class TemplateStringPart {
    /**
     * Empty text part.
     */
    public static final TemplateStringPart EMPTY_TEXT_PART = text(StringUtils.EMPTY);

    /**
     * Represents the type of this part.
     *
     * @see TemplateStringPartType
     */
    private TemplateStringPartType type;

    /**
     * Represents a textual value and only has meaning if this partial type is text.
     */
    private String text;

    /**
     * Represents variable name and only has meaning if this partial type is variable.
     */
    private String variableName;

    /**
     * Represents collection name and only has meaning if this partial type is collection variable.
     */
    private String collectionName;

    /**
     * Represents the order of this part before parsing the original string, and the order of null values is later.
     */
    private Integer order;

    public static TemplateStringPart text(String text) {
        if (text == null) {
            throw new IllegalArgumentException("The text parameter cannot be empty when creating a text part");
        }
        return new TemplateStringPart(TemplateStringPartType.TEXT, text, null, null, null);
    }

    public static TemplateStringPart commonVariable(String variableName) {
        if (variableName == null) {
            throw new IllegalArgumentException(
                    "The variableName parameter cannot be null " + "when creating a variable part");
        }
        return new TemplateStringPart(TemplateStringPartType.COMMON_VARIABLE, null, variableName, null, null);
    }

    public static TemplateStringPart collectionVariable(String collectionName, String variableName) {
        if (variableName == null) {
            throw new IllegalArgumentException(
                    "The variableName parameter cannot be null " + "when creating a collection variable part");
        }
        return new TemplateStringPart(
                TemplateStringPartType.COLLECTION_VARIABLE, null, variableName, collectionName, null);
    }

    public static TemplateStringPart bareCollectionVariable(String variableName) {
        return collectionVariable(null, variableName);
    }

    public static TemplateStringPart emptyText() {
        return EMPTY_TEXT_PART;
    }

    public TemplateStringPart order(Integer order) {
        this.order = order;
        return this;
    }
}
