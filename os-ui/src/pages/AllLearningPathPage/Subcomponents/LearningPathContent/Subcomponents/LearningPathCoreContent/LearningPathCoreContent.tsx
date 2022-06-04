import { useState, useEffect, useContext } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../../../../../redux/Store';
import { userContext, courseBookmarkContext } from '../../../../../../contexts/Contexts';
import LearningPath from '../../../../../../component/LearningPath/LearningPath';
import courseService from '../../../../../../services/courseService';
import userService from '../../../../../../services/userService';
import { SuggestedCourseType } from '../../../../../../types/SuggestedCourseType';
import { EMPTY_DATA_ERROR_MESSAGE } from '../../../../../../constants/Strings';
import styles from './LearningPathCoreContent.module.scss';

type CourseListType=SuggestedCourseType & {id:number, bookmarked?:boolean}

const LearningPathCoreContent = () => {
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const { token, id } = useContext(userContext);
  const [courseList, setCourseList] = useState<CourseListType[]>([]);
  const { mainCoreContainer, courseContainer } = styles;

  useEffect(() => {
    Promise.all([
      userService.getUserSavedCourses(id, token, { page: 0, size: 100 }),
      courseService.getSearchedCourses({ page: 0, size: 100, ...(sendingParams as object) }, token),
    ]).then((combinedData) => {
      const userSavedCourseContent = combinedData[0].content;
      const searchedCourseContent = combinedData[1].content;
      const list = [];
      if (userSavedCourseContent.length) {
        for (const searchedCourse of searchedCourseContent) {
          const index = userSavedCourseContent
            .findIndex((savedCourse:CourseListType) => savedCourse.id === searchedCourse.id);
          if (index !== -1) {
            userSavedCourseContent[index].bookmarked = true;
            list.push(userSavedCourseContent[index]);
          } else {
            searchedCourse.bookmarked = false; list.push(searchedCourse);
          }
        }
        setCourseList([...list]);
      } else setCourseList([...searchedCourseContent]);
    });
  }, [sendingParams]);

  return (
    <div className={mainCoreContainer}>
      {courseList.length ? courseList.map((course, index) => (
        <div className={courseContainer} key={index}>
          <courseBookmarkContext.Provider value={course.id}>
            <LearningPath
              title={course.title}
              rating={course.rating}
              difficulty={course.difficulty}
              keywords={course.keywords}
              isBookMarked={course.bookmarked}
            />
          </courseBookmarkContext.Provider>
        </div>
      )) : <h2>{EMPTY_DATA_ERROR_MESSAGE}</h2>}
    </div>
  );
};
export default LearningPathCoreContent;
