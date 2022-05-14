import { fetchDataPost, fetchDataGet } from './fetchData';

class UserService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = '/api/v1/users';
  }

  async getUserCourses(userId:number, userToken:string, params = {}) {
    const data = await (await fetchDataGet(`${this.baseUrl}/${userId}/courses`, params, userToken)).json();
    return data;
  }

  async getSuggestedCourses(userId:number, userToken:string) {
    const data = await (await fetchDataGet(`${this.baseUrl}/${userId}/courses/suggested`, {}, userToken)).json();
    return data;
  }

  async getMentors(params:object = {}) {
    const data = await (await fetchDataGet(`${this.baseUrl}/users/mentors`, params)).json();
    return data;
  }

  async savePreferredCategories(userId:number, userToken:string, content:Array<number>) {
    const data = await (await fetchDataPost(`${this.baseUrl}/${userId}/categories`, content, {}, userToken)).json();
    return data;
  }
}
export default new UserService();
