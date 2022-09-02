import React, { useState, useEffect, useRef } from 'react';
import { ErrorField } from '../../ErrorField/ErrorField';
import VisibileIcon from '../../../icons/Visibility';
import HiddenIcon from '../../../icons/Hidden';
import styles from '../Input-Styles.module.scss';

export const PasswordInput = ({
  textName, labelText, placeholderText, errorMessage, value, handleInputChange,
}:{
textName:string, labelText:string, errorMessage:string, value:string, placeholderText:string,
handleInputChange:(event:React.SyntheticEvent)=>void
}) => {
  const [isVisible, setIsVisible] = useState(false);
  const [isFocused, setIsFocused] = useState(false);
  const passwordInputRef = useRef<null|HTMLInputElement>(null);
  const passwordInputContainerRef = useRef<null|HTMLDivElement>(null);
  const { PasswordInputContainer, PasswordInputFieldWithIcon } = styles;

  const handlePasswordVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };
  useEffect(() => {
    if (isFocused) {
      (passwordInputContainerRef.current as HTMLDivElement).style.borderColor = 'black';
    } else {
      (passwordInputContainerRef.current as HTMLDivElement).style.borderColor = '#d9dbe9';
    }
  }, [isFocused]);
  useEffect(() => {
    if (isVisible) {
      (passwordInputRef.current as HTMLInputElement).type = 'text';
    } else (passwordInputRef.current as HTMLInputElement).type = 'password';
  }, [isVisible]);

  return (
    <div className={PasswordInputContainer}>
      <label htmlFor={textName}>
        {labelText}
        <span style={{ color: 'red' }}>*</span>
      </label>
      <div className={PasswordInputFieldWithIcon} ref={passwordInputContainerRef}>
        <input
          id={textName}
          type="password"
          ref={passwordInputRef}
          value={value}
          name={textName}
          placeholder={placeholderText}
          data-testid="psdInput"
          onChange={handleInputChange}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          required
        />
        {isVisible
          ? <VisibileIcon makeInvisible={handlePasswordVisibility} />
          : <HiddenIcon makeVisible={handlePasswordVisibility} />}
      </div>
      {errorMessage !== '' && <ErrorField.InputErrorField className={['inputErrorField']}>{errorMessage}</ErrorField.InputErrorField>}
    </div>
  );
};
