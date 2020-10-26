package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class City  extends BaseEntity {

    private String postCode;
    private String name;
    @ManyToOne
    private Country country;

}
