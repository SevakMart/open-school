import { fetchDataGet } from './fetchData';

class CategoriesService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = '/api/v1/categories';
  }

  async getCategories(params: object = {}) {
    const data = await (await fetchDataGet(this.baseUrl, params)).json();
    return data;
  }

  async getSearchedCategories(params:object = {}) {
    const data = await (await fetchDataGet(`${this.baseUrl}/subcategories`, params)).json();
    return data;
  }
}

export default new CategoriesService();
