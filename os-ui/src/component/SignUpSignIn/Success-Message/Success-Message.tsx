import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { closeModal, openModal } from '../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../types/types';
import styles from './Success-Message.module.scss';

const SuccessMessage = ({
  message,
  isSignUpSuccessfulRegistration,
  isResetPasswordSuccessfulMessage,
  isResendVerificationEmailMessage,
  isForgotVerificationMessage,
  forgotVerficationEmail,
}: {
	message: string;
	isSignUpSuccessfulRegistration: boolean;
	isResetPasswordSuccessfulMessage: boolean;
	isResendVerificationEmailMessage: boolean;
	isForgotVerificationMessage: boolean;
	forgotVerficationEmail?: string
  }) => {
  const {
    mainContainer, closeIcon, title, subtitle,
  } = styles;
  const dispatch = useDispatch();
  const { t } = useTranslation();

  const handleClosePortal = () => {
	  dispatch(closeModal());
  };

  useEffect(() => {
	  let timer: any;
	  if (isSignUpSuccessfulRegistration || isResendVerificationEmailMessage) {
      timer = setTimeout(
		  () => dispatch(openModal({ buttonType: Types.Button.VERIFY, forgotVerficationEmail })),
		  3000,
      );
	  } else if (isForgotVerificationMessage) {
      timer = setTimeout(
		  () => dispatch(openModal({ buttonType: Types.Button.FORGOT_VERIFICATION })),
		  3000,
      );
	  } else if (isResetPasswordSuccessfulMessage) {
      timer = setTimeout(
		  () => dispatch(openModal({ buttonType: Types.Button.RESET_PASSWORD })),
		  3000,
      );
	  }
	  return () => clearTimeout(timer);
  }, [
	  isSignUpSuccessfulRegistration,
	  isResendVerificationEmailMessage,
	  isResetPasswordSuccessfulMessage,
	  isForgotVerificationMessage,
  ]);

  return (
    <>
      <div className={closeIcon} onClick={handleClosePortal}>
        ✖
      </div>
      <div className={mainContainer}>
        <h1 className={title}>{t('messages.successHeader')}</h1>
        <h2 className={subtitle}>{message}</h2>
      </div>
    </>
  );
};
export default SuccessMessage;
