import { useState } from 'react';
import { CourseDescriptionType } from '../../../../types/CourseDescriptionType';

const CourseSummary = ({
  rating, enrolled, level, language, duration,
}:Omit<CourseDescriptionType, 'title'|'description'|'goal'|'modules'|'mentorDto'>) => {
  const a = 1;
  return (
    <h1>Hello World</h1>
  );
};
export default CourseSummary;
