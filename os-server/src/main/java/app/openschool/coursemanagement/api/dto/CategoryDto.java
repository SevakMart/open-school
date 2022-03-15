package app.openschool.coursemanagement.api.dto;

public class CategoryDto {

    private String title;
    private String logoPath;

    public CategoryDto() {
    }

    public CategoryDto(String title, String logoPath) {
        this.title = title;
        this.logoPath = logoPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
