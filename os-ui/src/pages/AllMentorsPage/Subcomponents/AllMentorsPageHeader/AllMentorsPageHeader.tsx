import { useEffect, useState } from 'react';
import { ALL_MENTORS, SAVED_MENTORS } from '../../../../constants/Strings';
import styles from './AllMentorsPageHeader.module.scss';

enum MentorsNav {
    ALL_MENTORS_NAV='All Mentors Nav',
    SAVED_MENTORS_NAV='Saved Mentors Nav'
}

const AllMentorsPageHeader = ({ activeNavigator, changeHeaderFocus }:
  {activeNavigator:string, changeHeaderFocus:(headerNav:string)=>void}) => {
  const { mentorHeader, activeNav, nonActiveNav } = styles;

  return (
    <div className={mentorHeader}>
      <nav>
        {/* eslint-disable-next-line max-len */}
        <p className={activeNavigator === ALL_MENTORS ? activeNav : nonActiveNav} onClick={() => changeHeaderFocus(ALL_MENTORS)}>
          {ALL_MENTORS}
        </p>
        {/* eslint-disable-next-line max-len */}
        <p className={activeNavigator === SAVED_MENTORS ? activeNav : nonActiveNav} onClick={() => changeHeaderFocus(SAVED_MENTORS)}>
          {SAVED_MENTORS}
        </p>
      </nav>
    </div>
  );
};
export default AllMentorsPageHeader;
