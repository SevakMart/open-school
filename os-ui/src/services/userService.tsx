import fetchService from './fetchData';

class UserService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'users';
  }

  async getUserCourses(userId:number, userToken:string, params = {}) {
    const response = await fetchService.get(`${this.basePath}/${userId}/courses/enrolled`, params, userToken);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async getSuggestedCourses(userId:number, userToken:string) {
    const response = await fetchService.get(`${this.basePath}/${userId}/courses/suggested`, {}, userToken);
    const { status } = response;
    const data = await response.json();
    return { data, status };
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

  async saveUserPreferredCourses(userId:number, courseId:number, userToken:string) {
    const data = await (await fetchService.post(`${this.basePath}/${userId}/courses/saved`, { courseId }, {}, userToken)).json();
    return data;
  }

  async getUserSavedCourses(userId:number, userToken:string, params = {}) {
    const data = await (await fetchService.get(`${this.basePath}/${userId}/courses/saved`, params, userToken)).json();
    return data;
  }

  async deleteUserSavedCourses(userId:number, courseId:number, userToken:string, params = {}) {
    const response = await fetchService.delete(`${this.basePath}/${userId}/courses/${courseId}/saved`, params, userToken);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async saveUserMentor(userId:number, mentorId:number, userToken:string, params = {}) {
    const data = await (await fetchService.post(`${this.basePath}/${userId}/mentors/${mentorId}`, null, params, userToken)).json();
    return data;
  }

  async deleteUserSavedMentor(userId:number, mentorId:number, userToken:string, params = {}) {
    const response = await fetchService.delete(`${this.basePath}/${userId}/mentors/${mentorId}/saved`, params, userToken);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }
}
export default new UserService();
