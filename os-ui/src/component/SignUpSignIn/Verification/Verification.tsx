import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../redux/Store';
import { openModalWithSuccessMessage } from '../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../types/types';
import Button from '../../Button/Button';
import Header from '../Header/Header';
import authService from '../../../services/authService';

/* eslint-disable max-len */
const Verification = () => {
  const signedUpUserId = useSelector<RootState>((state) => state.signedUpUserId);
  const { t } = useTranslation();
  const dispatch = useDispatch();

  const handleResend = () => {
    authService.resendVerificationEmail(signedUpUserId as number)
      .then((response) => {
        dispatch(openModalWithSuccessMessage({ buttonType: Types.Button.SUCCESS_MESSAGE, withSuccessMessage: response.message, isResendVerificationEmailMessage: true }));
      });
  };

  return (
    <>
      <Header
        mainTitle={t('string.onAccountVerification.title')}
        shouldRemoveIconContent
        isForgotPasswordContent={false}
        isVerificationContent
      />
      <div style={{ margin: '0 80px' }}>
        <Button.FormButton className={['formButton']} onClick={handleResend}>
          {t('button.resend')}
        </Button.FormButton>
      </div>
    </>
  );
};
export default Verification;
