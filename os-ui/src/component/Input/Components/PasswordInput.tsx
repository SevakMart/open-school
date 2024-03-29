import React, { useState, useEffect, useRef } from 'react';
import { ErrorField } from '../../ErrorField/ErrorField';
import VisibileIcon from '../../../icons/Visibility';
import HiddenIcon from '../../../icons/Hidden';
import { useFocus } from '../../../custom-hooks/useFocus';
import styles from '../Input-Styles.module.scss';
import { PasswordInputProps } from '../../../types/InputTypes';

export const PasswordInput = ({
  textName,
  labelText,
  placeholderText,
  errorMessage,
  value,
  handleInputChange,
  handleEnterPress,
}: PasswordInputProps) => {
  const [isVisible, setIsVisible] = useState(false);
  const passwordInputRef = useRef<HTMLInputElement | null>(null);
  const passwordInputContainerRef = useRef<HTMLDivElement | null>(null);
  const [handleOnFocus, handleOnBlur] = useFocus(
    '#d9dbe9',
    passwordInputContainerRef.current,
  );
  const { PasswordInputContainer, PasswordInputFieldWithIcon } = styles;

  const handlePasswordVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };

  useEffect(() => {
    if (isVisible) {
      (passwordInputRef.current as HTMLInputElement).type = 'text';
    } else (passwordInputRef.current as HTMLInputElement).type = 'password';
  }, [isVisible]);

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleEnterPress(event);
    }
  };

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
          onFocus={() => handleOnFocus()}
          onBlur={() => handleOnBlur()}
          onKeyDown={handleKeyDown}
          required
          tabIndex={0}
        />
        {isVisible ? (
          <VisibileIcon makeInvisible={handlePasswordVisibility} />
        ) : (
          <HiddenIcon makeVisible={handlePasswordVisibility} />
        )}
      </div>
      {errorMessage !== '' && (
        <ErrorField.InputErrorField className={['inputErrorField']}>
          {errorMessage}
        </ErrorField.InputErrorField>
      )}
    </div>
  );
};
