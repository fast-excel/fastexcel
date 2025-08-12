import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';
import HomepageFeatures from '@site/src/components/HomepageFeatures';
import Translate from '@docusaurus/Translate';

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
          <p className="hero__subtitle">
              <Translate>site.description</Translate>
          </p>
          <div className={styles.buttons}>
            <Link
                className="button button--secondary button--lg"
                to="/docs/quickstart/guide">
                <Translate>quickstart</Translate>
            </Link>

            <Link
                className="button button--secondary button--lg"
                to="https://github.com/fast-excel/fastexcel">
                <img
                    src="img/github_icon.svg"
                    alt="GitHub"
                    className={styles.buttonIcon}
                />
                <Translate>github</Translate>
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
          title=""
          description="FastExcel Official Documentation | Fast, concise, Java tool for processing Excel files that solves memory overflow issues with large files <head />">
        <HomepageHeader/>
        <main>
          <HomepageFeatures/>
        </main>
      </Layout>
  );
}
