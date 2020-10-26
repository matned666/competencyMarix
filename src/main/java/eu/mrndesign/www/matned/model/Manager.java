package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Manager  extends BaseEntity {

    @OneToOne
    private Person person;

    //    case when one person is a manager of many departments ---> may happen?
    @OneToMany
    private List<Department> departments;




}
