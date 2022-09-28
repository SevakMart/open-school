import { useTranslation } from 'react-i18next';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import LinkedinIcon from '../../../../icons/Linkedin';
import ModuleItem from '../ModuleItem/ModuleItem';
import styles from './CourseMainContent.module.scss';

const CourseMainContent = ({
  description, goal, modules, mentorDto, title,
}:Omit<CourseDescriptionType, 'rating'|'enrolled'|'level'|'language'|'duration'>) => {
  const { t } = useTranslation();
  const {
    mainContent, subContent, authorInformationContainer, authorInfo,
    imageAndFullName, moduleListContainer, mainTitle, mainCourseInfoContainer,
  } = styles;

  return (
    <div className={mainContent}>
      <h1 className={mainTitle}>{title}</h1>
      <div className={mainCourseInfoContainer}>
        <div className={subContent}>
          <h2>{t('string.courseDescriptionPage.title.description')}</h2>
          <p>{description}</p>
        </div>
        <div className={subContent}>
          <h2>{t('string.courseDescriptionPage.title.goal')}</h2>
          <p>{goal}</p>
        </div>
        <div className={subContent}>
          <h2>{t('string.courseDescriptionPage.title.modulesList')}</h2>
          <div className={moduleListContainer}>
            {
              modules.map((module, index) => (
                <ModuleItem
                  key={index}
                  moduleInfo={module}
                />
              ))
            }
          </div>
        </div>
      </div>
      <div className={authorInformationContainer}>
        <h2>{t('string.courseDescriptionPage.title.authorInfo')}</h2>
        <div className={authorInfo}>
          <div className={imageAndFullName}>
            <img src="https://reactjs.org/logo-og.png" alt={`${mentorDto.name} ${mentorDto.surname}`} />
            <p>{`${mentorDto.name} ${mentorDto.surname}`}</p>
          </div>
          <a href={mentorDto.linkedinPath}><LinkedinIcon size="1.25rem" /></a>
        </div>
      </div>
    </div>
  );
};
export default CourseMainContent;
