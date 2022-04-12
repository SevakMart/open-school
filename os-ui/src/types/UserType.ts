export interface SignedInUser {
    data:{name:string,
    surname?:string,
    professionName?:string,
    courseCount?:number,
    userImgPath?:string,
    roleType:string,
    company?:string,
    }
    status:number;
}
export interface NotSignedInUser {
    data:{
        timeStamp:string,
        message:string,
    };
    status:number;
}
