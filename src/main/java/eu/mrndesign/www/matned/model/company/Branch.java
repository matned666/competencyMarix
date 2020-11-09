package eu.mrndesign.www.matned.model.company;

import eu.mrndesign.www.matned.model.address.Address;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Branch extends BaseEntity implements AuditInterface {

    @Embedded
    private EntityDescription entityDescription;
    private LocalDate branchCreationDate;

    @ManyToOne
    private Address address;
    @ManyToMany
    private List<Department> departments;

    public Branch() {
    }

    public Branch(EntityDescription entityDescription, Address address, List<Department> departments) {
        this.entityDescription = entityDescription;
        this.address = address;
        this.departments = departments;
    }

    public EntityDescription getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getBranchCreationDate() {
        return branchCreationDate;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "entityDescription=" + entityDescription +
                ", branchCreationDate=" + branchCreationDate +
                ", address=" + address +
                ", departments=" + departments +
                '}';
    }
}
