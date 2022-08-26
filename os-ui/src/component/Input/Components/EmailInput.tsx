import React from 'react';
import { ErrorField } from '../../ErrorField/ErrorField';
import styles from '../Input-Styles.module.scss';

export const EmailInput = ({
  textName, labelText, placeholderText, errorMessage, value, handleInputChange,
}:{
textName:string, labelText:string, errorMessage:string, value:string, placeholderText:string,
handleInputChange:(event:React.SyntheticEvent)=>void
}) => {
  const { EmailInputContainer } = styles;
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
        required
      />
      {errorMessage && <ErrorField.InputErrorField className={['inputErrorField']}>{errorMessage}</ErrorField.InputErrorField>}
    </div>
  );
};
