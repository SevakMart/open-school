import LearningPathHeader from './Subcomponents/LearningPathHeader/LearningPathHeader';
import Search from '../../../../component/Search/Search';
import LearningPathCoreContent from './Subcomponents/LearningPathCoreContent/LearningPathCoreContent';
import styles from './LearningPathContent.module.scss';

const LearningPathContent = ({ filterTabIsVisible }:{filterTabIsVisible:boolean}) => {
  const { learningPathsMainContainer } = styles;
  return (
    <div className={learningPathsMainContainer} style={filterTabIsVisible ? { width: '75%', transitionDuration: '2s' } : { width: '98%', transitionDuration: '2s' }}>
      <LearningPathHeader />
      <Search changeUrlQueries={() => null} paddingLeft="0" leftPosition={filterTabIsVisible ? '20%' : '10%'} />
      <LearningPathCoreContent />
    </div>
  );
};
export default LearningPathContent;
