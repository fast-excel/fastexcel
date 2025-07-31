package cn.idev.excel.test.core.validation;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.util.ExcelValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validation data listener for testing
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@Slf4j
public class ValidationDataListener extends AnalysisEventListener<ValidationData> {
    
    private List<ValidationData> list = new ArrayList<>();
    
    @Override
    public void invoke(ValidationData data, AnalysisContext context) {
        log.debug("解析到一条数据:{}", data);
        list.add(data);
    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！共{}条数据", list.size());
        
        // 验证数据
        validateData();
        
        // 验证必填字段
        validateRequiredFields();
    }
    
    /**
     * 验证数据内容
     */
    private void validateData() {
        Assertions.assertFalse(list.isEmpty(), "数据列表不应为空");
        
        for (ValidationData data : list) {
            // 验证自动递增ID
            if (data.getId() != null) {
                Assertions.assertTrue(data.getId() > 0, "自动递增ID应大于0");
            }
            
            // 验证项目金额范围
            if (data.getProjectAmount() != null) {
                Assertions.assertTrue(data.getProjectAmount().compareTo(BigDecimal.ZERO) >= 0, 
                    "项目金额应大于等于0");
                Assertions.assertTrue(data.getProjectAmount().compareTo(new BigDecimal("999999.99")) <= 0, 
                    "项目金额应小于等于999999.99");
            }
            
            // 验证进度范围
            if (data.getProgress() != null) {
                Assertions.assertTrue(data.getProgress().compareTo(BigDecimal.ZERO) >= 0, 
                    "项目进度应大于等于0");
                Assertions.assertTrue(data.getProgress().compareTo(new BigDecimal("100.0")) <= 0, 
                    "项目进度应小于等于100");
            }
            
            // 验证项目类型是否在允许的选项中
            if (data.getProjectType() != null) {
                String[] allowedTypes = {"基础设施", "房屋建筑", "市政工程", "水利工程", "其他"};
                boolean isValidType = false;
                for (String type : allowedTypes) {
                    if (type.equals(data.getProjectType())) {
                        isValidType = true;
                        break;
                    }
                }
                Assertions.assertTrue(isValidType, "项目类型应在允许的选项中: " + data.getProjectType());
            }
        }
        
        log.info("数据内容验证通过");
    }
    
    /**
     * 验证必填字段
     */
    private void validateRequiredFields() {
        List<ExcelValidationUtils.ValidationResult> results = 
            ExcelValidationUtils.validateRequiredBatch(list);
        
        log.info("必填字段验证结果: 发现{}个错误", results.size());
        
        for (ExcelValidationUtils.ValidationResult result : results) {
            log.warn("验证失败: {}", result.toString());
        }
        
        // 注意：这里不强制要求验证通过，因为测试数据可能包含无效数据用于测试验证功能
    }
    
    public List<ValidationData> getList() {
        return list;
    }
}
