import styles from './SignUp.module.scss';
import CloseIcon from '../../icons/Close';
import LinkedinIcon1 from '../../icons/Linkedin1';
import EmailIcon1 from '../../icons/Email1';
import { SIGN_IN } from '../../constants/Strings';
import Form from '../Forms/SignUpSignInForm';

const SignUp = ({ handleSignUpClicks }:{handleSignUpClicks(arg:string):void}) => {
  const {
    mainContainer, formContainer, headerContent, iconContent, alreadyHaveAccount,
  } = styles;

  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        <CloseIcon handleClosing={() => handleSignUpClicks('closeButton')} />
        <div className={headerContent}>
          <h2>Sign Up!</h2>
          <div className={iconContent}>
            <button type="button"><LinkedinIcon1 /></button>
            <button type="button"><EmailIcon1 /></button>
          </div>
          <p>Or</p>
        </div>
        <Form formType="signUp" />
        <p className={alreadyHaveAccount}>
          Have An Account?
          <span><button type="button">{SIGN_IN}</button></span>
        </p>
      </div>
    </div>
  );
};
export default SignUp;
