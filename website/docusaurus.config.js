// @ts-check
// `@type` JSDoc annotations allow editor autocompletion and type checking
// (when paired with `@ts-check`).
// There are various equivalent ways to declare your Docusaurus config.
// See: https://docusaurus.io/docs/api/docusaurus-config
import {themes as prismThemes} from 'prism-react-renderer';

const branch = 'main';
const repoUrl = `https://github.com/fast-excel/fastexcel`;

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)
/** @type {import('@docusaurus/types').Config} */
const config = {
    title: 'FastExcel',
    favicon: 'img/favicon.ico',

    // Future flags, see https://docusaurus.io/docs/api/docusaurus-config#future
    future: {
        v4: true,
        // Improve compatibility with the upcoming Docusaurus v4
    },

    customFields: {
        repoUrl,
    },

    // Set the production url of your site here
    url: 'https://fast-excel.github.io',
    // Set the /<baseUrl>/ pathname under which your site is served
    // For GitHub pages deployment, it is often '/<projectName>/'
    baseUrl: '/fastexcel/',

    // GitHub pages deployment config.
    // If you aren't using GitHub pages, you don't need these.
    organizationName: 'fast-excel',
    // Usually your GitHub org/user name.
    projectName: 'fastexcel',
    // Usually your repo name.
    onBrokenLinks: 'throw',
    onBrokenMarkdownLinks: 'warn',

    deploymentBranch: 'gh-pages',

    // Even if you don't use internationalization, you can use this field to set
    // useful metadata like html lang. For example, if your site is Chinese, you
    // may want to replace "en" with "zh-Hans".
    i18n: {
        defaultLocale: 'en',
        locales: ['en', 'zh-cn'],
        localeConfigs: {
            en: {
                label: 'English',
                htmlLang: 'en',
                path: 'en',
            },
            'zh-cn': {
                label: '简体中文',
                path: 'zh-cn',
            },
        },
    },

    presets: [
        [
            'classic',
            {
                docs: {
                    sidebarPath: './sidebars.js',
                    editUrl: `${repoUrl}/edit/${branch}/website`
                },
                theme: {
                    customCss: './src/css/custom.css'
                },
            }
        ],
    ],

    plugins: [
        [
            '@docusaurus/plugin-content-docs',
            {
                id: 'community',
                path: 'community',
                routeBasePath: 'community',
                sidebarPath: './sidebarsCommunity.js',
                editUrl: `${repoUrl}/edit/${branch}/website`
            },
        ],
    ],

    themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
        ({
            // Replace with your project's social card
            image: 'img/logo.svg',
            navbar: {
                title: '',
                logo: {
                    alt: '',
                    src: 'img/logo.svg',
                },
                items: [
                    {
                        label: 'Docs',
                        position: 'left',
                        to: '/docs/',
                    },
                    {
                        label: 'Community',
                        position: 'left',
                        to: '/community/contact',
                    },
                    {
                        type: 'localeDropdown',
                        position: 'right',
                    },
                    {
                        href: repoUrl,
                        position: 'right',
                        className: 'header-github-link',
                    },
                ],
            },
            metadata: [
                {
                    name: 'keywords',
                    content: 'fastexcel, fast-excel, excel.poi, opensource',
                }
            ],
            footer: {
                style: 'dark',
                links: [],
                copyright: `Copyright © ${new Date().getFullYear()} FastExcel, Licensed under the Apache License, Version 2.0.`,
            },
            prism: {
                theme: prismThemes.dracula,
                darkTheme: prismThemes.dracula,
                additionalLanguages: ['java', 'bash']
            },
        }),
    themes: ['@docusaurus/theme-mermaid'],
    markdown: {
        format: 'md',
        mermaid: true,
    }
};

export
default config;
