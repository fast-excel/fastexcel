package cn.idev.excel.test.core.validate;

import cn.idev.excel.FastExcel;
import cn.idev.excel.exception.ExcelDataConvertException;
import cn.idev.excel.read.builder.ExcelReaderBuilder;
import cn.idev.excel.read.listener.PageReadListener;
import cn.idev.excel.read.metadata.holder.ValidateErrorHolder;
import cn.idev.excel.read.processor.FileErrorHandler;
import cn.idev.excel.test.util.TestFileUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

/**
 * Test cases for reading validation
 *
 * @author wangmeng
 */
@Slf4j
public class ValidateTest {


    @Test
    public void test() {
        // 不存在转换失败情况
        String fileName = TestFileUtil.getPath() + "validate" + File.separator + "checkRead" + ".xlsx";
        testCheckRead(fileName);
        testCheckOutFile(fileName);
        existConvertException(fileName);
        // 存在转换失败
        String fileName2 = TestFileUtil.getPath() + "validate" + File.separator + "checkRead2" + ".xlsx";
        existConvertException(fileName2);
        existConvertException(fileName);
        // 手动添加业务校验
        testAddError(fileName);
        testAddError(fileName2);
    }


    public void testCheckRead(String fileName) {
        ExcelReaderBuilder readerBuilder = FastExcel.read(fileName).head(ValidateDemoData.class);
        List<ValidateDemoData> list = readerBuilder.registerReadListener(new PageReadListener<>(l -> {
            System.out.println("读取内容:" + JSON.toJSONString(l));
        })).validate().sheet().doReadSync();

        ValidateErrorHolder errorHolder = readerBuilder.getErrorHolder();
        System.out.println(JSON.toJSONString(errorHolder));
        String errorText = readerBuilder.handleError();
        System.out.println(errorText);
    }

    public void testCheckOutFile(String fileName) {
        ExcelReaderBuilder readerBuilder = FastExcel.read(fileName).head(ValidateDemoData.class);
        List<ValidateDemoData> list = readerBuilder.registerReadListener(new PageReadListener<>(l -> {
            System.out.println("读取内容:" + JSON.toJSONString(l));
        })).validate().setErrorHandler(FileErrorHandler.INSTANCE).sheet().doReadSync();

        ValidateErrorHolder errorHolder = readerBuilder.getErrorHolder();
        System.out.println(JSON.toJSONString(errorHolder));
        File errorFile = readerBuilder.handleError();
        System.out.println("错误信息输出到" + errorFile.getAbsolutePath());
    }


    public void existConvertException(String fileName) {
        ExcelReaderBuilder readerBuilder = FastExcel.read(fileName).head(ValidateDemoData.class);
        try {
            List<ValidateDemoData> list = readerBuilder.registerReadListener(new PageReadListener<>(l -> {
                System.out.println("读取内容:" + JSON.toJSONString(l));
            })).validate().setErrorHandler(FileErrorHandler.INSTANCE).sheet().doReadSync();
        } catch (ExcelDataConvertException e) {
            log.info("存在类型转换失败异常");
        }
        ValidateErrorHolder errorHolder = readerBuilder.getErrorHolder();
        System.out.println(JSON.toJSONString(errorHolder));
        File errorFile = readerBuilder.handleError();
        System.out.println("错误信息输出到" + errorFile.getAbsolutePath());
    }


    public void testAddError(String fileName) {
        ExcelReaderBuilder readerBuilder = FastExcel.read(fileName).head(ValidateDemoData.class);
        List<ValidateDemoData> list = null;
        try {
            list = readerBuilder.registerReadListener(new PageReadListener<>(l -> {
                System.out.println("读取内容:" + JSON.toJSONString(l));
            })).validate().setErrorHandler(FileErrorHandler.INSTANCE).sheet().doReadSync();
        } catch (ExcelDataConvertException e) {
            log.info("存在类型转换失败异常");
        }
        ValidateErrorHolder errorHolder = readerBuilder.getErrorHolder();
        System.out.println(JSON.toJSONString(errorHolder));

        // 手动业务校验
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if(true){
                errorHolder.addError(i+1,"订单号已存在");
            }
            if(true){
                errorHolder.addError(i+1,"用户不存在");
            }
        }


        File errorFile = readerBuilder.handleError();
        System.out.println("错误信息输出到" + errorFile.getAbsolutePath());
    }


}
