package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Position  extends BaseEntity implements AuditInterface, EntityDescriptionImplementation{



    @Embedded
    private EntityDescription entityDescription;
    @OneToOne
    private PositionDetails positionDetails;

    public EntityDescription getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public PositionDetails getPositionDetails() {
        return positionDetails;
    }

    public void setPositionDetails(PositionDetails positionDetails) {
        this.positionDetails = positionDetails;
    }
}
