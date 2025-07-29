/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.idev.excel.test.temp.poi;

import java.io.IOException;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试poi
 *
 *
 **/
public class PoiDateFormatTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoiDateFormatTest.class);

    @Test
    public void read() throws IOException {
        String file = "src/test/resources/dataformat/dataformat.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(7);
        XSSFCell cell = row.getCell(0);
        LOGGER.info("dd{}", cell.getDateCellValue());
        LOGGER.info("dd{}", cell.getNumericCellValue());

        LOGGER.info("dd{}", DateUtil.isCellDateFormatted(cell));
    }
}
