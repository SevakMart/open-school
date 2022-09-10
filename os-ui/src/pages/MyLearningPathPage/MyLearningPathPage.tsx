import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import MyLearningPathMainContent from './Subcomponents/MainContent/MainContent';
import Header from './Subcomponents/Header/Header';
import SuggestedCourses from './Subcomponents/SuggestedCourses/SuggestedCourses';
import Button from '../../component/Button/Button';

/* eslint-disable max-len */

const MyLearningPathPage = ({ userInfo }:any) => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { id: userId, token } = userInfo;

  const exploreLearningPaths = () => {
    navigate('/exploreLearningPaths');
  };

  return (
    <>
      <Header userId={userId} token={token} />
      <MyLearningPathMainContent />
      <div style={{ textAlign: 'center' }}>
        <Button.MainButton className={['exploreCourseButton']} onClick={exploreLearningPaths}>
          {t('button.myLearningPathsPage.exploreCourses')}
        </Button.MainButton>
      </div>
      <SuggestedCourses userId={userId} token={token} />
    </>
  );
};
export default MyLearningPathPage;
