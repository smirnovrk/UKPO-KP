package com.yellow.photoshare.entity;

import com.yellow.photoshare.validation.PasswordMatches;
import org.hibernate.search.annotations.Field;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "UserEntity")
@Table(name="Users")
@Indexed
@PasswordMatches
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    @Field
    @NotNull
    @NotEmpty
    private String name;

    @Column(name = "Email", nullable = false)
    @Field
    @NotNull
    @NotEmpty
    private String email;

    @Column(name = "Password", nullable = false)
    @Field
    @NotNull
    @NotEmpty
    private String password;

    @Transient
    private String matchingPassword;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<TaskEntity> task;

    public void addTask(TaskEntity taskEntity) {
        task.add(taskEntity);
        taskEntity.setUserEntity(this);
    }

    public void removeTask(TaskEntity taskEntity) {
        task.remove(taskEntity);
        taskEntity.setUserEntity(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Set<TaskEntity> getTask() {
        return task;
    }

    public void setTask(Set<TaskEntity> task) {
        this.task = task;
    }
}
