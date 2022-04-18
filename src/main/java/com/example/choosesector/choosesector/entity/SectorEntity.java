package com.example.choosesector.choosesector.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;


import javax.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "sector")
@Getter
@Setter
public class SectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String sector;


    @NotNull
    private String group;

    @ManyToMany
    @JoinTable(name = "choosed_sector",
    joinColumns = @JoinColumn(name = "sector_id"),
    inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<PersonEntity> persons;

    public SectorEntity() {
    }


    public SectorEntity( String sector, String group, Set<PersonEntity> persons) {
        this.sector = sector;
        this.group = group;
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectorEntity)) return false;
        SectorEntity that = (SectorEntity) o;
        return getId() == that.getId() && Objects.equals(getSector(), that.getSector()) && Objects.equals(getGroup(), that.getGroup()) && Objects.equals(getPersons(), that.getPersons());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSector(), getGroup(), getPersons());
    }

    @Override
    public String toString() {
        return "SectorEntity{" +
                "id=" + id +
                ", sector='" + sector + '\'' +
                ", group='" + group + '\'' +
                ", persons=" + persons +
                '}';
    }
}
