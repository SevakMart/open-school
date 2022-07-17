import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { CourseDescriptionType } from '../../../../types/CourseDescriptionType';
import styles from './CourseMainContent.module.scss';

const CourseMainContent = ({
  description, goal, modules, mentorDto,
}:Omit<CourseDescriptionType, 'title'|'rating'|'enrolled'|'level'|'language'|'duration'>) => {
  const { t } = useTranslation();
  const { subContent, coursesInProgram } = styles;
  return (
    <div>
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
    </div>
  );
};
export default CourseMainContent;
