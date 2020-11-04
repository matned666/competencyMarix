package eu.mrndesign.www.matned.model.company;

import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class CompanyDetails extends BaseEntity implements AuditInterface {

    private String VAT_no;
    private LocalDate companyCreationDate;
    private boolean isActive;
    @ManyToOne
    private Branch motherBranch;

    public String getVAT_no() {
        return VAT_no;
    }

    public void setVAT_no(String VAT_no) {
        this.VAT_no = VAT_no;
    }

    public LocalDate getCompanyCreationDate() {
        return companyCreationDate;
    }

    public void setCompanyCreationDate(LocalDate companyCreationDate) {
        this.companyCreationDate = companyCreationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Branch getMotherBranch() {
        return motherBranch;
    }

    public void setMotherBranch(Branch motherBranch) {
        this.motherBranch = motherBranch;
    }
}
