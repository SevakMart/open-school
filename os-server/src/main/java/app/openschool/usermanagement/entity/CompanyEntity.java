package app.openschool.usermanagement.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "company_name")
    private String companyName;

    @OneToMany(mappedBy = "company")
    private List<UserEntity> users;

    public CompanyEntity() {
    }

    public CompanyEntity(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }
}
