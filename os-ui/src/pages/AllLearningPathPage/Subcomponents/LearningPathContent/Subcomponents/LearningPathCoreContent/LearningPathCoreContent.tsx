import { useState, useEffect, useContext } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../../../../../redux/Store';
import { userContext } from '../../../../../../contexts/Contexts';
import LearningPath from '../../../../../../component/LearningPath/LearningPath';
import courseService from '../../../../../../services/courseService';
import userService from '../../../../../../services/userService';
import { SuggestedCourseType } from '../../../../../../types/SuggestedCourseType';
import styles from './LearningPathCoreContent.module.scss';

type CourseListType=SuggestedCourseType & {id:number, isBookMarked?:boolean}

const LearningPathCoreContent = () => {
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const { token, id } = useContext(userContext);
  const [courseList, setCourseList] = useState<CourseListType[]>([]);
  const { t } = useTranslation();
  const { mainCoreContainer, courseContainer } = styles;

  useEffect(() => {
    const getSearchedCoursesPromise = Object.values(sendingParams as object)[0].length
    /* eslint-disable-next-line max-len */
      ? courseService.getSearchedCourses({ page: 0, size: 100, ...(sendingParams as object) }, token)
      : courseService.getSearchedCourses({ page: 0, size: 100 }, token);
    Promise.all([
      userService.getUserSavedCourses(id, token, { page: 0, size: 100 }),
      getSearchedCoursesPromise,
    ]).then((combinedData) => {
      const userSavedCourseContent = combinedData[0].content;
      const searchedCourseContent = combinedData[1].content;
      const list = [];
      if (userSavedCourseContent?.length) {
        for (const searchedCourse of searchedCourseContent) {
          const index = userSavedCourseContent
            .findIndex((savedCourse:CourseListType) => savedCourse.id === searchedCourse.id);
          if (index !== -1) {
            userSavedCourseContent[index].isBookMarked = true;
            list.push(userSavedCourseContent[index]);
          } else {
            searchedCourse.isBookMarked = false; list.push(searchedCourse);
          }
        }
        setCourseList([...list]);
      } else setCourseList(searchedCourseContent);
    });
  }, [sendingParams]);

  return (
    <div className={mainCoreContainer}>
      {courseList.length ? courseList.map((course) => (
        <div className={courseContainer} key={course.title}>
          <Link to={`/userCourse/${course.id}`}>
            <LearningPath
              courseInfo={course}
              saveCourse={
              (courseId:number) => userService.saveUserPreferredCourses(id, courseId, token)
            }
              deleteCourse={
              (courseId:number) => userService.deleteUserSavedCourses(id, courseId, token)
            }
            />
          </Link>
        </div>
      )) : <h2 data-testid="Error Message">{t('messages.noData.default')}</h2>}
    </div>
  );
};
export default LearningPathCoreContent;
