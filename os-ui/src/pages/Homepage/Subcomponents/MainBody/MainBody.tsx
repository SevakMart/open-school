import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import Button from '../../../../component/Button/Button';
import LeftArrowIcon from '../../../../icons/LeftArrow';
import RightArrowIcon from '../../../../icons/RightArrow';
import styles from './MainBody.module.scss';

const MainBody = ({
  page, maxPage, children, clickPrevious, clickNext, isMentor,
}:
     /* eslint-disable-next-line max-len */
    {page:number, maxPage:number, children:any, clickPrevious:()=>void, clickNext:()=>void, isMentor:boolean}) => {
  const {
    listContainer, icon, icon__left, icon__right, registrationButton,
  } = styles;
  const { t } = useTranslation();

  return (
    <>
      <div className={listContainer} style={isMentor ? { margin: '6%' } : { backgroundColor: '#DFF3FF', margin: '0 6%' }}>
        {page > 0 && (
        <div className={`${icon} ${icon__left}`}>
          <LeftArrowIcon testId="categoryLeftArrow" handleArrowClick={() => { clickPrevious(); }} />
        </div>
        )}
        {children}
        {page < maxPage
        && (
        <div className={`${icon} ${icon__right}`}>
          <RightArrowIcon testId="categoryRightArrow" handleArrowClick={() => { clickNext(); }} />
        </div>
        )}
      </div>
      <div className={registrationButton} style={isMentor ? { margin: 'auto', padding: '5% 0 10%' } : { margin: 'auto', padding: '5% 0 4%' }}>
        <Link to="/categories/subcategories">
          <Button.MainButton className={['mainMentorRegistrationButton']}>
            {isMentor ? t('button.homePage.registerMentor') : t('button.homePage.seeAll')}
          </Button.MainButton>
        </Link>
      </div>
    </>
  );
};
export default MainBody;
