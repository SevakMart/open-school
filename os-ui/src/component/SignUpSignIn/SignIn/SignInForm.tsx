import SignInDefault from './Default/Default';

const SignInForm = ({ signInForm, handleSignIn }:{signInForm:string, handleSignIn:()=>void}) => (
  <>
    {signInForm === 'default'
    && (
    <div style={{ marginTop: '-10px' }}>
      <SignInDefault handleSignIn={() => handleSignIn()} />
    </div>
    )}
  </>
);
export default SignInForm;
