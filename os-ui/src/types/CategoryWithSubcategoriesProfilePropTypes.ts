export interface CategoryWithSubcategoriesProfilePropTypes{
    parentCategory:string;
    subcategories:Array<{id:number, title:string}>
    /* addSubcategory(subcategoryId:number):void
    removeSubcategory(subcategoryId:number):void */
}
