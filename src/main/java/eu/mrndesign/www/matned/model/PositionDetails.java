package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PositionDetails  extends BaseEntity implements EntityDescriptionImplementation {

    @Embedded
    private EntityDescription entityDescription;

    @Override
    public EntityDescription getEntityDescription() {
        return entityDescription;
    }
}
