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
    private Company company;
    @ManyToOne
    private Address address;

    public Branch() {
    }

    public Branch(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public Branch(EntityDescription entityDescription, Address address) {
        this.entityDescription = entityDescription;
        this.address = address;
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

    public void setBranchCreationDate(LocalDate branchCreationDate) {
        this.branchCreationDate = branchCreationDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "entityDescription=" + entityDescription +
                ", branchCreationDate=" + branchCreationDate +
                ", address=" + address +
                '}';
    }
}
