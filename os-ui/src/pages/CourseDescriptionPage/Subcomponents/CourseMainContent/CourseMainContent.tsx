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
    name, surname, linkedinPath, userImgPath,
  } = mentorDto;
  const {
    mainContent, subContent, coursesInProgram, authorInformationContainer, authorInfo, imageAndFullName,
  } = styles;
  console.log(modules);
  return (
    <div className={mainContent}>
      <div className={subContent}>
        <h2>{t('What you will learn')}</h2>
        <p>{description}</p>
      </div>
      <div className={subContent}>
        <h2>{t('Course Goal')}</h2>
        <p>{goal}</p>
      </div>
      <div className={subContent}>
        <h2>{t('Courses In This Program')}</h2>
      </div>
      <div className={authorInformationContainer}>
        <h2>{t('Author Information')}</h2>
        <div className={authorInfo}>
          <div className={imageAndFullName}>
            <img src="https://reactjs.org/logo-og.png" alt={`${name} ${surname}`} />
            <p>{`${name} ${surname}`}</p>
          </div>
          <a href={linkedinPath}><LinkedinIcon size="1.25rem" /></a>
        </div>
      </div>
    </div>
  );
};
export default CourseMainContent;
