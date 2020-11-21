package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.company.Company;
import eu.mrndesign.www.matned.model.company.CompanyDetails;

import java.util.Objects;

import static eu.mrndesign.www.matned.Patterns.DATE_FORMATTER;

public class CompanyDTO {

    public static CompanyDTO applyWithAudit(Company entity, CompanyDetails details) {
        CompanyDTO dto = apply(entity, details);
        dto.auditDTO = AuditInterface.apply(entity);
        dto.detailsAuditDTO = AuditInterface.apply(details);
        return dto;
    }

    public static CompanyDTO apply(Company entity, CompanyDetails details){
        EntityDescription entityDescription = entity.getEntityDescription();
        String companyCreationDate = details.getCompanyCreationDate() != null ? details.getCompanyCreationDate().format(DATE_FORMATTER) : null;
        String description = entityDescription != null ? entityDescription.getDescription() : null;
        String name = entityDescription != null ? entityDescription.getName() : null;
        return new CompanyDTO.CompanyDTOBuilder(name)
                .description(description)
                .companyCreationDate(companyCreationDate)
                .VAT_no(details.getVAT_no())
                .build();
    }

    public static CompanyDTO applyWithAudit(Company entity) {
        CompanyDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static CompanyDTO apply(Company entity){
        EntityDescription entityDescription = entity.getEntityDescription();
        String name = entityDescription != null ? entityDescription.getName() : null;
        String description = entityDescription != null ? entityDescription.getDescription() : null;
        return new CompanyDTO.CompanyDTOBuilder(name)
                .description(description)
                .build();
    }

    private String name;
    private String description;
    private String VAT_no;
    private String companyCreationDate;

    private AuditDTO auditDTO;
    private AuditDTO detailsAuditDTO;

    public CompanyDTO() {
    }

    private CompanyDTO(CompanyDTOBuilder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.VAT_no = builder.VAT_no;
        this.companyCreationDate = builder.companyCreationDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVAT_no() {
        return VAT_no;
    }

    public String getCompanyCreationDate() {
        return companyCreationDate;
    }


    public AuditDTO getAuditDTO() {
        return auditDTO;
    }

    public AuditDTO getDetailsAuditDTO() {
        return detailsAuditDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDTO dto = (CompanyDTO) o;
        return Objects.equals(name, dto.name) &&
                Objects.equals(description, dto.description) &&
                Objects.equals(VAT_no, dto.VAT_no) &&
                Objects.equals(companyCreationDate, dto.companyCreationDate) &&
                Objects.equals(auditDTO, dto.auditDTO) &&
                Objects.equals(detailsAuditDTO, dto.detailsAuditDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, VAT_no, companyCreationDate, auditDTO, detailsAuditDTO);
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", VAT_no='" + VAT_no + '\'' +
                ", companyCreationDate='" + companyCreationDate + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }

    public static class CompanyDTOBuilder {

        String name;
        String description;
        String VAT_no;
        String companyCreationDate;

        public CompanyDTOBuilder(String name) {
            this.name = name;
        }

        public CompanyDTOBuilder description(String description){
            this.description = description;
            return this;
        }

        public CompanyDTOBuilder VAT_no(String VAT_no){
            this.VAT_no = VAT_no;
            return this;
        }

        public CompanyDTOBuilder companyCreationDate(String companyCreationDate){
            this.companyCreationDate = companyCreationDate;
            return this;
        }

        public CompanyDTO build(){
            return new CompanyDTO(this);
        }
    }
}
