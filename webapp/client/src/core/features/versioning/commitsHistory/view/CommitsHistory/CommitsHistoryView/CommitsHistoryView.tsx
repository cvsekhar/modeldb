import * as React from 'react';

import groupCommitsByDatesInDescOrder from 'core/features/versioning/commitsHistory/helpers/groupCommitsByDatesInDescOrder';
import {
  ICommitHistorySettings,
  ICommitView,
} from 'core/features/versioning/commitsHistory/store/types';
import { IRepository } from 'core/shared/models/Versioning/Repository';
import Placeholder from 'core/shared/view/elements/Placeholder/Placeholder';

import CommitsHistoryBreadcrumbs from './CommitsHistoryBreadcrumbs/CommitsHistoryBreadcrumbs';
import styles from './CommitsHistoryView.module.css';
import GroupedCommitsByDate from './GroupedCommitsByDate/GroupedCommitsByDate';
import { PageHeader } from 'core/shared/view/elements/PageComponents';
import { RepositoryNavigation } from 'core/features/versioning/repositoryNavigation';

interface ILocalProps {
  repository: IRepository;
  settings: ICommitHistorySettings;
  commits: ICommitView[];
}

type AllProps = ILocalProps;

const CommitsHistoryView = ({ repository, settings, commits }: AllProps) => {
  return (
    <div className={styles.root}>
      <PageHeader
        title={`History for ${repository.name}`}
        withoutSeparator={true}
        rightContent={<RepositoryNavigation />}
      />
      <div className={styles.breadcrumbs}>
        <CommitsHistoryBreadcrumbs
          repositoryName={repository.name}
          settings={settings}
        />
      </div>
      {commits.length > 0 ? (
        <div>
          <div className={styles.groups}>
            {groupCommitsByDatesInDescOrder(commits).map((group, i) => (
              <div className={styles.group} key={i}>
                <GroupedCommitsByDate
                  data={group}
                  repositoryName={repository.name}
                />
              </div>
            ))}
          </div>
          {/* <div className={styles.pagination}>
            <Pagination
              onCurrentPageChange={(newPage) => {
                history.push(routes.repositoryCommitsHistory.getRedirectPathWithQueryParams({
                  params: {
                    ...routes.repositoryCommitsHistory.getMatch(history.location.pathname)!,
                    commitPointerValue: settings.branch,
                  },
                  queryParams: {
                    page: String(newPage + 1),
                  },
                }));
              }}
              pagination={{
                currentPage: settings.currentPage,
                pageSize: paginationPageSize,
                totalCount: commitsWithPagination.totalCount,
              }}
            />
          </div> */}
        </div>
      ) : (
        <Placeholder>There are not any commits to show</Placeholder>
      )}
    </div>
  );
};

export default CommitsHistoryView;
