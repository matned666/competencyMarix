package eu.mrndesign.www.matned.model.company;

import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.common.EntityDescriptionImplementation;
import eu.mrndesign.www.matned.model.personal.Competence;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Inventory extends BaseEntity implements AuditInterface, EntityDescriptionImplementation {

    @Embedded
    private EntityDescription entityDescription;
    private BigDecimal price;
    @ManyToMany
    private List<Competence> requiredCompetences;


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
