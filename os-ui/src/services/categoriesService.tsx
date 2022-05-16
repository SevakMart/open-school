import fetchService from './fetchData';

class CategoriesService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'categories';
  }

  async getCategories(params: object = {}) {
    const response = await fetchService.get(this.basePath, params);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async getSearchedCategories(params:object = {}) {
    const data = await (await fetchService.get(`${this.basePath}/subcategories`, params)).json();
    return data;
  }
}

export default new CategoriesService();
