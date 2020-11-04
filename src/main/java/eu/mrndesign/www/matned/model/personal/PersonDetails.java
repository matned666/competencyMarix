package eu.mrndesign.www.matned.model.personal;

import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.address.Country;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;



@Entity
@EntityListeners(AuditingEntityListener.class)
public class PersonDetails extends BaseEntity implements AuditInterface {


    private String motherName;
    private String motherMaidenName;
    private String fatherName;

    @ManyToOne
    private Document documentType;
    private String documentNumber;
    private String idNumber;
    @ManyToOne
    private Country nationality;



}
