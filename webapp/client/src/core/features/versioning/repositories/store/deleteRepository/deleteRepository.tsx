import gql from 'graphql-tag';
import { useMutation } from 'react-apollo';
import * as React from 'react';

import { hasAccessToAction } from 'models/EntitiesActions';
import DeleteFAI from 'core/shared/view/elements/DeleteFAI/DeleteFAI';
import { IRepository } from 'core/shared/models/Versioning/Repository';
import { mutationResultToCommunication } from 'core/shared/utils/graphql/queryResultToCommunicationWithData';

import * as Types from './graphql-types/DeleteRepository';
import { toastCommunicationError } from 'core/shared/view/elements/Notification/Notification';
import onCompletedUpdate from 'core/shared/utils/graphql/onCompletedUpdate';

const DELETE_REPOSITORY = gql`
  mutation DeleteRepository($id: ID!) {
    repository(id: $id) {
      id
      delete
    }
  }
`;
export function useDeleteRepositoryMutation({
  repository,
  onDeleted,
}: {
  repository: IRepository;
  onDeleted: () => void;
}) {
  const [deleteRepositoryMutation, mutationResult] = useMutation<
    Types.DeleteRepository,
    Types.DeleteRepositoryVariables
  >(DELETE_REPOSITORY);
  const deleteRepository = (variables: Types.DeleteRepositoryVariables) =>
    deleteRepositoryMutation({
      variables,
      update: onCompletedUpdate(() => {
        onDeleted();
      }),
    });
  const communication = mutationResultToCommunication(mutationResult);

  const deleteRepositoryButton = hasAccessToAction('delete', repository) ? (
    <DeleteFAI
      confirmText="Are you sure?"
      onDelete={() => deleteRepository({ id: repository.id })}
    />
  ) : (
    undefined
  );
  React.useEffect(() => {
    if (communication.error) {
      toastCommunicationError(communication.error);
    }
  }, [communication.error]);

  return {
    deleteRepositoryButton,
    deletingRepository: mutationResultToCommunication(mutationResult),
  };
}
