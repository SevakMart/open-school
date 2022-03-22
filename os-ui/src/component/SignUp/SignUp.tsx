import { useState } from 'react';
import styles from './SignUp.module.scss';
import CloseIcon from '../../icons/Close';
import LinkedinIcon1 from '../../icons/Linkedin1';
import EmailIcon1 from '../../icons/Email1';
import HiddenIcon from '../../icons/Hidden';
import { SIGN_UP, SIGN_IN } from '../../constants/Strings';
import Button from '../Button/Button';

const SignUp = () => {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const {
    mainContainer, formContainer, headerContent, iconContent,
    inputContent, closeButton, alreadyHaveAccount,
    passwordVisibilityIcon,
  } = styles;
  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        <button className={closeButton} type="button"><CloseIcon /></button>
        <div className={headerContent}>
          <h2>Sign Up!</h2>
          <div className={iconContent}>
            <button type="button"><LinkedinIcon1 /></button>
            <button type="button"><EmailIcon1 /></button>
          </div>
          <p>Or</p>
        </div>
        <form className={inputContent}>
          <div>
            <label htmlFor="fullName">
              Full Name
              <span style={{ color: 'red' }}>*</span>
            </label>
            <br />
            <br />
            <input id="fullName" type="text" value={fullName} name="fullName" placeholder="Fill in first name" />
          </div>
          <div>
            <label htmlFor="email">
              Email
              <span style={{ color: 'red' }}>*</span>
            </label>
            <br />
            <br />
            <input id="email" type="email" value={email} name="email" placeholder="ex: namesurname@gmail.com" />
          </div>
          <div>
            <label htmlFor="password">
              Password
              <span style={{ color: 'red' }}>*</span>
            </label>
            <br />
            <br />
            <input id="password" type="password" value={password} name="password" placeholder="Enter your password" />
            <i className={passwordVisibilityIcon}><HiddenIcon /></i>
          </div>
          <p>Forgot Password?</p>
          <button type="submit">{SIGN_UP}</button>
        </form>
        <p className={alreadyHaveAccount}>
          Have An Account?
          <span>
            <Button buttonType="signIn">
              {SIGN_IN}
            </Button>
          </span>
        </p>
      </div>
    </div>
  );
};
export default SignUp;
