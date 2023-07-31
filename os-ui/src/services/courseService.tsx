/* eslint-disable class-methods-use-this */
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

  async findSearchedQuestionPeers(enrolledCourseId: number, params: object = {}, token:string) {
    const response = await fetchService.get(`${this.basePath}/enrolled/${enrolledCourseId}/peers-questions`, params, token);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async findSearchedQuestionMentor(enrolledCourseId: number, params: object = {}, token:string) {
    const response = await fetchService.get(`${this.basePath}/enrolled/${enrolledCourseId}/mentor-questions`, params, token);
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async getCourseVideoLink(id: any) {
    const response = await fetchService.get(id);
    const data = await response.json();
    return data;
  }
}
export default new CourseService();
