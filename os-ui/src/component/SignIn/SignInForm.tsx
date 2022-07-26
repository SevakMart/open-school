import SignInDefault from './Default/Default';

const SignInForm = ({ signInForm, handleSignIn, forgotPasswordHandler }:
    {signInForm:string, handleSignIn:()=>void, forgotPasswordHandler:()=>void}) => {
  const handleForgotPassword = () => forgotPasswordHandler();

  return (
    <>
      {
        signInForm === 'default'
          ? (
            <SignInDefault
              handleSignIn={() => handleSignIn()}
              forgotPasswordFunc={handleForgotPassword}
            />
          ) : null
        }
    </>
  );
};
export default SignInForm;
