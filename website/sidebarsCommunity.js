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
const sidebarsCommunity = {
    community: [
        {
            id: "contact",
            type: "doc",
        },
        {
            id: "bug-report",
            type: "doc",
        },
        {
            id: "contribution",
            type: "doc",
        },
        {
            id: "development",
            type: "doc",
        }
    ]
};

export default sidebarsCommunity;
