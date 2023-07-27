import React, { useRef, useState } from 'react';
import './popupCodeToVerify.scss';
import '../ForgotPassword/forgotPassword.scss';
import { ErrorField } from '../../../ErrorField/ErrorField';

const PopupCodeToVerify = ({ errorMessage, formValues, setFormValues }: {errorMessage: string, formValues: any, setFormValues: (formValues: any) => void}) => {
  const [codeDigits, setCodeDigits] = useState<string[]>(['', '', '', '']);
  const codeInputRefs = useRef<Array<HTMLInputElement | null>>([]);

  const handleCodeChange = (index: number, value: string) => {
    const numericValue = value.replace(/\D/g, '');
    const newCodeDigits = [...codeDigits];
    newCodeDigits[index] = numericValue;
    setCodeDigits(newCodeDigits);
    setFormValues({ ...formValues, token: newCodeDigits.join('') });

    if (numericValue.length === 1 && index < newCodeDigits.length - 1) {
      const nextIndex = index + 1;
      const nextInputRef = codeInputRefs.current[nextIndex];
      if (nextInputRef) {
        nextInputRef.focus();
      }
    }
  };

  const handleCodeKeyDown = (index: number, event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Backspace' && index > 0 && codeDigits[index] === '') {
      const newCodeDigits = [...codeDigits];
      newCodeDigits[index - 1] = '';
      setCodeDigits(newCodeDigits);
      setFormValues({ ...formValues, token: newCodeDigits.join('') });

      const prevInputRef = codeInputRefs.current[index - 1];
      if (prevInputRef) {
        prevInputRef.focus();
      }
    } else if (event.key === 'ArrowLeft' && index > 0) {
      const prevInputRef = codeInputRefs.current[index - 1];
      if (prevInputRef) {
        prevInputRef.focus();
        setTimeout(() => {
          prevInputRef.setSelectionRange(1, 1); // set the cursor in the start position
        });
      }
    } else if (event.key === 'ArrowRight' && index < codeDigits.length - 1) {
      const nextInputRef = codeInputRefs.current[index + 1];
      if (nextInputRef) {
        nextInputRef.focus();
        setTimeout(() => {
          nextInputRef.setSelectionRange(0, 0); // set the cursor in the start position
        });
      }
    }
  };

  const handleCodePaste = (event: React.ClipboardEvent<HTMLInputElement>) => {
    event.preventDefault();
    const pasteData = event.clipboardData.getData('text');
    const pasteDigits = pasteData.replace(/\D/g, '').padEnd(4, '').split('').slice(0, 4);
    const NthInputRef = codeInputRefs.current[pasteDigits.length - 1];
    const newCodeDigits = [...codeDigits];
    newCodeDigits.splice(0, pasteDigits.length, ...pasteDigits);
    setCodeDigits(newCodeDigits);
    setFormValues({ ...formValues, token: newCodeDigits.join('') });

    if (NthInputRef) {
      NthInputRef.focus();
      NthInputRef.setSelectionRange(0, 0);
    }
  };

  const handleRefCreated = (ref: HTMLInputElement | null, index: number) => {
    codeInputRefs.current[index] = ref;
  };

  return (
    <div className="container">
      <div style={{ marginLeft: '50px' }}>
        {errorMessage !== '' && <ErrorField.InputErrorField className={['inputErrorField']}>{errorMessage}</ErrorField.InputErrorField>}
      </div>
      <form autoComplete="off">
        <div className="codeBox">
          {codeDigits.map((digit, index) => (
            <input
              key={index}
              type="text"
              inputMode="numeric"
              className="ceil"
              autoComplete="new-password"
              maxLength={1}
              value={digit}
              ref={(ref) => handleRefCreated(ref, index)}
              onChange={(e) => handleCodeChange(index, e.target.value)}
              onKeyDown={(e) => handleCodeKeyDown(index, e)}
              onPaste={handleCodePaste}
            />
          ))}
        </div>
      </form>
    </div>
  );
};

export default PopupCodeToVerify;
