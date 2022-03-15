package app.openschool.coursemanagement.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(name = "parent_category_id")
    private String parentCategoryId;

    @Column(name = "logo_path")
    private String logoPath;

    @OneToMany(mappedBy = "category")
    private List<CourseEntity> courses;

    public CategoryEntity() {
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public CategoryEntity(String title) {
        this.title = title;
    }

    public CategoryEntity(String title, String parentCategoryId) {
        this.title = title;
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
