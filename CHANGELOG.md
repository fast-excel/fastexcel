# CHANGELOG

## 1.1.0

此次升级主要修复 [EasyExcel](https://github.com/alibaba/easyexcel) 历史 BUG，同时剔除了部分依赖库，符合 MIT 协议的相关规范。

具体更新内容如下：
- 【改进】移除 `itext` 依赖库，将 `转换PDF` 功能迁移至新项目；
- 【修复】fill填充空数据，可能导致行数据错乱的问题；
- 【修复】自定义数据格式可能导致数据读取失败的问题；
- 【优化】例行升级依赖的Jar包版本;
- 【优化】增加报错内容详细信息；
- 【优化】更新代码格式和部分错别字；
- 【优化】更新部分文档和使用说明。

## 1.2.0

此次升级主要内容： 
1. 修复[EasyExcel](https://github.com/alibaba/easyexcel) 历史 BUG；
2. 将项目的开源协议修改为Apache-2.0 license；
3. 修复测试模块中一些无法本地运行的测试用例；
4. 更新并增加相关API文档和说明。

具体更新内容如下：
- 【新增】新功能-支持自定义表头解析 [#257](https://github.com/fast-excel/fastexcel/pull/257) [@721806280](https://github.com/721806280)
- 【新增】增加测试用例 [#264](https://github.com/fast-excel/fastexcel/pull/264) [@happyfeetw](https://github.com/happyfeetw)
- 【改进】将开源协议修改为 Apache-2.0 license [#251](https://github.com/fast-excel/fastexcel/pull/251) [@psxjoy](https://github.com/psxjoy)
- 【修复】修复最后空列无法读取的BUG [#287](https://github.com/fast-excel/fastexcel/pull/287) [@delei](https://github.com/delei)
- 【修复】修复测试用例中错误的文件路径 [#286](https://github.com/fast-excel/fastexcel/pull/286) [@alaahong](https://github.com/alaahong)
- 【修复】修复错别字 [#268](https://github.com/fast-excel/fastexcel/pull/268) [@happyfeetw](https://github.com/happyfeetw)
- 【修复】修复填充时日期格式丢失问题 [#273](https://github.com/fast-excel/fastexcel/pull/273) [@waterisblue](https://github.com/waterisblue)
- 【优化】升级 Apache POI 相关依赖 [#211](https://github.com/fast-excel/fastexcel/pull/211) [@psxjoy](https://github.com/psxjoy)
- 【优化】优化项目Maven依赖 [#236](https://github.com/fast-excel/fastexcel/pull/236) [@wangmiscoding](https://github.com/wangmiscoding) 
- 【优化】优化CI 和构建流程 [#246](https://github.com/fast-excel/fastexcel/pull/246) [#247](https://github.com/fast-excel/fastexcel/pull/247) [@tisonkun](https://github.com/tisonkun)
- 【优化】优化测试模块的部分测试用例 [#249](https://github.com/fast-excel/fastexcel/pull/249) [#269](https://github.com/fast-excel/fastexcel/pull/269) [#291](https://github.com/fast-excel/fastexcel/pull/291)  [@tmlx1990](https://github.com/tmlx1990)
- 【优化】增加注释，新增单元测试用例 [#260](https://github.com/fast-excel/fastexcel/pull/260) [@nx-xn2002](https://github.com/nx-xn2002)
- 【优化】增加注释 [#270](https://github.com/fast-excel/fastexcel/pull/257) [#274](https://github.com/fast-excel/fastexcel/pull/270) [@HuangZhanQi](https://github.com/HuangZhanQi)
- 【优化】优化测试模块的部分测试用例  [#283](https://github.com/fast-excel/fastexcel/pull/283) [#285](https://github.com/fast-excel/fastexcel/pull/285) [#286](https://github.com/fast-excel/fastexcel/pull/286)  [#297](https://github.com/fast-excel/fastexcel/pull/297) [#298](https://github.com/fast-excel/fastexcel/pull/298) [@alaahong](https://github.com/alaahong)
- 【优化】优化项目的maven配置 [#284](https://github.com/fast-excel/fastexcel/pull/284) [@alaahong](https://github.com/alaahong)
- 【优化】优化 CONTRIBUTING.md 内容 [#300](https://github.com/fast-excel/fastexcel/pull/300) [@alaahong](https://github.com/alaahong)
