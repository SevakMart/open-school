package app.openschool.usermanagement.api.dto;


public class MentorDto {

    private String name;

    private String surname;

    private String professionName;

    private String companyName;

    private Integer courseCount;

    private String userImgPath;

    private String emailPath;

    private String linkedinPath;


    public MentorDto() {
    }

    public String getEmailPath() {
        return emailPath;
    }

    public void setEmailPath(String emailPath) {
        this.emailPath = emailPath;
    }

    public String getLinkedinPath() {
        return linkedinPath;
    }

    public void setLinkedinPath(String linkedinPath) {
        this.linkedinPath = linkedinPath;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
