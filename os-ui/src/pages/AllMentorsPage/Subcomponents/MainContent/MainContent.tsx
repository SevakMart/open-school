import { useState } from 'react';
import AllMentorsPageHeader, { Mentors } from '../AllMentorsPageHeader/AllMentorsPageHeader';
import Content from '../Content/Content';
import SavedMentors from '../SavedMentors/SavedMentors';
import styles from './MainContent.module.scss';

/* eslint-disable max-len */

const MainContent = () => {
  const [focusedHeader, setFocusedHeader] = useState(Mentors.ALL_MENTORS);
  const { mainContentContainer } = styles;
  return (
    <div className={mainContentContainer}>
      <AllMentorsPageHeader changeHeaderFocus={(headerTitle) => setFocusedHeader(headerTitle)} />
      {focusedHeader === Mentors.ALL_MENTORS ? <Content /> : <SavedMentors />}
    </div>
  );
};
export default MainContent;
