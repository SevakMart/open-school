import React from 'react';
import { ErrorField } from '../../ErrorField/ErrorField';
import styles from '../Input-Styles.module.scss';
import { EmailInputProps } from '../../../types/InputTypes';

export const EmailInput = ({
  textName,
  labelText,
  placeholderText,
  errorMessage,
  value,
  handleInputChange,
  handleEnterPress,
}: EmailInputProps) => {
  const { EmailInputContainer } = styles;

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleEnterPress(event);
    }
  };

  return (
    <div className={EmailInputContainer}>
      <label htmlFor={textName}>
        {labelText}
        <span style={{ color: 'red' }}>*</span>
      </label>
      <input
        id={textName}
        type="email"
        value={value}
        name={textName}
        placeholder={placeholderText}
        data-testid="emailInput"
        onChange={(e) => handleInputChange(e)}
        onKeyDown={handleKeyDown}
        required
        tabIndex={-1}
      />
      {errorMessage !== '' && (
        <ErrorField.InputErrorField className={['inputErrorField']}>
          {errorMessage}
        </ErrorField.InputErrorField>
      )}
    </div>
  );
};
