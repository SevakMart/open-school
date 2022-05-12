import { fetchDataGet } from './fetchData';

class PublicService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = '/api/v1/public';
  }

  async getPublicCategories(params: object = {}) {
    const data = await (await fetchDataGet(`${this.baseUrl}/categories`, params)).json();
    return data;
  }

  async getPublicMentors(params:object = {}) {
    const data = await (await fetchDataGet(`${this.baseUrl}/users/mentors`, params)).json();
    return data;
  }
}

export default new PublicService();
