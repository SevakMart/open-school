import { useTranslation } from 'react-i18next';
import styles from './Footer.module.scss';

const Footer = () => {
  const { t } = useTranslation();
  const {
    mainContainer, categories, becomeMentor, findUsOn,
  } = styles;
  return (
    <div className={mainContainer}>
      <button type="button">{t('Open-School')}</button>
      <div className={categories}>
        <h2>{t('Categories')}</h2>
        <p>{t('Software Engineering')}</p>
        <p>{t('Design')}</p>
        <p>{t('Management')}</p>
      </div>
      <div className={becomeMentor}>
        <h2>{t('Become a Mentort')}</h2>
        <p>{t('About')}</p>
        <p>{t('Help')}</p>
      </div>
      <div className={findUsOn}>
        <h2>{t('Find Us On')}</h2>
        <p>Facebook</p>
        <p>Instagram</p>
      </div>
    </div>
  );
};
export default Footer;
