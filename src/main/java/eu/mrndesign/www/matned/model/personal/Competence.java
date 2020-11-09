package eu.mrndesign.www.matned.model.personal;

import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Competence  extends BaseEntity implements AuditInterface {

    @Embedded
    private EntityDescription entityDescription;

    public Competence() {
    }

    public Competence(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public EntityDescription getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Competence that = (Competence) o;
        return Objects.equals(entityDescription, that.entityDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), entityDescription);
    }

    @Override
    public String toString() {
        return "Competence{" +
                "entityDescription=" + entityDescription +
                '}';
    }
}
