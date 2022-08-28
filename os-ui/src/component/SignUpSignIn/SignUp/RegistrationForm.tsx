import SignUpDefault from './Default/Default';

const SignUpRegistrationForm = ({ registrationForm, switchToSignInForm }:
  {registrationForm:string, switchToSignInForm:(message:string)=>void}) => (
    <>
      {registrationForm === 'default' && <SignUpDefault switchToSignInForm={(message:string) => switchToSignInForm(message)} />}
    </>
);
export default SignUpRegistrationForm;
