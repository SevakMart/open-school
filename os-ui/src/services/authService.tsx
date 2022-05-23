import fetchService from './fetchData';
import { RegistrationFormType } from '../types/RegistartionFormType';
import { ResetPasswordType } from '../types/ResetPasswordType';

class AuthService {
  readonly basePath: string;

  constructor() {
    this.basePath = 'auth';
  }

  async register(content:RegistrationFormType) {
    const data = await (await fetchService.post(`${this.basePath}/register`, content, {})).json();
    return data;
  }

  async signIn(content:Omit<RegistrationFormType, 'firstName'>) {
    const response = await fetchService.post(`${this.basePath}/login`, content, {});
    const { status, headers } = response;
    const data = await response.json();
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