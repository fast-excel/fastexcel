package cn.idev.excel.write.handler;

import cn.idev.excel.write.metadata.fill.TemplateStringPart;
import java.util.Collection;

/**
 * Template string parse handler
 */
public interface TemplateStringParseHandler {
    /**
     * Parse the template string
     *
     * @param stringValue String value
     * @return The multi parts formed after parsing a template string
     */
    Collection<TemplateStringPart> parse(String stringValue);
}
