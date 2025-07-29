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

package cn.idev.excel.test.core.celldata;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson2.JSON;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class CellDataDataListener extends AnalysisEventListener<CellDataReadData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CellDataDataListener.class);
    List<CellDataReadData> list = new ArrayList<>();

    @Override
    public void invoke(CellDataReadData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        CellDataReadData cellDataData = list.get(0);

        Assertions.assertEquals("2020年01月01日", cellDataData.getDate().getData());
        Assertions.assertEquals((long) cellDataData.getInteger1().getData(), 2L);
        Assertions.assertEquals((long) cellDataData.getInteger2(), 2L);
        if (context.readWorkbookHolder().getExcelType() != ExcelTypeEnum.CSV) {
            Assertions.assertEquals(
                    cellDataData.getFormulaValue().getFormulaData().getFormulaValue(), "B2+C2");
        } else {
            Assertions.assertNull(cellDataData.getFormulaValue().getData());
        }
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
