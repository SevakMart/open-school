import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import Form from '../Form';

const initialFormValues = {
  firstName: '', lastName: '', email: '', psd: '', token: '', newPassword: '', confirmedPassword: '',
};
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));
describe('Create unit tests for Form component', () => {
  test('Make snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Form
          isSignUpForm
          isResetPasswordForm={false}
          formButtonText="Sign Up"
          errorFormValue={initialFormValues}
          handleForm={jest.fn()}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
