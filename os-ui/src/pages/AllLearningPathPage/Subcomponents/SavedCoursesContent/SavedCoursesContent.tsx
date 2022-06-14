import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import userService from '../../../../services/userService';
import { SuggestedCourseType } from '../../../../types/SuggestedCourseType';
import { CourseContent } from '../../../../types/CourseContent';
import NavbarOnSignIn from '../../../../component/NavbarOnSignIn/NavbarOnSignIn';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import LearningPathHeader from '../LearningPathContent/Subcomponents/LearningPathHeader/LearningPathHeader';
import { EMPTY_DATA_ERROR_MESSAGE } from '../../../../constants/Strings';
import styles from './SavedCoursesContent.module.scss';

type CourseListType=SuggestedCourseType & {id:number, bookmarked?:boolean}

const SavedCoursesContent = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const { token, id } = userInfo as any;
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
    userService.getUserSavedCourses(id, token, { page: 0, size: 100 })
      .then((data) => setSavedCourseList([...data.content.map((
        course:CourseListType,
      ) => ({ ...course, bookmarked: true }))]));
  }, []);

  return (
    <>
      <NavbarOnSignIn />
      <div className={coreContent}>
        <LearningPathHeader activeNavigator={CourseContent.SAVEDCOURSES} />
        <div className={mainContainer}>
          {savedCourseList.length ? savedCourseList.map((course) => (
            <React.Fragment key={course.title}>

              <LearningPath
                title={course.title}
                rating={course.rating}
                difficulty={course.difficulty}
                keywords={course.keywords}
                isBookMarked={course.bookmarked}
                courseId={course.id}
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
