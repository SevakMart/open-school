import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { CourseDescriptionType } from '../../../../types/CourseDescriptionType';
import LinkedinIcon from '../../../../icons/Linkedin';
import styles from './CourseMainContent.module.scss';

const CourseMainContent = ({
  description, goal, modules, mentorDto,
}:Omit<CourseDescriptionType, 'title'|'rating'|'enrolled'|'level'|'language'|'duration'>) => {
  const { t } = useTranslation();
  const {
    mainContent, subContent, coursesInProgram, authorInformationContainer,
    authorInfo, imageAndFullName,
  } = styles;

  return (
    <div className={mainContent}>
      <div className={subContent}>
        <h2>{t('string.courseDescription.title.description')}</h2>
        <p>{description}</p>
      </div>
      <div className={subContent}>
        <h2>{t('string.courseDescription.title.goal')}</h2>
        <p>{goal}</p>
      </div>
      <div className={subContent}>
        <h2>{t('string.courseDescription.title.modulesList')}</h2>
      </div>
      <div className={authorInformationContainer}>
        <h2>{t('string.courseDescription.title.authorInfo')}</h2>
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
