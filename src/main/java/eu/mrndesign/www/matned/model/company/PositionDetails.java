package eu.mrndesign.www.matned.model.company;

import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.common.EntityDescriptionImplementation;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PositionDetails  extends BaseEntity{

    LocalDate positionCreationDate;
    LocalDate positionDeactivationDate;
    @ManyToMany
    List<Inventory> inventories;
    boolean isActive;


}
