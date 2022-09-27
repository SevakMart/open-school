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

  async requestCourseDescription(courseId:number, params:object = {}, token:string) {
    const response = (await fetchService.get(`${this.basePath}/${courseId}`, params, token));
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }
}
export default new CourseService();
