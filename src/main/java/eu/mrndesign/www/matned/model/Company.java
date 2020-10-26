package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Company extends BaseEntity {


    @Embedded
    private EntityDescription entityDescription;
    private String VAT_no;
    @OneToMany(mappedBy = "id")
    private List<Branch> branches;

}
