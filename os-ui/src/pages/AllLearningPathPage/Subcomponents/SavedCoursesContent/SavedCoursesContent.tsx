import React, { useEffect, useState, useContext } from 'react';
import userService from '../../../../services/userService';
import { SuggestedCourseType } from '../../../../types/SuggestedCourseType';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import { EMPTY_DATA_ERROR_MESSAGE } from '../../../../constants/Strings';
import { userContext } from '../../../../contexts/Contexts';
import styles from './SavedCoursesContent.module.scss';

type CourseListType=SuggestedCourseType & {id:number, bookmarked?:boolean}

const SavedCoursesContent = () => {
  const { token, id } = useContext(userContext);
  const [savedCourseList, setSavedCourseList] = useState<CourseListType[]>([]);
  const { mainContainer, coreContent } = styles;

  const handleCourseDeletion = (courseId:number) => {
    userService.deleteUserSavedCourses(id, courseId, token);
    const index = savedCourseList.findIndex((course) => course.id === courseId);
    const savedCourses = savedCourseList;
    savedCourses.splice(index, 1);
    const bookmarkedCourses = savedCourses.map(
      (course:CourseListType) => ({ ...course, bookmarked: true }),
    );
    setSavedCourseList([...bookmarkedCourses]);
  };

  useEffect(() => {
    userService.getUserSavedCourses(6, token, { page: 0, size: 100 })
      .then((data) => setSavedCourseList([...data.content.map((
        course:CourseListType,
      ) => ({ ...course, bookmarked: true }))]));
  }, []);

  return (
    <>
      <div className={coreContent}>
        <div className={mainContainer}>
          {savedCourseList.length ? savedCourseList.map((course) => (
            <React.Fragment key={course.title}>

              <LearningPath
                courseInfo={course}
                saveCourse={(courseId:number) => {
                  userService.saveUserPreferredCourses(id, courseId, token);
                }}
                deleteCourse={handleCourseDeletion}
              />

            </React.Fragment>
          )) : <h2 data-testid="Empty data Message">{EMPTY_DATA_ERROR_MESSAGE}</h2>}
        </div>
      </div>
    </>
  );
};
export default SavedCoursesContent;
