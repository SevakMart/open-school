import { request } from './requestService';

class FetchService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = '/api/v1';
  }

  async get(path: string, params: object = {}, token = '') {
    return request(`${this.baseUrl}/${path}`, 'GET', params, token);
  }

  async post(path: string, body: object | null, params: object, token = '') {
    return request(`${this.baseUrl}/${path}`, 'POST', params, token, body);
  }

  async delete(path: string, params: object = {}, token = '') {
    return request(`${this.baseUrl}/${path}`, 'DELETE', params, token);
  }

  async put(path: string, body: object | null, params: object = {}, token = '') {
    return request(`${this.baseUrl}/${path}`, 'PUT', params, token, body);
  }
}
export default new FetchService();
