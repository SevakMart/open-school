import { fetchDataGet } from './fetchData';

class PublicService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = '/api/v1/public';
  }

  async getPublicCategories(params: object = {}) {
    const data = await fetchDataGet(`${this.baseUrl}/categories`, params);
    return data;
  }

  async getPublicMentors(params:object = {}) {
    const data = await fetchDataGet(`${this.baseUrl}/users/mentors`, params);
    return data;
  }
}

export default new PublicService();
