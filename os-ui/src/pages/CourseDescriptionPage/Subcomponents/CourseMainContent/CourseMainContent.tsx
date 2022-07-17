import { useState } from 'react';
import { CourseDescriptionType } from '../../../../types/CourseDescriptionType';

const CourseMainContent = ({
  title, description, goal, modules, mentorDto,
}:Omit<CourseDescriptionType, 'rating'|'enrolled'|'level'|'language'|'duration'>) => {
  const a = 1;
  return (
    <h1>Hello World</h1>
  );
};
export default CourseMainContent;
