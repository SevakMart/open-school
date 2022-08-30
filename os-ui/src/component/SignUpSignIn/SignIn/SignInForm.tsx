import SignInDefault from './Default/Default';

const SignInForm = ({ signInForm }:{signInForm:string}) => (
  <>
    {signInForm === 'default' && <SignInDefault />}
  </>
);
export default SignInForm;
