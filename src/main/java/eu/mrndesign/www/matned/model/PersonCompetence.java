package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PersonCompetence  extends BaseEntity {

    private Integer level;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Competence competence;

}
