package app.openschool.coursemanagement.api;

import app.openschool.coursemanagement.entity.Category;

public class CategoryGenerator {

  public static Category generateCategory() {
    Category category = new Category();
    category.setId(1L);
    category.setParentCategoryId(2);
    category.setTitle("Java");
    category.setLogoPath("AAA");
    category.setSubCategoryCount(3);
    return category;
  }
}
