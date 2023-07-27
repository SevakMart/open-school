import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { closeModal } from '../../../redux/Slices/PortalOpenStatus';
import LinkedinIcon1 from '../../../icons/LinkedinIcon/Linkedin1';
import EmailIcon1 from '../../../icons/EmailIcon/Email1';
import styles from './Header.module.scss';

const Header = ({
  mainTitle, shouldRemoveIconContent, isForgotPasswordContent, isVerificationContent,
}:{mainTitle:string, shouldRemoveIconContent:boolean, isForgotPasswordContent:boolean, isVerificationContent:boolean}) => {
  const {
    headerContent, iconContent, closeIcon, icon,
  } = styles;
  const dispatch = useDispatch();
  const { t } = useTranslation();

  const handleClosePortal = () => {
    dispatch(closeModal());
  };

  return (
    <>
      <div className={closeIcon} onClick={handleClosePortal}>✖</div>
      <div className={headerContent}>
        <h2>{mainTitle}</h2>
        {isVerificationContent && <p>{t('messages.verificationPageHint')}</p>}
        {isForgotPasswordContent
          && (
          <>
            <p>{t('string.forgotPsd.enterEmail')}</p>
            <p>{t('string.forgotPsd.sendNotification')}</p>
          </>
          )}
        { !shouldRemoveIconContent
        && (
        <>
          <div className={iconContent}>
            <div className={icon}><LinkedinIcon1 href="" /></div>
            <div className={icon}><EmailIcon1 title="example@example.com" /></div>
          </div>
          <p>{t('string.signIn.or')}</p>
        </>
        )}
      </div>
    </>
  );
};
export default Header;
