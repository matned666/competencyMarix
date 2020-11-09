package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.personal.Document;

import java.util.Objects;

import static eu.mrndesign.www.matned.Patterns.DATE_FORMATTER;

public class DocumentDTO {

    public static DocumentDTO applyWithAudit(Document entity){
        DocumentDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static DocumentDTO apply(Document entity){
        return new DocumentDTOBuilder(entity.getTitle())
                .description(entity.getDescription())
                .releasedBy(entity.getReleasedBy())
                .documentNumber(entity.getDocumentNumber())
                .documentType(entity.getDocumentNumber() != null ? entity.getDocumentType().name() : null)
                .documentNumber(entity.getDocumentNumber())
                .idNumber(entity.getIdNumber())
                .releaseDate(entity.getReleaseDate() != null ? entity.getReleaseDate().format(DATE_FORMATTER) : null)
                .expiryDate(entity.getExpiryDate() != null ? entity.getExpiryDate().format(DATE_FORMATTER) : null)
                .build();
    }

    private String title;
    private String description;
    private String releasedBy;
    private String documentType;
    private String documentNumber;
    private String idNumber;
    private String releaseDate;
    private String expiryDate;

    private AuditDTO auditDTO;

    public DocumentDTO() {
    }

    private DocumentDTO(DocumentDTOBuilder builder){
        this.title = builder.title;
        this.description = builder.description;
        this.releasedBy = builder.releasedBy;
        this.documentType = builder.documentType;
        this.documentNumber = builder.documentNumber;
        this.idNumber = builder.idNumber;
        this.releaseDate = builder.releaseDate;
        this.expiryDate = builder.expiryDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getReleasedBy() {
        return releasedBy;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public AuditDTO getAuditDTO() {
        return auditDTO;
    }

    public void setAuditDTO(AuditDTO auditDTO) {
        this.auditDTO = auditDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentDTO that = (DocumentDTO) o;
        return title.equals(that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(releasedBy, that.releasedBy) &&
                Objects.equals(documentType, that.documentType) &&
                Objects.equals(documentNumber, that.documentNumber) &&
                Objects.equals(idNumber, that.idNumber) &&
                Objects.equals(releaseDate, that.releaseDate) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, releasedBy, documentType, documentNumber, idNumber, releaseDate, expiryDate);
    }

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releasedBy='" + releasedBy + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }

    public static class DocumentDTOBuilder {

        private String title;
        private String description;
        private String releasedBy;
        private String documentType;
        private String documentNumber;
        private String idNumber;
        private String releaseDate;
        private String expiryDate;

        public DocumentDTOBuilder(String title) {
            this.title = title;
        }

        public DocumentDTOBuilder description(String description){
            this.description = description;
            return this;
        }
        public DocumentDTOBuilder releasedBy(String releasedBy){
            this.releasedBy = releasedBy;
            return this;
        }
        public DocumentDTOBuilder documentType(String documentType){
            this.documentType = documentType;
            return this;
        }
        public DocumentDTOBuilder documentNumber(String documentNumber){
            this.documentNumber = documentNumber;
            return this;
        }
        public DocumentDTOBuilder idNumber(String idNumber){
            this.idNumber = idNumber;
            return this;
        }
        public DocumentDTOBuilder releaseDate(String releaseDate){
            this.releaseDate = releaseDate;
            return this;
        }
        public DocumentDTOBuilder expiryDate(String expiryDate){
            this.expiryDate = expiryDate;
            return this;
        }

        public DocumentDTO build(){
            return new DocumentDTO(this);
        }
    }
}
