import React, { useRef } from 'react';
import './popupCodeToVerify.scss';
import '../ForgotPassword/forgotPassword.scss';
import { useDispatch, useSelector } from 'react-redux';
import CloseIcon from '../../../../icons/Close';
import { closeModal, openModal } from '../../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../../types/types';
import { RootState } from '../../../../redux/Store';
import { setCodeDigits } from '../../../../redux/Slices/CodeVerificationPswSlice';

const PopupCodeToVerify: React.FC = () => {
  const dispatch = useDispatch();
  const AllcodeDigitsState = useSelector<RootState>((state) => state.CodeVerificationPsw) as {
    codeDigits: string[],
  };

  const { codeDigits } = AllcodeDigitsState;
  const codeInputRefs = useRef<Array<HTMLInputElement | null>>([]);
  const disabled = !codeDigits[0] || !codeDigits[1] || !codeDigits[2] || !codeDigits[3];

  const handleClosePortal = () => {
    dispatch(closeModal());
    dispatch(setCodeDigits(['', '', '', '']));
  };

  const handleCodeChange = (index: number, value: string) => {
    const numericValue = value.replace(/\D/g, '');
    const newCodeDigits = [...codeDigits];
    newCodeDigits[index] = numericValue;
    dispatch(setCodeDigits(newCodeDigits));

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
      dispatch(setCodeDigits(newCodeDigits));

      const prevInputRef = codeInputRefs.current[index - 1];
      if (prevInputRef) {
        prevInputRef.focus();
      }
    } else if (event.key === 'ArrowLeft' && index > 0) {
      const prevInputRef = codeInputRefs.current[index - 1];
      if (prevInputRef) {
        prevInputRef.focus();
        setTimeout(() => {
          prevInputRef.setSelectionRange(1, 1); // Установка позиции курсора в начало
        });
      }
    } else if (event.key === 'ArrowRight' && index < codeDigits.length - 1) {
      const nextInputRef = codeInputRefs.current[index + 1];
      if (nextInputRef) {
        nextInputRef.focus();
        setTimeout(() => {
          nextInputRef.setSelectionRange(0, 0); // Установка позиции курсора в начало
        });
      }
    }
  };

  const handleCodePaste = (event: React.ClipboardEvent<HTMLInputElement>) => {
    event.preventDefault();
    const pasteData = event.clipboardData.getData('text');
    const pasteDigits = pasteData.replace(/\D/g, '').padEnd(4, '').split('').slice(0, 4);
    const NthInputRef = codeInputRefs.current[pasteDigits.length - 1]; // Исправлено здесь
    const newCodeDigits = [...codeDigits];
    newCodeDigits.splice(0, pasteDigits.length, ...pasteDigits);
    dispatch(setCodeDigits(newCodeDigits));
    if (NthInputRef) {
      NthInputRef.focus();
      NthInputRef.setSelectionRange(0, 0);
    }
  };

  const toSomePopup = (to: string) => {
    dispatch(openModal({ buttonType: to }));
  };

  const handleRefCreated = (ref: HTMLInputElement | null, index: number) => {
    codeInputRefs.current[index] = ref;
  };

  const email = useSelector<RootState>((state) => state.forgotPasswordEmail);

  return (
    <div className="container">
      <div className="closeIcon" onClick={handleClosePortal}>
        <CloseIcon />
      </div>
      <div className="verPopupBody">
        <div className="verPopUpTitle">Enter security code</div>
        <p className="verPopUpcontent">
          Please check your email for a message with your code. Your code is 4 numbers long.
        </p>
        <div className="sentMail">
          <div>We sent your code to:</div>
          <div>{email}</div>
        </div>
      </div>
      <div className="codeBox">
        {codeDigits.map((digit, index) => (
          <input
            key={index}
            type="text"
            inputMode="numeric"
            className="ceil"
            maxLength={1}
            value={digit}
            ref={(ref) => handleRefCreated(ref, index)}
            onChange={(e) => handleCodeChange(index, e.target.value)}
            onKeyDown={(e) => handleCodeKeyDown(index, e)}
            onPaste={handleCodePaste}
          />
        ))}
      </div>
      <div className="popUpBtns">
        <button type="button" className="anyBtns backBtn" onClick={() => { toSomePopup(Types.Button.FORGOT_PASSWORD); dispatch(setCodeDigits(['', '', '', ''])); }}>
          Go Back
        </button>
        <button type="button" className="anyBtns continueBtn" disabled={disabled} onClick={() => toSomePopup(Types.Button.RESET_PASSWORD)}>
          Continue
        </button>
      </div>
    </div>
  );
};

export default PopupCodeToVerify;
