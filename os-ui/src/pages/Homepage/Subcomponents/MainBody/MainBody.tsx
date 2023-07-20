import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import styles from './MainBody.module.scss';

const MainBody = ({
  children, isMentor,
}:
     /* eslint-disable-next-line max-len */
    {children:any, isMentor:boolean}) => {
  const {
    listContainer, registrationButton, regButton, btnBelow, btnAbove,
  } = styles;
  const { t } = useTranslation();

  const btnX = isMentor ? btnAbove : btnBelow;

  return (
    <>
      <div className={listContainer} style={isMentor ? { margin: '3%' } : { backgroundColor: '#FFFFFF', margin: '0 3%' }}>
        {children}

      </div>
      <div className={registrationButton}>
        <Link to="/categories/subcategories">
          <button type="button" className={`${regButton} ${btnX}`}>
            {isMentor ? t('button.homePage.registerMentor') : t('button.homePage.seeAll')}
          </button>
        </Link>
      </div>
    </>
  );
};
export default MainBody;
