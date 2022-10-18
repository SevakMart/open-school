import fetchService from './fetchData';

class CategoriesService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'categories';
  }

  async getCategories(params: object = {}, token:string) {
    const response = await fetchService.get(`${this.basePath}/categories/parentCategories`, params, token);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async getSearchedCategories(params: object = {}, token:string) {
    const data = await (await fetchService.get(`${this.basePath}/subcategories`, params, token)).json();
    return data;
  }
}

export default new CategoriesService();
