import fetchService from './fetchData';

class UserService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'users';
  }

  async getUserCourses(userToken:string, params = {}) {
    const data = await (await fetchService.get(`${this.basePath}/{userId}/courses/enrolled`, params, userToken)).json();
    return data;
  }

  async getSuggestedCourses(userId:number, userToken:string) {
    const data = await (await fetchService.get(`${this.basePath}/${userId}/courses/suggested`, {}, userToken)).json();
    return data;
  }

  async getMentors(params:object = {}, token:string) {
    const response = await fetchService.get(`${this.basePath}/mentors`, params, token);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async savePreferredCategories(userId:number, userToken:string, content:Array<number>) {
    const data = await (await fetchService.post(`${this.basePath}/${userId}/categories`, content, {}, userToken)).json();
    return data;
  }

  async saveUserPreferredCourses(courseId:number, userToken:string) {
    const data = await (await fetchService.post(`${this.basePath}/{userId}/courses/saved`, { courseId }, {}, userToken)).json();
    return data;
  }

  async getUserSavedCourses(userToken:string, params = {}) {
    const data = await (await fetchService.get(`${this.basePath}/{userId}/courses/saved`, params, userToken)).json();
    return data;
  }

  async deleteUserSavedCourses(courseId:number, userToken:string, params = {}) {
    const data = await (await fetchService.delete(`${this.basePath}/{userId}/courses/${courseId}/saved`, params, userToken)).json();
    console.log(data);
    return data;
  }
}
export default new UserService();
