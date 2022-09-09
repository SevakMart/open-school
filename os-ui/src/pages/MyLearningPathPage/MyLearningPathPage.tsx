import { useNavigate } from 'react-router-dom';
import MyLearningPathMainContent from './Subcomponents/MainContent/MainContent';
import Header from './Subcomponents/Header/Header';
import SuggestedCourses from './Subcomponents/SuggestedCourses/SuggestedCourses';
import { EXPLORE_COURSES } from '../../constants/Strings';
import styles from './MyLearningPathPage.module.scss';

/* eslint-disable max-len */

const MyLearningPathPage = ({ userInfo }:any) => {
  const navigate = useNavigate();
  const { id: userId, token } = userInfo;
  const { exploreCourseButton } = styles;

  const exploreLearningPaths = () => {
    navigate('/exploreLearningPaths');
  };

  return (
    <>
      <Header userId={userId} token={token} />
      <MyLearningPathMainContent />
      <button type="button" className={exploreCourseButton} onClick={exploreLearningPaths}>{EXPLORE_COURSES}</button>
      <SuggestedCourses userId={userId} token={token} />
    </>
  );
};
export default MyLearningPathPage;
