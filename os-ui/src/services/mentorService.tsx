import fetchService from './fetchData';

class MentorService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'mentors';
  }

  async requestAllMentors(userToken:string, params = {}) {
    const response = await (await fetchService.get(`${this.basePath}`, params, userToken));
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async requestUserSavedMentors(userId:number, userToken:string, params = {}) {
    const data = await (await fetchService.get(`${this.basePath}/${userId}`, params, userToken)).json();
    return data;
  }

  async searchMentorsByName(userToken:string, params = {}) {
    const data = await (await fetchService.get(`${this.basePath}/searched`, params, userToken)).json();
    return data;
  }

  async searchSavedMentorsByName(userId:number, userToken:string, params = {}) {
    const data = await (await fetchService.get(`${this.basePath}/searched/${userId}`, params, userToken)).json();
    return data;
  }
}
export default new MentorService();
