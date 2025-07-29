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

package cn.idev.excel.test.temp;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.support.cglib.beans.BeanMap;
import cn.idev.excel.support.cglib.core.DebuggingClassWriter;
import cn.idev.excel.util.BeanMapUtils;
import com.alibaba.fastjson2.JSON;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 临时测试
 *
 *
 **/
public class Xls03Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Xls03Test.class);

    @TempDir
    Path tempDir;

    @Test
    public void test() {
        List<Object> list = EasyExcel.read("src/test/resources/compatibility/t07.xlsx")
                .sheet()
                .doReadSync();
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void test2() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, tempDir.toString());

        CamlData camlData = new CamlData();
        // camlData.setTest("test2");
        // camlData.setAEst("test3");
        // camlData.setTEST("test4");

        BeanMap beanMap = BeanMapUtils.create(camlData);

        LOGGER.info("test:{}", beanMap.get("test"));
        LOGGER.info("test:{}", beanMap.get("Test"));
        LOGGER.info("test:{}", beanMap.get("TEst"));
        LOGGER.info("test:{}", beanMap.get("TEST"));
    }
}
