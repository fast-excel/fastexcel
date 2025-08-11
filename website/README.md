# FastExcel Website

This website is built using [Docusaurus](https://docusaurus.io/), a modern static website generator.

## Requirements

- [Node.js](https://nodejs.org/en/download/) version 18.0 or above (which can be checked by running `node -v`). You can use [nvm](https://github.com/nvm-sh/nvm) for managing multiple Node versions on a single machine installed.
- When installing Node.js, you are recommended to check all checkboxes related to dependencies.

## Installation

```bash
yarn install
# yarn install --registry=https://registry.npmmirror.com
```

## Local Development

```bash
# English
yarn start

# Chinese
yarn start --locale zh-cn
```

This command starts a local development server and opens up a browser window. Most changes are reflected live without having to restart the server.

## I18N

```bash
# Chinese
yarn write-translations --locale zh-cn

# English
yarn write-translations --locale en
```


## Build

```bash
yarn build
```

This command generates static content into the `build` directory and can be served using any static contents hosting service.

## Deployment

Using SSH:

```bash
USE_SSH=true yarn deploy
```

Not using SSH:

```bash
GIT_USER=<Your GitHub username> yarn deploy
```

If you are using GitHub pages for hosting, this command is a convenient way to build the website and push to the `gh-pages` branch.

## Directory Structure

```bash
|-- community             # community directory
|-- docs                  # document directory
|-- i18n
|   `-- zh-cn             # internationalized chinese
|       |-- code.json
|       |-- docusaurus-plugin-content-docs
|       |-- docusaurus-plugin-content-community
|       `-- docusaurus-theme-classic
|-- src
|-- static
|   |-- img               # picture static resource
|   |   |-- blog          # blog picture
|   |   |-- docs          # document picture
|   |   |-- index         # homepage picture
|-- docusaurus.config.js
|-- sidebars.js           # document sidebar menu configuration
|-- sidebarsCommunity.js  # community document sidebar menu configuration
```
