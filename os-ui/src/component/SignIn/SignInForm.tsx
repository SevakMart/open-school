import SignInDefault from './Default/Default';

const SignInForm = ({ signInForm, handleSignIn }:
    {signInForm:string, handleSignIn:(message:string)=>void}) => (
      <>
        {
        signInForm === 'default'
          ? (
            <SignInDefault
              handleSignIn={(message:string) => handleSignIn(message)}
            />
          ) : null
        }
      </>
);
export default SignInForm;
