import SignInDefault from './Default/Default';

const SignInForm = ({ signInForm, handleSignIn, forgotPasswordHandler }:
    {signInForm:string, handleSignIn:(message:string)=>void, forgotPasswordHandler:()=>void}) => {
  const handleForgotPassword = () => forgotPasswordHandler();

  return (
    <>
      {
        signInForm === 'default'
          ? (
            <SignInDefault
              handleSignIn={(message:string) => handleSignIn(message)}
              forgotPasswordFunc={handleForgotPassword}
            />
          ) : null
        }
    </>
  );
};
export default SignInForm;
