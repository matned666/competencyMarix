package eu.mrndesign.www.matned.model.company;

import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.common.EntityDescriptionImplementation;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Department  extends BaseEntity implements EntityDescriptionImplementation {

    @Embedded
    private EntityDescription entityDescription;
    @ManyToOne
    private Branch branch;

    public Department() {
    }

    public Department(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }



    @Override
    public EntityDescription getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
