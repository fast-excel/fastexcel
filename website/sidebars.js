// @ts-check

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

/**
 * Creating a sidebar enables you to:
 - create an ordered group of docs
 - render a sidebar for each doc of that group
 - provide next/previous navigation

 The sidebars can be generated from the filesystem, or explicitly defined here.

 Create as many sidebars as you want.

 @type {import('@docusaurus/plugin-content-docs').SidebarsConfig}
 */
const sidebars = {
  // By default, Docusaurus generates a sidebar from the docs folder structure
  tutorialSidebar: [
    "zh/intro",
    {
      type: 'category',
      label: '入门',
      items: [
        'zh/quickstart/guide',
        'zh/quickstart/example',
      ],
    },
    {
      type: 'category',
      label: '读取Excel',
      items: [
        'zh/read/simple',
        'zh/read/sheet',
        'zh/read/num-rows',
        'zh/read/csv',
        'zh/read/head',
        'zh/read/extra',
        'zh/read/exception',
        'zh/read/pojo',
        'zh/read/converter',
        'zh/read/spring',
      ],
    },
    {
      type: 'category',
      label: '写入Excel',
      items: [
        'zh/write/simple',
        'zh/write/sheet',
        'zh/write/image',
        'zh/write/csv',
        'zh/write/head',
        'zh/write/extra',
        'zh/write/format',
        'zh/write/pojo',
        'zh/write/style',
        'zh/write/spring',
      ],
    },
    {
      type: 'category',
      label: '填充Excel',
      items: [
        'zh/fill/fill',
      ],
    },
    {
      type: 'category',
      label: '进阶内容',
      items: [
        'zh/help/annotation',
        'zh/help/core-class',
        'zh/help/large-data',
        'zh/help/parameter',
      ],
    },
    "zh/faq",
    "zh/bug-report",
    "zh/contribution",
    "zh/development",
    "zh/contact",
  ],

  // But you can create a sidebar manually
  /*
  tutorialSidebar: [
    'intro',
    'hello',
    {
      type: 'category',
      label: 'Tutorial',
      items: ['tutorial-basics/create-a-document'],
    },
  ],
   */

};

export default sidebars;
