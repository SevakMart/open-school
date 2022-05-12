import { fetchDataPost } from './fetchData';
import { RegistrationFormType } from '../types/RegistartionFormType';
import { ResetPasswordType } from '../types/ResetPasswordType';

class AuthService {
  readonly baseUrl: string;

  constructor() {
    this.baseUrl = '/api/v1/auth';
  }

  async register(content:RegistrationFormType) {
    const data = await (await fetchDataPost(`${this.baseUrl}/register`, content, {})).json();
    return data;
  }

  async signIn(content:Omit<RegistrationFormType, 'firstName'>) {
    const response = await fetchDataPost(`${this.baseUrl}/login`, content, {});
    const { status, headers } = response;
    const data = await response.json();
    const FullTokenResponse = headers.get('Authorization');
    const tokenStartingIndex = FullTokenResponse ? FullTokenResponse.indexOf(' ') : undefined;
    const token = FullTokenResponse && tokenStartingIndex
      ? FullTokenResponse.slice(tokenStartingIndex + 1) : null;
    return { ...data, status, token };
  }

  async sendForgotPasswordRequest(content:string) {
    const response = await fetchDataPost(`${this.baseUrl}/password/forgot`, content, {});
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }

  async sendResetPasswordRequest(content:ResetPasswordType) {
    const response = await fetchDataPost(`${this.baseUrl}/password/reset`, content, {});
    const { status } = response;
    const data = await response.json();
    return { data, status };
  }
}

export default new AuthService();
