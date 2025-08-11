import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';
import HomepageFeatures from '@site/src/components/HomepageFeatures';

import Heading from '@theme/Heading';
import styles from './index.module.css';

function HomepageHeader() {
  const {siteConfig} = useDocusaurusContext();
  return (
      <header className={clsx('hero hero--primary', styles.heroBanner)}>
        <div className="container">
          <Heading as="h1" className="hero__title">
            {siteConfig.title}
          </Heading>
          <p className="hero__subtitle">{siteConfig.tagline}</p>
          <div className={styles.buttons}>
            <Link
                className="button button--secondary button--lg"
                to="/docs/zh/intro">
              Get Started
            </Link>
          </div>
        </div>
      </header>
  );
}

export default function Home() {
  const {siteConfig} = useDocusaurusContext();
  return (
      <Layout
          title={`FastExcel 官方文档| 快速、简洁、解决大文件内存溢出的java处理Excel工具`}
          description="FastExcel Official Documentation | Fast, concise, Java tool for processing Excel files that solves memory overflow issues with large files <head />">
        <HomepageHeader/>
        <main>
          <HomepageFeatures/>
        </main>
      </Layout>
  );
}
