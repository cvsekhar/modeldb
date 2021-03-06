import { IServerFiltersInRequest } from 'core/features/filter/service/serverModel/Filters/converters';
import { IFilterData } from 'core/features/filter/Model';
import { IPagination } from 'core/shared/models/Pagination';
import { IWorkspace } from 'models/Workspace';
import { makeAddFiltersToRequestWithDefaultFilters } from 'features/filter/service/serverModel/Filter/converters';
import { addPaginationToRequest } from 'core/services/serverModel/Pagination/converters';
import { IServerPaginationInRequest } from 'core/services/serverModel/Pagination/Pagination';
import {
  addWorkspaceName,
  IServerEntityWithWorkspaceName,
} from 'services/serverModel/Workspace/converters';
import { ISorting } from 'core/shared/models/Sorting';
import { addSorting } from 'services/serverModel/Sorting/Sorting';

export type ILoadProjectsRequest = IServerFiltersInRequest &
  IServerPaginationInRequest &
  IServerEntityWithWorkspaceName;
type ITransformedLoadProjectsRequest = Partial<ILoadProjectsRequest>;

const addFilters = makeAddFiltersToRequestWithDefaultFilters<
  ITransformedLoadProjectsRequest
>();

const makeLoadProjectsRequest = (
  filters: IFilterData[],
  pagination?: IPagination,
  workspaceName?: IWorkspace['name'],
  sorting?: ISorting
): Promise<ILoadProjectsRequest> => {
  return Promise.resolve({})
    .then(
      pagination
        ? addPaginationToRequest<ITransformedLoadProjectsRequest>(pagination)
        : pagination
    )
    .then(workspaceName ? addWorkspaceName(workspaceName) : request => request)
    .then(addFilters(filters))
    .then(sorting ? addSorting(sorting) : request => request) as Promise<
    ILoadProjectsRequest
  >;
};

export default makeLoadProjectsRequest;
