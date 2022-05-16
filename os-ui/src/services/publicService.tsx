import fetchService from './fetchData';

class PublicService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'public';
  }

  async getPublicCategories(params: object = {}) {
    const data = await (await fetchService.get(`${this.basePath}/categories`, params)).json();
    return data;
  }

  async getPublicMentors(params:object = {}) {
    const data = await (await fetchService.get(`${this.basePath}/users/mentors`, params)).json();
    return data;
  }
}

export default new PublicService();
