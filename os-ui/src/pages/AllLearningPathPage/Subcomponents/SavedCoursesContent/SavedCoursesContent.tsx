import React, { useEffect, useState, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation } from 'react-router-dom';
import userService from '../../../../services/userService';
import { SuggestedCourseType } from '../../../../types/SuggestedCourseType';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import { userContext } from '../../../../contexts/Contexts';
import styles from './SavedCoursesContent.module.scss';

type CourseListType=SuggestedCourseType & {id:number, isBookMarked?:boolean}

const SavedCoursesContent = () => {
  const { token, id } = useContext(userContext);
  const [savedCourseList, setSavedCourseList] = useState<CourseListType[]>([]);
  const { t } = useTranslation();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const { mainContainer } = styles;
  const NoDisplayedDataMessage = <h2 data-testid="Error Message">{t('messages.noData.default')}</h2>;

  useEffect(() => {
    userService.getUserSavedCourses(id, token, { page: 0, size: 100 })
      .then((data) => setSavedCourseList([...data.content]));
  }, [params]);

  return (
    <div className={mainContainer}>
      {savedCourseList.length > 0 ? savedCourseList.map((course) => (
        <React.Fragment key={course.title}>
          <LearningPath
            courseInfo={course}
          />
        </React.Fragment>
      )) : NoDisplayedDataMessage}
    </div>

  );
};
export default SavedCoursesContent;
