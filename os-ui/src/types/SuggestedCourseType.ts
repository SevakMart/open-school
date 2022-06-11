export interface SuggestedCourseType{
    title:string
    rating:number
    difficulty:string
    keywords:string[]
    isBookMarked?:boolean
    courseId?:number
    saveCourse?(id:number):void
    deleteCourse?(id:number):void
}
