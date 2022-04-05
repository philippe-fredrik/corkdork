package se.iths.corkdork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String role;

    @OneToMany(cascade = CascadeType.ALL,
    orphanRemoval = true, mappedBy = "role")
    private Set<UserEntity> users = new HashSet<>();

    public RoleEntity(String role) {
        this.role = role;
    }

    public RoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void addUser(UserEntity user) {
        users.add(user);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
