package cn.idev.excel.test.temp.read;

import cn.idev.excel.FastExcel;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.enums.CellExtraTypeEnum;
import cn.idev.excel.metadata.CellExtra;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Zhe Zhang
 **/

public class CommentTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentTest.class);
    
    private final List<String> commentList = Arrays.asList("测试", "comment");
    
    @Test
    public void xlsxCommentTest() throws Exception {
        File file = new File("src/test/resources/comment/comment.xlsx");
        FastExcel.read(file, new ReadListener() {
            @Override
            public void invoke(Object data, AnalysisContext context) {
            }
            
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
            
            @Override
            public void extra(CellExtra extra, AnalysisContext context) {
                LOGGER.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
                if (extra.getType().equals(CellExtraTypeEnum.COMMENT)) {
                    commentList.contains(extra.getText());
                }
            }
        }).excelType(ExcelTypeEnum.XLSX).extraRead(CellExtraTypeEnum.COMMENT).sheet().doRead();
    }
    
    @Test
    public void xlsCommentTest() throws Exception {
        File file = new File("src/test/resources/comment/comment.xls");
        FastExcel.read(file, new ReadListener() {
            @Override
            public void invoke(Object data, AnalysisContext context) {
            }
            
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
            
            @Override
            public void extra(CellExtra extra, AnalysisContext context) {
                LOGGER.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
                if (extra.getType().equals(CellExtraTypeEnum.COMMENT)) {
                    commentList.contains(extra.getText());
                }
            }
        }).excelType(ExcelTypeEnum.XLS).extraRead(CellExtraTypeEnum.COMMENT).sheet().doRead();
    }
    
}
