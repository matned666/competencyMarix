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


    public Company() {
    }

    public Company(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public Company(EntityDescription entityDescription, CompanyDetails companyDetails, List<Branch> branches) {
        this.entityDescription = entityDescription;
        this.companyDetails = companyDetails;
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


    @Override
    public String toString() {
        return "Company{" +
                "entityDescription=" + entityDescription +
                ", companyDetails=" + companyDetails +
                '}';
    }
}
