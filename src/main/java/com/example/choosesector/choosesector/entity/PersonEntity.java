package com.example.choosesector.choosesector.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @Column(unique = true, nullable = false)
    private String username;
    @NotNull
    private String password;
    private boolean agree = false;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "choosed_sector",
    joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "sector_id"))
    private Set<SectorEntity> chosedSectors;

    public PersonEntity() {
    }

    public PersonEntity(String username, String password, boolean isAgree, Set<Role> roles, Set<SectorEntity> chosedSectors) {

        this.username = username;
        this.password = password;
        this.agree = isAgree;
        this.roles = roles;
        this.chosedSectors = chosedSectors;
    }
}
