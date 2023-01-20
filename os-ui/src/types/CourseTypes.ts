export interface CourseDescriptionType {
    title:string;
    description:string;
    goal:string;
    modules:{link?:any, title:string,
         description:string, moduleItemSet:{[index:string]:string}[]}[];
    mentorDto:{[index:string]:string};
    rating:number;
    enrolled:number;
    level:string;
    language:string;
    duration:number;
}
export interface UserCourseType{
    title:string;
    courseStatus:string;
    percentage:number;
    remainingTime:number;
    grade:number;
    dueDate:string;
}
export type CompleteCourse = Pick<UserCourseType, 'title'|'courseStatus'|'grade'>
export type ProgressedCourse = Omit<UserCourseType, 'grade'>

export enum CourseContent {
    ALLCOURSES='All Courses',
    SAVEDCOURSES='Saved Courses',
  }
export interface SuggestedCourseType{
    id:number;
    title:string;
    rating:number;
    difficulty:string;
    keywords:string[];
}
