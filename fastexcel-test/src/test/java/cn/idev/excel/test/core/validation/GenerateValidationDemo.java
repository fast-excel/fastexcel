package cn.idev.excel.test.core.validation;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.write.handler.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Generate validation demo templates
 *
 * @author FastExcel Team
 * @since 1.2.1
 */
public class GenerateValidationDemo {

    @Test
    public void generateAllTemplates() {
        System.out.println("=== 生成FastExcel验证功能演示模板 ===\n");
        
        try {
            // 1. 生成空模板（仅包含验证规则）
            generateEmptyTemplate();
            
            // 2. 生成包含示例数据的模板
            generateTemplateWithSampleData();
            
            // 3. 生成包含无效数据的模板（用于测试验证）
            generateTemplateWithInvalidData();

            // 4. 生成带标题的模板（测试标题适应性）
            generateTemplateWithTitle();
            
            System.out.println("\n=== 所有模板生成完成 ===");
            System.out.println("请打开生成的Excel文件查看验证效果：");
            System.out.println("1. 下拉选择框验证 - 项目类型列");
            System.out.println("2. 数值范围验证 - 项目金额和进度列");
            System.out.println("3. 日期范围验证 - 开始日期和结束日期列");
            System.out.println("4. 默认值设置 - 负责部门列");
            System.out.println("5. 单元格锁定 - 创建人列");
            System.out.println("6. 自动递增序号 - 序号列");
            
        } catch (Exception e) {
            System.out.println("❌ 生成模板时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 生成空模板（仅包含验证规则）
     */
    private void generateEmptyTemplate() {
        File file = TestFileUtil.createNewFile("项目信息录入模板_空模板.xlsx");
        System.out.println("1. 生成空模板: " + file.getName());
        
        List<ValidationData> emptyList = new ArrayList<>();
        
        EasyExcel.write(file, ValidationData.class)
            .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
            .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
            .registerWriteHandler(new AutoIncrementIdHandler())
            .sheet("项目信息录入模板")
            .doWrite(emptyList);
        
        if (file.exists()) {
            System.out.println("   ✅ 生成成功，文件大小: " + file.length() + " bytes");
            System.out.println("   📋 此模板包含所有验证规则，可直接用于数据录入");
            System.out.println("   📁 文件位置: " + file.getAbsolutePath());
        }
    }
    
    /**
     * 生成包含示例数据的模板
     */
    private void generateTemplateWithSampleData() {
        File file = TestFileUtil.createNewFile("项目信息录入模板_示例数据.xlsx");
        System.out.println("\n2. 生成示例数据模板: " + file.getName());
        
        List<ValidationData> sampleData = createSampleData();
        
        EasyExcel.write(file, ValidationData.class)
            .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
            .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
            .registerWriteHandler(new AutoIncrementIdHandler())
            .sheet("项目信息示例")
            .doWrite(sampleData);
        
        if (file.exists()) {
            System.out.println("   ✅ 生成成功，文件大小: " + file.length() + " bytes");
            System.out.println("   📋 此模板包含" + sampleData.size() + "条示例数据，展示正确的数据格式");
            System.out.println("   📁 文件位置: " + file.getAbsolutePath());
        }
    }
    
    /**
     * 生成包含无效数据的模板（用于测试验证）
     */
    private void generateTemplateWithInvalidData() {
        File file = TestFileUtil.createNewFile("项目信息录入模板_验证测试.xlsx");
        System.out.println("\n3. 生成验证测试模板: " + file.getName());
        
        List<ValidationData> invalidData = createInvalidData();
        
        EasyExcel.write(file, ValidationData.class)
            .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
            .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
            .registerWriteHandler(new AutoIncrementIdHandler())
            .sheet("验证测试数据")
            .doWrite(invalidData);
        
        if (file.exists()) {
            System.out.println("   ✅ 生成成功，文件大小: " + file.length() + " bytes");
            System.out.println("   📋 此模板包含" + invalidData.size() + "条测试数据，用于验证功能测试");
            System.out.println("   📁 文件位置: " + file.getAbsolutePath());
        }
    }
    
    /**
     * 创建示例数据
     */
    private List<ValidationData> createSampleData() {
        List<ValidationData> dataList = new ArrayList<>();
        
        for (int i = 1; i <= 5; i++) {
            ValidationData data = new ValidationData();
            data.setProjectName("示例项目" + i);
            data.setProjectType(i % 2 == 0 ? "基础设施" : "房屋建筑");
            data.setProjectAmount(new BigDecimal(100.50 + i * 50));
            data.setProgress(new BigDecimal(20.0 + i * 15));
            data.setStartDate(new Date());
            data.setEndDate(new Date(System.currentTimeMillis() + (30L + i * 10) * 24 * 60 * 60 * 1000));
            data.setRemark("这是示例项目" + i + "的详细说明和备注信息");
            
            dataList.add(data);
        }
        
        return dataList;
    }
    
    /**
     * 创建无效数据（用于测试验证功能）
     */
    private List<ValidationData> createInvalidData() {
        List<ValidationData> dataList = new ArrayList<>();
        
        // 有效数据作为对比
        ValidationData validData = new ValidationData();
        validData.setProjectName("正确的项目");
        validData.setProjectType("基础设施");
        validData.setProjectAmount(new BigDecimal("500.00"));
        validData.setProgress(new BigDecimal("75.0"));
        validData.setStartDate(new Date());
        validData.setRemark("这是一条完全正确的数据");
        dataList.add(validData);
        
        // 无效数据1 - 缺少必填字段
        ValidationData invalidData1 = new ValidationData();
        invalidData1.setProjectName(null); // 缺少项目名称
        invalidData1.setProjectType("基础设施");
        invalidData1.setProjectAmount(new BigDecimal("300.00"));
        invalidData1.setRemark("缺少项目名称的无效数据");
        dataList.add(invalidData1);
        
        // 无效数据2 - 多个字段缺失
        ValidationData invalidData2 = new ValidationData();
        invalidData2.setProjectName("项目2");
        invalidData2.setProjectType(null); // 缺少项目类型
        invalidData2.setProjectAmount(null); // 缺少项目金额
        invalidData2.setStartDate(null); // 缺少开始日期
        invalidData2.setRemark("多个必填字段缺失的无效数据");
        dataList.add(invalidData2);
        
        return dataList;
    }
    
    @Test
    public void generateSimpleTemplate() {
        System.out.println("=== 生成简单验证模板 ===");
        
        File file = TestFileUtil.createNewFile("简单验证模板.xlsx");
        
        // 创建一条示例数据
        List<ValidationData> dataList = new ArrayList<>();
        ValidationData data = new ValidationData();
        data.setProjectName("示例项目");
        data.setProjectType("基础设施");
        data.setProjectAmount(new BigDecimal("100.00"));
        data.setProgress(new BigDecimal("50.0"));
        data.setStartDate(new Date());
        data.setRemark("这是一个简单的示例");
        dataList.add(data);
        
        EasyExcel.write(file, ValidationData.class)
            .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
            .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
            .registerWriteHandler(new AutoIncrementIdHandler())
            .sheet("简单示例")
            .doWrite(dataList);
        
        System.out.println("✅ 简单模板生成成功: " + file.getAbsolutePath());
        System.out.println("📋 包含1条示例数据和所有验证功能");
    }

    /**
     * 生成带标题的模板（测试标题适应性）
     */
    private void generateTemplateWithTitle() {
        File file = TestFileUtil.createNewFile("项目信息录入模板_带标题.xlsx");
        System.out.println("4. 生成带标题模板: " + file.getName());

        // 创建示例数据
        List<ValidationData> dataList = createSampleData();

        EasyExcel.write(file, ValidationData.class)
                .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
                .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
                .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
                .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
                .registerWriteHandler(new AutoIncrementIdHandler())
                .sheet("项目信息")
                // 关键：设置标题行数为2（第一行是大标题，第二行是列标题）
                .relativeHeadRowIndex(1)
                .doWrite(dataList);

        System.out.println("✅ 带标题模板生成成功: " + file.getName());
        System.out.println("   包含标题行、示例数据和所有验证功能");
    }

    /**
     * 测试单独的带标题模板生成
     */
    @Test
    public void generateTitleTemplate() {
        System.out.println("=== 生成带标题验证模板测试 ===");

        try {
            generateTemplateWithTitle();
            System.out.println("✅ 带标题模板测试完成！");
        } catch (Exception e) {
            System.err.println("❌ 生成带标题模板失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
