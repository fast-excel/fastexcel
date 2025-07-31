package cn.idev.excel.test.core.validation;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.util.ExcelValidationUtils;
import cn.idev.excel.write.handler.*;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Validation functionality test
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ValidationDataTest {

    private static File file07;
    private static File file03;
    private static File templateFile;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("validation07.xlsx");
        file03 = TestFileUtil.createNewFile("validation03.xls");
        templateFile = TestFileUtil.createNewFile("validationTemplate.xlsx");
    }

    @Test
    public void t01WriteValidationTemplate07() throws Exception {
        writeValidationTemplate(file07);
    }

    @Test
    public void t02WriteValidationTemplate03() throws Exception {
        writeValidationTemplate(file03);
    }

    @Test
    public void t03WriteValidationTemplateOnly() throws Exception {
        writeValidationTemplateOnly(templateFile);
    }

    @Test
    public void t11ReadAndValidate07() throws Exception {
        readAndValidate(file07);
    }

    @Test
    public void t12ReadAndValidate03() throws Exception {
        readAndValidate(file03);
    }

    @Test
    public void t21TestRequiredValidation() {
        testRequiredValidation();
    }

    @Test
    public void t22TestAnnotationPresence() {
        testAnnotationPresence();
    }

    /**
     * 写入带验证的Excel模板（包含数据）
     */
    private void writeValidationTemplate(File file) throws Exception {
        List<ValidationData> dataList = createValidTestData();

        EasyExcel.write(file, ValidationData.class)
            .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
            .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
            .registerWriteHandler(new AutoIncrementIdHandler())
            .sheet("项目信息")
            .doWrite(dataList);

        // 验证文件生成
        Assertions.assertTrue(file.exists(), "Excel文件应该生成成功");
        Assertions.assertTrue(file.length() > 0, "Excel文件大小应该大于0");

        // 验证Excel内容（仅对xlsx格式进行详细验证）
        if (file.getName().endsWith(".xlsx")) {
            verifyExcelContent(file);
        }
    }

    /**
     * 写入纯模板（不包含数据，仅包含验证规则）
     */
    private void writeValidationTemplateOnly(File file) throws Exception {
        List<ValidationData> emptyList = new ArrayList<>();

        EasyExcel.write(file, ValidationData.class)
            .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
            .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
            .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
            .registerWriteHandler(new AutoIncrementIdHandler())
            .sheet("项目信息模板")
            .doWrite(emptyList);

        // 验证模板文件生成
        Assertions.assertTrue(file.exists(), "Excel模板文件应该生成成功");
        Assertions.assertTrue(file.length() > 0, "Excel模板文件大小应该大于0");
    }

    /**
     * 读取并验证Excel数据
     */
    private void readAndValidate(File file) throws Exception {
        ValidationDataListener listener = new ValidationDataListener();
        
        EasyExcel.read(file, ValidationData.class, listener)
            .sheet()
            .doRead();

        List<ValidationData> dataList = listener.getList();
        Assertions.assertFalse(dataList.isEmpty(), "读取的数据不应为空");
        
        // 验证第一条数据的内容
        ValidationData firstData = dataList.get(0);
        Assertions.assertNotNull(firstData.getProjectName(), "项目名称不应为空");
        Assertions.assertNotNull(firstData.getProjectType(), "项目类型不应为空");
        Assertions.assertNotNull(firstData.getProjectAmount(), "项目金额不应为空");
        Assertions.assertNotNull(firstData.getStartDate(), "开始日期不应为空");
    }

    /**
     * 测试必填字段验证功能
     */
    private void testRequiredValidation() {
        List<ValidationData> invalidDataList = createInvalidTestData();
        
        List<ExcelValidationUtils.ValidationResult> results = 
            ExcelValidationUtils.validateRequiredBatch(invalidDataList);
        
        // 应该有验证失败的记录
        Assertions.assertFalse(results.isEmpty(), "应该有验证失败的记录");
        
        // 验证错误信息包含预期的字段
        boolean hasProjectNameError = false;
        boolean hasProjectTypeError = false;
        
        for (ExcelValidationUtils.ValidationResult result : results) {
            for (String error : result.getErrors()) {
                if (error.contains("项目名称")) {
                    hasProjectNameError = true;
                }
                if (error.contains("项目类型")) {
                    hasProjectTypeError = true;
                }
            }
        }
        
        Assertions.assertTrue(hasProjectNameError, "应该包含项目名称的验证错误");
        Assertions.assertTrue(hasProjectTypeError, "应该包含项目类型的验证错误");
    }

    /**
     * 测试注解是否正确存在
     */
    private void testAnnotationPresence() {
        try {
            // 测试@ExcelRequired注解
            java.lang.reflect.Field nameField = ValidationData.class.getDeclaredField("projectName");
            Assertions.assertTrue(nameField.isAnnotationPresent(cn.idev.excel.annotation.validation.ExcelRequired.class),
                "@ExcelRequired注解应该存在");

            // 测试@ExcelSelect注解
            java.lang.reflect.Field typeField = ValidationData.class.getDeclaredField("projectType");
            Assertions.assertTrue(typeField.isAnnotationPresent(cn.idev.excel.annotation.validation.ExcelSelect.class),
                "@ExcelSelect注解应该存在");

            // 测试@ExcelNumberValidation注解
            java.lang.reflect.Field amountField = ValidationData.class.getDeclaredField("projectAmount");
            Assertions.assertTrue(amountField.isAnnotationPresent(cn.idev.excel.annotation.validation.ExcelNumberValidation.class),
                "@ExcelNumberValidation注解应该存在");

            // 测试@ExcelDateValidation注解
            java.lang.reflect.Field dateField = ValidationData.class.getDeclaredField("startDate");
            Assertions.assertTrue(dateField.isAnnotationPresent(cn.idev.excel.annotation.validation.ExcelDateValidation.class),
                "@ExcelDateValidation注解应该存在");

            // 测试@AutoIncrementId注解
            java.lang.reflect.Field idField = ValidationData.class.getDeclaredField("id");
            Assertions.assertTrue(idField.isAnnotationPresent(cn.idev.excel.annotation.validation.AutoIncrementId.class),
                "@AutoIncrementId注解应该存在");

            // 测试@ExcelDefaultValue注解
            java.lang.reflect.Field deptField = ValidationData.class.getDeclaredField("department");
            Assertions.assertTrue(deptField.isAnnotationPresent(cn.idev.excel.annotation.validation.ExcelDefaultValue.class),
                "@ExcelDefaultValue注解应该存在");

        } catch (NoSuchFieldException e) {
            Assertions.fail("字段不存在: " + e.getMessage());
        }
    }

    /**
     * 验证Excel文件内容
     */
    private void verifyExcelContent(File file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        
        // 验证列宽设置
        Assertions.assertTrue(sheet.getColumnWidth(0) > 0, "第一列应该有设置列宽");
        
        // 验证表头
        Row headerRow = sheet.getRow(0);
        Assertions.assertNotNull(headerRow, "表头行不应为空");
        
        Cell firstCell = headerRow.getCell(0);
        Assertions.assertNotNull(firstCell, "第一个单元格不应为空");
        
        workbook.close();
    }

    /**
     * 创建有效的测试数据
     */
    private List<ValidationData> createValidTestData() {
        List<ValidationData> dataList = new ArrayList<>();
        
        for (int i = 1; i <= 3; i++) {
            ValidationData data = new ValidationData();
            data.setProjectName("测试项目" + i);
            data.setProjectType("基础设施");
            data.setProjectAmount(new BigDecimal("100.50"));
            data.setProgress(new BigDecimal("50.5"));
            data.setStartDate(new Date());
            data.setEndDate(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000));
            data.setRemark("这是测试项目" + i + "的备注信息");
            
            dataList.add(data);
        }
        
        return dataList;
    }

    /**
     * 创建包含无效数据的测试数据
     */
    private List<ValidationData> createInvalidTestData() {
        List<ValidationData> dataList = new ArrayList<>();
        
        // 无效数据1 - 缺少项目名称
        ValidationData data1 = new ValidationData();
        data1.setProjectName(null);
        data1.setProjectType("基础设施");
        data1.setProjectAmount(new BigDecimal("100.50"));
        data1.setStartDate(new Date());
        dataList.add(data1);
        
        // 无效数据2 - 缺少项目类型和金额
        ValidationData data2 = new ValidationData();
        data2.setProjectName("测试项目2");
        data2.setProjectType(null);
        data2.setProjectAmount(null);
        data2.setStartDate(new Date());
        dataList.add(data2);
        
        // 有效数据
        ValidationData data3 = new ValidationData();
        data3.setProjectName("有效项目");
        data3.setProjectType("房屋建筑");
        data3.setProjectAmount(new BigDecimal("200.00"));
        data3.setStartDate(new Date());
        dataList.add(data3);
        
        return dataList;
    }
}
