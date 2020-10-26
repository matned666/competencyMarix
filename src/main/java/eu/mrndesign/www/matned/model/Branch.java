package eu.mrndesign.www.matned.model;

import eu.mrndesign.www.matned.model.address.Address;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Branch extends BaseEntity {

    @Embedded
    private EntityDescription entityDescription;
    @ManyToOne
    private Address address;
    @OneToMany(mappedBy = "id")
    private List<Department> departments;

}
