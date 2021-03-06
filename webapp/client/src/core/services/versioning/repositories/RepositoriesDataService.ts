import { bind } from 'decko';

import { BaseDataService } from 'core/services/BaseDataService';
import { IServerRepository } from 'core/services/serverModel/Versioning/Repository/Repository';
import { IRepository } from 'core/shared/models/Versioning/Repository';

export default class RepositoriesDataService extends BaseDataService {
  constructor() {
    super();
  }

  @bind
  public async loadRepositoryName(
    id: IRepository['id']
  ): Promise<IRepository['name']> {
    const response = await this.get<{ repository: IServerRepository }>({
      url: `/v1/modeldb/versioning/repositories/${id}`,
    });
    const serverRepository = response.data.repository;
    return serverRepository.name;
  }
}
