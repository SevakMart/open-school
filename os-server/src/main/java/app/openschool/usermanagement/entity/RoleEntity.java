package app.openschool.usermanagement.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "role_type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "role")
    private List<UserEntity> users;

    public RoleEntity() {
    }

    public RoleEntity(String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
