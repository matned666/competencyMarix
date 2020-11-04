package eu.mrndesign.www.matned.model.company;

import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.personal.Person;
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
    @ManyToOne
    private Department department;

}
