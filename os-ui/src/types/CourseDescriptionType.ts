export interface CourseDescriptionType {
    title:string;
    description:string;
    goal:string;
    modules:{title:string, description:string, moduleItemSet:{[index:string]:string}[]}[];
    mentorDto:{[index:string]:string};
    rating:number;
    enrolled:number;
    level:string;
    language:string;
    duration:number;
}
