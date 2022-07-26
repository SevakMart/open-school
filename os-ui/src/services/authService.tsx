import fetchService from './fetchData';
import { RegistrationFormType } from '../types/RegistartionFormType';
import { ResetPasswordType } from '../types/ResetPasswordType';

class AuthService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'auth';
  }

  async register(content:RegistrationFormType) {
    const response = await fetchService.post(`${this.basePath}/register`, content, {});
    const { status } = response;
    const data = await response.json();
    return { ...data, status };
  }

  async signIn(content:Omit<RegistrationFormType, 'firstName' | 'lastName'>) {
    const response = await fetchService.post(`${this.basePath}/login`, content, {});
    const { status, headers } = response;
    const data = await response.json();
    const FullTokenResponse = headers.get('Authorization');
    const tokenStartingIndex = FullTokenResponse ? FullTokenResponse.indexOf(' ') : undefined;
    const token = FullTokenResponse && tokenStartingIndex
      ? FullTokenResponse.slice(tokenStartingIndex + 1) : null;
    return { ...data, status, token };
  }

  async redirectUserToAccount(content:object) {
    const response = await fetchService.post(`${this.basePath}/account`, content, {});
    const { status, headers } = response;
    let data = {};
    if (response.status === 200) {
      data = await response.json();
    }
    const FullTokenResponse = headers.get('Authorization');
    const tokenStartingIndex = FullTokenResponse ? FullTokenResponse.indexOf(' ') : undefined;
    const token = FullTokenResponse && tokenStartingIndex
      ? FullTokenResponse.slice(tokenStartingIndex + 1) : null;
    return { ...data, status, token };
  }

  async sendForgotPasswordRequest(content:object) {
    const response = await fetchService.post(`${this.basePath}/password/forgot`, content, {});
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async sendResetPasswordRequest(content:ResetPasswordType) {
    const response = await fetchService.post(`${this.basePath}/password/reset`, content, {});
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }
}

export default new AuthService();
