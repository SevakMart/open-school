package app.openschool.category.api;

import app.openschool.category.Category;

public class CategoryGenerator {

  public static Category generateCategory() {
    Category category = new Category();
    category.setId(1L);
    category.setTitle("Java");
    category.setLogoPath("AAA");
    return category;
  }
}
