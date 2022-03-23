import { useState } from 'react';
import styles from './SignUpSignInForm.module.scss';
import CloseIcon from '../../icons/Close';
import LinkedinIcon1 from '../../icons/Linkedin1';
import EmailIcon1 from '../../icons/Email1';
import HiddenIcon from '../../icons/Hidden';
import { SIGN_UP, SIGN_IN } from '../../constants/Strings';
// import Button from '../Button/Button';

interface FormProps{
  formType:string;
  formClick(arg:string):void;
}

const Form = ({ formType, formClick }:FormProps) => {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const {
    mainContainer, formContainer, headerContent, iconContent,
    inputContent, alreadyHaveAccount,
    passwordVisibilityIcon,
  } = styles;

  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        <CloseIcon handleClosing={() => formClick('closeButton')} />
        <div className={headerContent}>
          {formType === 'signUp' ? <h2>Sign Up!</h2> : <h2>Sign In!</h2>}
          <div className={iconContent}>
            <button type="button"><LinkedinIcon1 /></button>
            <button type="button"><EmailIcon1 /></button>
          </div>
          <p>Or</p>
        </div>
        <form className={inputContent}>
          {formType === 'signUp'
            ? (
              <div>
                <label htmlFor="fullName">
                  Full Name
                  <span style={{ color: 'red' }}>*</span>
                </label>
                <input id="fullName" type="text" value={fullName} name="fullName" placeholder="Fill in first name" />
              </div>
            ) : null}
          <div>
            <label htmlFor="email">
              Email
              <span style={{ color: 'red' }}>*</span>
            </label>
            <input id="email" type="email" value={email} name="email" placeholder="ex: namesurname@gmail.com" />
          </div>
          <div>
            <label htmlFor="password">
              Password
              <span style={{ color: 'red' }}>*</span>
            </label>
            <input id="password" type="password" value={password} name="password" placeholder="Enter your password" />
            <i className={passwordVisibilityIcon}><HiddenIcon /></i>
          </div>
          <p>Forgot Password?</p>
          {formType === 'signUp' ? <button type="submit">{SIGN_UP}</button> : <button type="submit">{SIGN_IN}</button>}
        </form>
        {formType === 'signUp'
          ? (
            <p className={alreadyHaveAccount}>
              Have An Account?
              <span>
                <button type="button">{SIGN_IN}</button>
              </span>
            </p>
          ) : (
            <p className={alreadyHaveAccount}>
              Dont Have An Account?
              <span>
                <button type="button">{SIGN_UP}</button>
              </span>
            </p>
          )}
      </div>
    </div>
  );
};
export default Form;
