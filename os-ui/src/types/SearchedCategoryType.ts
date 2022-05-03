interface SubcategoryType{
    id:number;
    title:string;
}

export interface SearchedCategoryType{
    [index:string]:SubcategoryType[];
}
