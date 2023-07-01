import { useTranslation } from 'react-i18next';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import ModuleItem from '../ModuleItem/ModuleItem';
import AuthorInfo from '../AuthorInfo/AuthorInfo';
import styles from './CourseMainContent.module.scss';
/* eslint-disable max-len */
const CourseMainContent = ({
  description, goal, modules, mentorDto, title,
}: Omit<CourseDescriptionType, 'Rating' | 'Enrolled' | 'Course Level' | 'Language' | 'Estimated efforts'>) => {
  const { t } = useTranslation();
  const {
    mainContent, subContent, moduleListContainer, mainTitle, mainCourseInfoContainer,
  } = styles;

  return (
    <div className={mainContent}>
      <h1 className={mainTitle}>{title}</h1>
      <div className={mainCourseInfoContainer}>
        <div className={subContent}>
          <h2>{t('What You Will Learn')}</h2>
          <p>{description}</p>
        </div>
        <div className={subContent}>
          <h2>{t('Course Goal')}</h2>
          <p>{goal}</p>
        </div>
        <div className={subContent}>
          <h2>{t('Modules in This Program')}</h2>
          <div className={moduleListContainer}>
            {modules.map((module, index) => (<ModuleItem key={index} moduleInfo={module} />))}
          </div>
        </div>
      </div>
      <AuthorInfo mentorName={mentorDto.name} mentorSurname={mentorDto.surname} mentorLinkedIn={mentorDto.linkedinPath} />
    </div>
  );
};
export default CourseMainContent;
