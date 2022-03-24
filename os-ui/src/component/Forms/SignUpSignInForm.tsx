import React, { useState, useRef, useEffect } from 'react';
import styles from './SignUpSignInForm.module.scss';
import HiddenIcon from '../../icons/Hidden';
import VisibileIcon from '../../icons/Visibility';
import { SIGN_UP, SIGN_IN } from '../../constants/Strings';

const Form = ({ formType }:{formType:string}) => {
  const [password, setPassword] = useState('');
  const [isVisible, setIsVisible] = useState(false);
  const passwordInputRef = useRef<null|HTMLInputElement>(null);
  const { inputContent } = styles;

  const handleVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };
  const handlePassword = (e:React.SyntheticEvent) => {
    setPassword((e.target as HTMLInputElement).value);
  };
  useEffect(() => {
    if (isVisible) {
      (passwordInputRef.current as HTMLInputElement).type = 'text';
    } else (passwordInputRef.current as HTMLInputElement).type = 'password';
  }, [isVisible]);

  return (
    <form className={inputContent}>
      {formType === 'signUp'
        ? (
          <div>
            <label htmlFor="fullName">
              Full Name
              <span style={{ color: 'red' }}>*</span>
            </label>
            <input id="fullName" type="text" name="fullName" placeholder="Fill in first name" />
          </div>
        ) : null}
      <div>
        <label htmlFor="email">
          Email
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input id="email" type="email" name="email" placeholder="ex: namesurname@gmail.com" />
      </div>
      <div>
        <label htmlFor="password">
          Password
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input
          id="password"
          type="password"
          ref={passwordInputRef}
          value={password}
          name="password"
          placeholder="Enter your password"
          onChange={handlePassword}
        />
        {isVisible
          ? <VisibileIcon makeInvisible={handleVisibility} />
          : <HiddenIcon makeVisible={handleVisibility} />}
      </div>
      <p>Forgot Password?</p>
      {formType === 'signUp' ? <button type="submit">{SIGN_UP}</button> : <button type="submit">{SIGN_IN}</button>}
    </form>
  );
};
export default Form;
