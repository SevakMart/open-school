import { fetchDataGet } from './fetchData';

class CategoriesService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = '/api/v1/categories';
  }

  async getCategories(params: object = {}) {
    const data = await fetchDataGet(this.baseUrl, params);
    return data;
  }

  async getSearchedCategories(params:object = {}) {
    const data = await fetchDataGet(`${this.baseUrl}/subcategories`, params);
    return data;
  }
}

export default new CategoriesService();
