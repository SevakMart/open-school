import { fetchDataGet } from './fetchData';

class CategoriesService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = '/api/v1/categories';
  }

  getCategories(params: object = {}) {
    return fetchDataGet(this.baseUrl, params);
  }
}

export default new CategoriesService();
