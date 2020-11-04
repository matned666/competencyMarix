package eu.mrndesign.www.matned.model.personal;

import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Competence  extends BaseEntity {

    @Embedded
    private EntityDescription entityDescription;

}
