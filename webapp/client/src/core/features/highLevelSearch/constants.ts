import { IPaginationSettings } from 'core/shared/models/Pagination';
import { ActiveFilter } from 'core/shared/models/HighLevelSearch';

export const paginationSettings: IPaginationSettings = {
  currentPage: 0,
  pageSize: 10,
};
export const defaultFilter: ActiveFilter = 'projects';
