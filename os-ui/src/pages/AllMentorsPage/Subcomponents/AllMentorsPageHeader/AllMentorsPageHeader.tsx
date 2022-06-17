import { useEffect, useState } from 'react';
import { ALL_MENTORS, SAVED_MENTORS } from '../../../../constants/Strings';
import styles from './AllMentorsPageHeader.module.scss';

enum MentorsNav {
    ALL_MENTORS_NAV='All Mentors Nav',
    SAVED_MENTORS_NAV='Saved Mentors Nav'
}

const AllMentorsPageHeader = ({ activeNavigator }:{activeNavigator:string}) => {
  const { activeNav, nonActiveNav } = styles;

  return (
    <div>
      <nav>
        <p className={activeNavigator === MentorsNav.ALL_MENTORS_NAV ? activeNav : nonActiveNav}>
          {ALL_MENTORS}
        </p>
        <p className={activeNavigator === MentorsNav.SAVED_MENTORS_NAV ? activeNav : nonActiveNav}>
          {SAVED_MENTORS}
        </p>
      </nav>
    </div>
  );
};
export default AllMentorsPageHeader;
