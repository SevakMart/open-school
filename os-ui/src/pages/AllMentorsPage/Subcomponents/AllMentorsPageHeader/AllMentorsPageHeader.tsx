import { useTranslation } from 'react-i18next';
import { ALL_MENTORS, SAVED_MENTORS } from '../../../../constants/Strings';
import styles from './AllMentorsPageHeader.module.scss';

const AllMentorsPageHeader = ({ activeNavigator, changeHeaderFocus }:
  {activeNavigator:string, changeHeaderFocus:(headerNav:string)=>void}) => {
  const { mentorHeader, activeNav, nonActiveNav } = styles;
  const { t } = useTranslation();

  return (
    <div className={mentorHeader}>
      <nav>
        {/* eslint-disable-next-line max-len */}
        <p className={activeNavigator === ALL_MENTORS ? activeNav : nonActiveNav} onClick={() => changeHeaderFocus(ALL_MENTORS)}>
          {t('All Mentors')}
        </p>
        {/* eslint-disable-next-line max-len */}
        <p className={activeNavigator === SAVED_MENTORS ? activeNav : nonActiveNav} onClick={() => changeHeaderFocus(SAVED_MENTORS)}>
          {t('Saved Mentors')}
        </p>
      </nav>
    </div>
  );
};
export default AllMentorsPageHeader;
