import SignUpDefault from './Default/Default';

const SignUpRegistrationForm = ({ registrationForm }:{registrationForm:string, }) => (
  <>
    {registrationForm === 'default' && <SignUpDefault />}
  </>
);
export default SignUpRegistrationForm;
