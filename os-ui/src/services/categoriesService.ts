import { fetchDataGet } from './fetchData';

class CategoriesService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = 'http://localhost:5000/api/v1/categories';
  }

  async getCategories(params: object = {}) {
    const data = await fetchDataGet(this.baseUrl, params);
    return data;
  }

  async getSearchedCategories(params:object = {}) {
    const data = await fetchDataGet(`${this.baseUrl}/subcategories`, params);
    console.log(data);
    return data;
  }
}

export default new CategoriesService();
