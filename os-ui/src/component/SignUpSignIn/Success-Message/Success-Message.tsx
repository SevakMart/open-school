import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { openModal } from '../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../types/types';
import styles from './Success-Message.module.scss';

/* eslint-disable max-len */

const SuccessMessage = ({
  message, isSignUpSuccessfulRegistration, isResetPasswordSuccessfulMessage, isResendVerificationEmailMessage,
}:
  {message:string, isSignUpSuccessfulRegistration:boolean, isResetPasswordSuccessfulMessage:boolean, isResendVerificationEmailMessage:boolean}) => {
  const { mainContainer } = styles;
  const dispatch = useDispatch();
  const { t } = useTranslation();

  useEffect(() => {
    let timer:any;
    if (isSignUpSuccessfulRegistration || isResendVerificationEmailMessage) {
      timer = setTimeout(() => dispatch(openModal(Types.Button.VERIFY)), 3000);
    } else if (isResetPasswordSuccessfulMessage) {
      timer = setTimeout(() => dispatch(openModal(Types.Button.SIGN_IN)), 3000);
    } else {
      timer = setTimeout(() => dispatch(openModal(Types.Button.RESET_PASSWORD)), 3000);
    }
    return () => clearTimeout(timer);
  }, []);

  return (
    <div className={mainContainer}>
      <h1>{t('messages.successHeader')}</h1>
      <h2>{message}</h2>
    </div>
  );
};
export default SuccessMessage;
