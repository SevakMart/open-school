import { UserCourseType } from '../../../../types/UserCourseType';

const CompletedCourse = ({ title, courseStatus, grade }:Pick<UserCourseType, 'title'|'courseStatus'|'grade'>) => {
  const a = 1;
  return (
    <p>Hello world</p>
  );
};
export default CompletedCourse;
