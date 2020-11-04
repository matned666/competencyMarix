package eu.mrndesign.www.matned.model.company;

import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Company extends BaseEntity implements AuditInterface {


    @Embedded
    private EntityDescription entityDescription;
    @OneToOne
    private CompanyDetails companyDetails;
    @OneToMany(mappedBy = "id")
    private List<Branch> branches;

    public Company() {
    }

    public Company(EntityDescription entityDescription, CompanyDetails companyDetails, List<Branch> branches) {
        this.entityDescription = entityDescription;
        this.companyDetails = companyDetails;
        this.branches = branches;
    }

    public EntityDescription getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public String toString() {
        return "Company{" +
                "entityDescription=" + entityDescription +
                ", companyDetails=" + companyDetails +
                ", branches=" + branches +
                '}';
    }
}
