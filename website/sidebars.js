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
    docs: [
        {
            id: "introduce",
            type: "doc",
        },
        {
            type: 'category',
            label: 'quickstart',
            items: [
                'quickstart/guide',
                'quickstart/simple-example'
            ]
        },
        {
            type: 'category',
            label: 'read',
            items: [
                'read/simple',
                'read/sheet',
                'read/num-rows',
                'read/csv',
                'read/head',
                'read/extra',
                'read/exception',
                'read/pojo',
                'read/converter',
                'read/spring'
            ]
        },
        {
            type: 'category',
            label: 'write',
            items: [
                'write/simple',
                'write/sheet',
                'write/image',
                'write/csv',
                'write/head',
                'write/extra',
                'write/format',
                'write/pojo',
                'write/style',
                'write/spring'
            ]
        },
        {
            type: 'category',
            label: 'fill',
            items: [
                'fill/fill'
            ]
        },
        {
            type: 'category',
            label: 'help',
            items: [
                'help/annotation',
                'help/core-class',
                'help/parameter',
                'help/large-data',
                "help/faq"
            ]
        }
    ]
};

export default sidebars;
