import fetchService from './fetchData';

class UserService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'users';
  }

  async getUserCourses(userId:number, userToken:string, params = {}) {
    const data = await (await fetchService.get(`${this.basePath}/${userId}/courses`, params, userToken)).json();
    return data;
  }

  async getSuggestedCourses(userId:number, userToken:string) {
    const data = await (await fetchService.get(`${this.basePath}/${userId}/courses/suggested`, {}, userToken)).json();
    return data;
  }

  async getMentors(params:object = {}) {
    const response = await fetchService.get(`${this.basePath}/mentors`, params);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async savePreferredCategories(userId:number, userToken:string, content:Array<number>) {
    const data = await (await fetchService.post(`${this.basePath}/${userId}/categories`, content, {}, userToken)).json();
    return data;
  }
}
export default new UserService();
