import { useTranslation } from 'react-i18next';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/Store';
import Button from '../../Button/Button';
import Header from '../Header/Header';
import authService from '../../../services/authService';

const Verification = () => {
  /* the userInfo below is temporary until I create local storage helpers */
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const { t } = useTranslation();

  const handleResend = () => {
    authService.resendVerificationEmail((userInfo as any).userId);
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
