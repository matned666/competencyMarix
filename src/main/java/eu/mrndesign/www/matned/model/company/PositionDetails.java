package eu.mrndesign.www.matned.model.company;

import eu.mrndesign.www.matned.model.audit.AuditInterface;
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
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PositionDetails  extends BaseEntity implements AuditInterface {

    LocalDate positionCreationDate;
    LocalDate positionDeactivationDate;
    boolean isActive;
    @ManyToMany
    List<Inventory> inventories;

    public PositionDetails() {
    }

    public LocalDate getPositionCreationDate() {
        return positionCreationDate;
    }

    public LocalDate getPositionDeactivationDate() {
        return positionDeactivationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PositionDetails that = (PositionDetails) o;
        return isActive == that.isActive &&
                Objects.equals(positionCreationDate, that.positionCreationDate) &&
                Objects.equals(positionDeactivationDate, that.positionDeactivationDate) &&
                Objects.equals(inventories, that.inventories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), positionCreationDate, positionDeactivationDate, isActive, inventories);
    }

    @Override
    public String toString() {
        return "PositionDetails{" +
                "positionCreationDate=" + positionCreationDate +
                ", positionDeactivationDate=" + positionDeactivationDate +
                ", isActive=" + isActive +
                ", inventories=" + inventories +
                '}';
    }
}
