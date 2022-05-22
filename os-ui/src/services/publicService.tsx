import fetchService from './fetchData';

class PublicService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'public';
  }

  async getPublicCategories(params: object = {}) {
    const response = await fetchService.get(`${this.basePath}/categories`, params);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async getPublicMentors(params:object = {}) {
    const response = await fetchService.get(`${this.basePath}/users/mentors`, params);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }
}

export default new PublicService();
