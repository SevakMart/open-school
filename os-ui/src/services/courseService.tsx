import fetchService from './fetchData';

class CourseService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'courses';
  }

  async getSearchedCourses(params:object = {}, token:string) {
    const data = await (await fetchService.get(`${this.basePath}`, params, token)).json();
    return data;
  }
}
export default new CourseService();
