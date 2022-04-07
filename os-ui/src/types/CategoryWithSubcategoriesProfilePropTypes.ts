export interface CategoryWithSubcategoriesProfilePropTypes{
    parentCategory:string;
    subcategories:Array<{id:number, title:string}>
}
