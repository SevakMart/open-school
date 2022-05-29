import fetchService from './fetchData';

class CourseService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'courses';
  }

  async getFilterFeatures(params:object = {}, token:string) {
    const data = await (await fetchService.get(`${this.basePath}/features`, params, token)).json();
    return data;
  }
}
export default new CourseService();
