import cn from 'classnames';
import { bind } from 'decko';
import * as React from 'react';
import { connect } from 'react-redux';

import CodeVersion from 'core/shared/view/domain/CodeVersion/CodeVersion';
import ProjectEntityDescriptionManager from 'features/descriptionManager/view/ProjectEntityDescriptionManager/ProjectEntityDescriptionManager';
import ProjectEntityTagsManager from 'features/tagsManager/view/ProjectEntityTagsManager/ProjectEntityTagsManager';
import WithCurrentUserActionsAccesses from 'core/shared/view/domain/WithCurrentUserActionsAccesses/WithCurrentUserActionsAccesses';
import { ICommunication } from 'core/shared/utils/redux/communication';
import Avatar from 'core/shared/view/elements/Avatar/Avatar';
import Draggable from 'core/shared/view/elements/Draggable/Draggable';
import Experiment from 'models/Experiment';
import { selectDeletingExperiment } from 'features/experiments/store';
import { IConnectedReduxProps, IApplicationState } from 'store/store';

import ExperimentBulkDeletion from './ExperimentBulkDeletion/ExperimentBulkDeletion';
import styles from './ExperimentWidget.module.css';
import { unknownUser } from 'models/User';

interface ILocalProps {
  projectId: string;
  experiment: Experiment;
  onViewExprRuns(): void;
}

interface IPropsFromState {
  deletingExperiment: ICommunication;
}

type AllProps = ILocalProps & IPropsFromState & IConnectedReduxProps;

class ExperimentWidget extends React.PureComponent<AllProps> {
  public render() {
    const { projectId, experiment, deletingExperiment } = this.props;

    return (
      <WithCurrentUserActionsAccesses
        entityType="experiment"
        entityId={experiment.id}
        actions={['delete']}
      >
        {({ actionsAccesses }) => (
          <ExperimentBulkDeletion
            id={experiment.id}
            isEnabled={actionsAccesses.delete}
          >
            {togglerForBulkDeletion => (
              <div
                className={cn(styles.root, {
                  [styles.deleting]: deletingExperiment.isRequesting,
                })}
                data-test="experiment"
                onClick={this.onViewExprRuns}
              >
                <div className={styles.content}>
                  <div className={styles.title_block}>
                    <div className={styles.title} data-test="experiment-name">
                      {experiment.name}
                    </div>
                    <div>
                      <span onClick={this.preventOnViewExprRuns}>
                        <ProjectEntityDescriptionManager
                          entityId={experiment.id}
                          description={experiment.description}
                          entityType={'experiment'}
                        />
                      </span>
                    </div>
                  </div>
                  <div className={styles.tags_block}>
                    <ProjectEntityTagsManager
                      id={experiment.id}
                      projectId={projectId}
                      tags={experiment.tags}
                      entityType="experiment"
                      isDraggableTags={true}
                      onClick={this.onTagsManagerClick}
                    />
                  </div>
                  {experiment.codeVersion && (
                    <div className={styles.codeVersionBlock}>
                      <div className={styles.codeVersionBlock__label}>
                        Code version:
                      </div>
                      <div onClick={this.preventOnViewExprRuns}>
                        <CodeVersion
                          entityType="experiment"
                          entityId={experiment.id}
                          codeVersion={experiment.codeVersion}
                        />
                      </div>
                    </div>
                  )}
                  <div className={styles.owner_block}>
                    <div className={styles.owner_username}>
                      <div>{unknownUser.username}</div>
                      <div className={styles.owner_status}>Owner</div>
                    </div>
                    <Avatar
                      username={unknownUser.username}
                      sizeInPx={36}
                      picture={unknownUser.picture}
                    />
                  </div>
                  <div className={styles.created_date_block}>
                    <div className={styles.created_date}>
                      Created: {experiment.dateCreated.toLocaleDateString()}
                    </div>
                    <div>
                      Updated: {experiment.dateUpdated.toLocaleDateString()}
                    </div>
                  </div>
                  <div className={styles.actions}>
                    {togglerForBulkDeletion && (
                      <div
                        className={cn(styles.action, {
                          [styles.action_delete]: true,
                        })}
                        onClick={this.preventOnViewExprRuns}
                      >
                        {togglerForBulkDeletion}
                      </div>
                    )}
                  </div>
                </div>
              </div>
            )}
          </ExperimentBulkDeletion>
        )}
      </WithCurrentUserActionsAccesses>
    );
  }

  @bind
  private onTagsManagerClick(e: React.MouseEvent, byEmptiness: boolean) {
    !byEmptiness ? this.preventOnViewExprRuns(e) : undefined;
  }

  @bind
  private preventOnViewExprRuns(e: React.MouseEvent) {
    e.stopPropagation();
  }

  @bind
  private onViewExprRuns(e: React.MouseEvent<any>) {
    this.props.onViewExprRuns();
  }
}

const mapStateToProps = (
  state: IApplicationState,
  localProps: ILocalProps
): IPropsFromState => {
  return {
    deletingExperiment: selectDeletingExperiment(
      state,
      localProps.experiment.id
    ),
  };
};

export default connect(mapStateToProps)(ExperimentWidget);
