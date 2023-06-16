export interface CourseDescriptionType {
    title:string;
    description:string;
    enrolledCourseId: number;
    goal:string;
    modules:{link?:any, title:string, estimatedTime?:number;
         description:string, moduleItemSet:{[index:string]:string}[]}[];
    mentorDto:{[index:string]:string};
    Rating:number;
    Enrolled:number;
    Course_Level:string;
    Language:string;
    Estimated_efforts:number;
	currentUserEnrolled: boolean;
}
export interface UserCourseType{
    title:string;
    courseStatus:string;
    percentage:number;
    remainingTime:number;
    grade:number;
    dueDate:string;
	courseId:number;
	id:number;
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
