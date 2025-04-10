package cn.idev.excel.test.temp.issue2319;

import java.io.File;

import com.alibaba.fastjson2.JSON;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.FastExcel;
import cn.idev.excel.read.listener.PageReadListener;
import cn.idev.excel.test.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Issue2319Test {
    @Test
    public void IssueTest1() {
        String fileName = TestFileUtil.getPath() + "temp/issue2319" + File.separator + "test1.xlsx";
        FastExcel.read(fileName, Issue2319.class, new PageReadListener<Issue2319>(dataList -> {
            for (Issue2319 issueData : dataList) {
                System.out.println(("读取到一条数据{}" + JSON.toJSONString(issueData)));
            }
        })).sheet().doRead();
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2319
    @Test
    public void IssueTest2() {
        String fileName = TestFileUtil.getPath() + "temp/issue2319" + File.separator + "test2.xlsx";
        FastExcel.read(fileName, Issue2319.class, new PageReadListener<Issue2319>(dataList -> {
            for (Issue2319 issueData : dataList) {
                System.out.println(("读取到一条数据{}" + JSON.toJSONString(issueData)));
            }
        })).sheet().doRead();
    }
}
