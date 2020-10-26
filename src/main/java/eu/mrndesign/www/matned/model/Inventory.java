package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Version;
import java.math.BigDecimal;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Inventory extends BaseEntity  implements AuditInterface, EntityDescriptionImplementation{

    @Embedded
    private EntityDescription entityDescription;
    private BigDecimal price;


    public EntityDescription getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
