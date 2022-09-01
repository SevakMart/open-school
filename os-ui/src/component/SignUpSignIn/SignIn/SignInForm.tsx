import SignInDefault from './Default/Default';

const SignInForm = ({ signInForm, handleSignIn }:{signInForm:string, handleSignIn:()=>void}) => (
  <>
    {signInForm === 'default' && <SignInDefault handleSignIn={() => handleSignIn()} />}
  </>
);
export default SignInForm;
