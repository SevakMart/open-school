import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { closeModal } from '../../../redux/Slices/PortalOpenStatus';
import CloseIcon from '../../../icons/Close';
import LinkedinIcon1 from '../../../icons/Linkedin1';
import EmailIcon1 from '../../../icons/Email1';
import styles from './Header.module.scss';
/* eslint-disable max-len */
const Header = ({ mainTitle, shouldRemoveIconContent }:{mainTitle:string, shouldRemoveIconContent:boolean}) => {
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
      <div className={closeIcon} onClick={handleClosePortal}><CloseIcon /></div>
      <div className={headerContent}>
        <h2>{mainTitle}</h2>
        { !shouldRemoveIconContent
        && (
        <>
          <div className={iconContent}>
            <div className={icon}><LinkedinIcon1 /></div>
            <div className={icon}><EmailIcon1 /></div>
          </div>
          <p>{t('string.signIn.or')}</p>
        </>
        )}
      </div>
    </>
  );
};
export default Header;
