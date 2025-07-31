package cn.idev.excel.test.core.validation;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.write.handler.AutoIncrementIdHandler;
import cn.idev.excel.write.handler.ExcelDateValidationHandler;
import cn.idev.excel.write.handler.ExcelDefaultValueHandler;
import cn.idev.excel.write.handler.ExcelNumberValidationHandler;
import cn.idev.excel.write.handler.ExcelSelectHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 验证效果测试 - 创建包含各种问题数据的文件来测试验证功能
 */
public class ValidationEffectTest {

    @Test
    public void generateValidationEffectTest() {
        System.out.println("=== 生成验证效果测试文件 ===");
        
        try {
            // 1. 生成正常数据文件（作为对比）
            generateNormalDataFile();
            
            // 2. 生成问题数据文件（测试验证效果）
            generateProblemDataFile();
            
            System.out.println("\n=== 验证效果测试文件生成完成 ===");
            System.out.println("请打开生成的Excel文件测试验证效果：");
            System.out.println("1. 正常数据文件 - 所有数据都符合验证规则");
            System.out.println("2. 问题数据文件 - 包含各种违反验证规则的数据");
            System.out.println("\n测试方法：");
            System.out.println("- 尝试在下拉框中输入不在选项中的值");
            System.out.println("- 尝试在数值列输入超出范围的值");
            System.out.println("- 尝试在日期列输入无效日期");
            
        } catch (Exception e) {
            System.err.println("❌ 生成验证效果测试文件失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 生成正常数据文件（所有数据都符合验证规则）
     */
    private void generateNormalDataFile() {
        File file = TestFileUtil.createNewFile("验证效果测试_正常数据.xlsx");
        System.out.println("1. 生成正常数据文件: " + file.getName());
        
        List<ValidationData> normalDataList = createNormalData();
        
        EasyExcel.write(file, ValidationData.class)
                .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
                .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
                .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
                .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
                .registerWriteHandler(new AutoIncrementIdHandler())
                .sheet("正常数据")
                .doWrite(normalDataList);
        
        System.out.println("✅ 正常数据文件生成成功: " + file.getName());
        System.out.println("   包含5条符合所有验证规则的数据");
    }
    
    /**
     * 生成问题数据文件（包含各种违反验证规则的数据）
     */
    private void generateProblemDataFile() {
        File file = TestFileUtil.createNewFile("验证效果测试_问题数据.xlsx");
        System.out.println("2. 生成问题数据文件: " + file.getName());
        
        List<ValidationData> problemDataList = createProblemData();
        
        EasyExcel.write(file, ValidationData.class)
                .registerWriteHandler(new ExcelSelectHandler(ValidationData.class))
                .registerWriteHandler(new ExcelNumberValidationHandler(ValidationData.class))
                .registerWriteHandler(new ExcelDateValidationHandler(ValidationData.class))
                .registerWriteHandler(new ExcelDefaultValueHandler(ValidationData.class))
                .registerWriteHandler(new AutoIncrementIdHandler())
                .sheet("问题数据")
                .doWrite(problemDataList);
        
        System.out.println("✅ 问题数据文件生成成功: " + file.getName());
        System.out.println("   包含5条违反验证规则的数据，用于测试验证效果");
    }
    
    /**
     * 创建正常数据（符合所有验证规则）
     */
    private List<ValidationData> createNormalData() {
        List<ValidationData> dataList = new ArrayList<>();
        
        // 正常数据1
        ValidationData data1 = new ValidationData();
        data1.setProjectName("正常项目1");
        data1.setProjectType("基础设施");  // 符合下拉选项
        data1.setProjectAmount(new BigDecimal("150.50"));  // 符合范围 0-999999.99
        data1.setProgress(new BigDecimal("35.5"));  // 符合范围 0-100
        data1.setStartDate(createDate(2025, 1, 15));  // 符合日期范围
        data1.setEndDate(createDate(2025, 6, 15));  // 符合日期范围
        data1.setDepartment("工程部");
        data1.setRemark("这是正常的项目数据");
        dataList.add(data1);
        
        // 正常数据2
        ValidationData data2 = new ValidationData();
        data2.setProjectName("正常项目2");
        data2.setProjectType("房屋建筑");  // 符合下拉选项
        data2.setProjectAmount(new BigDecimal("200.00"));  // 符合范围
        data2.setProgress(new BigDecimal("50.0"));  // 符合范围
        data2.setStartDate(createDate(2025, 2, 1));  // 符合日期范围
        data2.setEndDate(createDate(2025, 8, 1));  // 符合日期范围
        data2.setDepartment("建设部");
        data2.setRemark("这也是正常的项目数据");
        dataList.add(data2);
        
        // 添加更多正常数据...
        for (int i = 3; i <= 5; i++) {
            ValidationData data = new ValidationData();
            data.setProjectName("正常项目" + i);
            data.setProjectType("市政工程");  // 符合下拉选项
            data.setProjectAmount(new BigDecimal(100 + i * 50 + ".00"));  // 符合范围
            data.setProgress(new BigDecimal(20 + i * 10 + ".0"));  // 符合范围
            data.setStartDate(createDate(2025, i, 1));  // 符合日期范围
            data.setEndDate(createDate(2025, i + 3, 1));  // 符合日期范围
            data.setDepartment("工程部");
            data.setRemark("正常项目" + i + "的详细说明");
            dataList.add(data);
        }
        
        return dataList;
    }
    
    /**
     * 创建问题数据（违反各种验证规则）
     */
    private List<ValidationData> createProblemData() {
        List<ValidationData> dataList = new ArrayList<>();
        
        // 问题数据1：项目类型不在下拉选项中
        ValidationData data1 = new ValidationData();
        data1.setProjectName("问题项目1-下拉框测试");
        data1.setProjectType("无效类型");  // ❌ 不在下拉选项中
        data1.setProjectAmount(new BigDecimal("150.50"));
        data1.setProgress(new BigDecimal("35.5"));
        data1.setStartDate(createDate(2025, 1, 15));
        data1.setEndDate(createDate(2025, 6, 15));
        data1.setDepartment("工程部");
        data1.setRemark("这个项目的类型不在下拉选项中，应该会被验证拦截");
        dataList.add(data1);
        
        // 问题数据2：项目金额超出范围
        ValidationData data2 = new ValidationData();
        data2.setProjectName("问题项目2-金额超限");
        data2.setProjectType("基础设施");
        data2.setProjectAmount(new BigDecimal("1000000.00"));  // ❌ 超出范围 0-999999.99
        data2.setProgress(new BigDecimal("50.0"));
        data2.setStartDate(createDate(2025, 2, 1));
        data2.setEndDate(createDate(2025, 8, 1));
        data2.setDepartment("财务部");
        data2.setRemark("这个项目的金额超出了允许范围，应该会被验证拦截");
        dataList.add(data2);
        
        // 问题数据3：进度超出范围
        ValidationData data3 = new ValidationData();
        data3.setProjectName("问题项目3-进度超限");
        data3.setProjectType("房屋建筑");
        data3.setProjectAmount(new BigDecimal("300.00"));
        data3.setProgress(new BigDecimal("150.0"));  // ❌ 超出范围 0-100
        data3.setStartDate(createDate(2025, 3, 1));
        data3.setEndDate(createDate(2025, 9, 1));
        data3.setDepartment("项目部");
        data3.setRemark("这个项目的进度超出了100%，应该会被验证拦截");
        dataList.add(data3);
        
        // 问题数据4：日期超出范围
        ValidationData data4 = new ValidationData();
        data4.setProjectName("问题项目4-日期超限");
        data4.setProjectType("水利工程");
        data4.setProjectAmount(new BigDecimal("400.00"));
        data4.setProgress(new BigDecimal("60.0"));
        data4.setStartDate(createDate(2019, 1, 1));  // ❌ 早于2020-01-01
        data4.setEndDate(createDate(2035, 1, 1));  // ❌ 晚于2030-12-31
        data4.setDepartment("水利部");
        data4.setRemark("这个项目的日期超出了允许范围，应该会被验证拦截");
        dataList.add(data4);
        
        // 问题数据5：多个字段都有问题
        ValidationData data5 = new ValidationData();
        data5.setProjectName("问题项目5-多重问题");
        data5.setProjectType("不存在的类型");  // ❌ 不在下拉选项中
        data5.setProjectAmount(new BigDecimal("-100.00"));  // ❌ 负数，小于0
        data5.setProgress(new BigDecimal("200.0"));  // ❌ 超出范围
        data5.setStartDate(createDate(2015, 1, 1));  // ❌ 早于范围
        data5.setEndDate(createDate(2040, 1, 1));  // ❌ 晚于范围
        data5.setDepartment("不存在部门");
        data5.setRemark("这个项目有多个字段都违反了验证规则，是最严重的测试案例");
        dataList.add(data5);
        
        return dataList;
    }

    /**
     * 创建日期的辅助方法
     */
    private Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);  // month是0-based
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
