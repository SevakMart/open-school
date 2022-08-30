import React from 'react';
import { ErrorField } from '../../ErrorField/ErrorField';
import styles from '../Input-Styles.module.scss';

export const TextInput = ({
  textName, labelText, placeholderText, errorMessage, value, handleInputChange,
}:{
textName:string, labelText:string, errorMessage:string, value:string, placeholderText:string,
handleInputChange:(event:React.SyntheticEvent)=>void
}) => {
  const { textInputContainer } = styles;
  return (
    <div className={textInputContainer}>
      <label htmlFor={textName}>
        {labelText}
        <span style={{ color: 'red' }}>*</span>
      </label>
      <input
        id={textName}
        type="text"
        value={value}
        name={textName}
        placeholder={placeholderText}
        data-testid="lastNameInput"
        onChange={(e) => handleInputChange(e)}
        required
      />
      {errorMessage !== '' && <ErrorField.InputErrorField className={['inputErrorField']}>{errorMessage}</ErrorField.InputErrorField>}
    </div>
  );
};
