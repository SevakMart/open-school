import fetchService from './fetchData';

class FeatureService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'features';
  }

  async getFilterFeatures(params:object = {}, token:string) {
    const data = await (await fetchService.get(`${this.basePath}/courses/searched`, params, token)).json();
    return data;
  }
}
export default new FeatureService();
