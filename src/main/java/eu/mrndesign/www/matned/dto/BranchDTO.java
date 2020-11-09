package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.common.EntityDescriptionImplementation;
import eu.mrndesign.www.matned.model.company.Branch;

import java.time.LocalDate;
import java.util.Objects;

import static eu.mrndesign.www.matned.Patterns.DATE_FORMATTER;

public class BranchDTO implements DTOEintityDescriptionImplementation{

    public static BranchDTO applyWithAudit(Branch entity){
        BranchDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static BranchDTO apply(Branch entity){
        return new BranchDTO(
                entity.getEntityDescription() != null ? entity.getEntityDescription().getName(): null,
                entity.getEntityDescription() != null ? entity.getEntityDescription().getDescription(): null,
                entity.getBranchCreationDate() != null ? entity.getBranchCreationDate().format(DATE_FORMATTER): null);
    }

    private String name;
    private String description;
    private String branchCreationDate;

    private AuditDTO auditDTO;

    public BranchDTO() {
    }

    public BranchDTO(String name, String description, String branchCreationDate) {
        this.name = name;
        this.description = description;
        this.branchCreationDate = branchCreationDate;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranchCreationDate() {
        return branchCreationDate;
    }

    public void setBranchCreationDate(String branchCreationDate) {
        this.branchCreationDate = branchCreationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchDTO branchDTO = (BranchDTO) o;
        return Objects.equals(name, branchDTO.name) &&
                Objects.equals(description, branchDTO.description) &&
                Objects.equals(branchCreationDate, branchDTO.branchCreationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, branchCreationDate);
    }

    @Override
    public String toString() {
        return "BranchDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", branchCreationDate='" + branchCreationDate + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }
}
