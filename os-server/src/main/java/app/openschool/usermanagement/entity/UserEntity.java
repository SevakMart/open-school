package app.openschool.usermanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String name;

    @Column(name = "last_name")
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "profession_name")
    private String professionName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @Column(name = "user_img_path")
    private String userImgPath;

    @Column(name = "course_count")
    private Integer courseCount;

    @Column(name = "email_path")
    private String emailPath;

    @Column(name = "linkedin_path")
    private String linkedinPath;


    public UserEntity() {
    }

    public UserEntity(String name, String email, String password, RoleEntity role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public void setCompanyName(CompanyEntity company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfessionName() {
        return professionName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public RoleEntity getRole() {
        return role;
    }

    public CompanyEntity getCompany() {
        return company;
    }
}
