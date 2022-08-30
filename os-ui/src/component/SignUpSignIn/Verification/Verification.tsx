import { useTranslation } from 'react-i18next';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/Store';
import Button from '../../Button/Button';
import Header from '../Header/Header';
import authService from '../../../services/authService';

const Verification = () => {
  const signedUpUserId = useSelector<RootState>((state) => state.signedUpUserId);
  const { t } = useTranslation();

  const handleResend = () => {
    authService.resendVerificationEmail(signedUpUserId as number);
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
