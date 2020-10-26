package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Employee  extends BaseEntity {

    @ManyToOne
    private Manager manager;
    @OneToOne
    private Person person;
    @ManyToOne
    private Position position;
    @OneToMany
    private List<Inventory> inventory;
    @ManyToOne
    private Department department;

}
