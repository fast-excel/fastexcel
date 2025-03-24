package cn.idev.excel.test.demo.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class UploadData {
    
    private String string;
    
    private Date date;
    
    private Double doubleData;
}
