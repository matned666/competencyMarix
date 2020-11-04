package eu.mrndesign.www.matned.model.personal;

import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.address.Country;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Document extends BaseEntity implements AuditInterface {

    private String title;

    @Column(length = 2000)
    private String description;

    private String releasedBy;
    private DocumentType documentType;
    private String documentNumber;
    private String idNumber;
    private LocalDate releaseDate;
    private LocalDate expiryDate;

    public Document() {
    }

    private Document(DocBuilder builder) {
    }

    @ManyToOne
    private Country releaseCountry;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleasedBy() {
        return releasedBy;
    }

    public void setReleasedBy(String releasedBy) {
        this.releasedBy = releasedBy;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String cardNumber) {
        this.documentNumber = cardNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Country getReleaseCountry() {
        return releaseCountry;
    }

    public void setReleaseCountry(Country releaseCountry) {
        this.releaseCountry = releaseCountry;
    }

    public enum DocumentType{
        IDENTITY_CARD,
        PASSPORT,
        VISA,
        DRIVERS_LICENCE,
        CERTIFICATE,
        OTHER_DOCUMENT;

        public String nice(){
            switch (this){
                case IDENTITY_CARD: return "Identity card";
                case PASSPORT: return "Passport";
                case VISA: return "Visa";
                case DRIVERS_LICENCE: return "Driver's licence";
                case CERTIFICATE: return "Certificate";
                default: return "Other document";
            }
        }

        public String symbol(){
            switch (this){
                case IDENTITY_CARD: return "ID";
                case PASSPORT: return "PASS";
                case VISA: return "VISA";
                case DRIVERS_LICENCE: return "DL";
                case CERTIFICATE: return "CERT";
                default: return "OTHER";
            }
        }



    }

    public static class DocBuilder {

        private String title;
        private String description;
        private String releasedBy;
        private DocumentType documentType;
        private String documentNumber;
        private String idNumber;
        private LocalDate releaseDate;
        private LocalDate expiryDate;

        public DocBuilder() {
        }

        public DocBuilder title(String title){
            this.title = title;
            return this;
        }

        public DocBuilder description(String description){
            this.description = description;
            return this;
        }

        public DocBuilder releasedBy(String releasedBy){
            this.releasedBy = releasedBy;
            return this;
        }

        public DocBuilder documentType(DocumentType documentType){
            this.documentType = documentType;
            return this;
        }

        public DocBuilder documentNumber(String documentNumber){
            this.documentNumber = documentNumber;
            return this;
        }

        public DocBuilder idNumber(String idNumber){
            this.idNumber = idNumber;
            return this;
        }

        public DocBuilder releaseDate(LocalDate releaseDate){
            this.releaseDate = releaseDate;
            return this;
        }

        public DocBuilder expiryDate(LocalDate expiryDate){
            this.expiryDate = expiryDate;
            return this;
        }

        public Document build(){
            return new Document(this);
        }
    }
}
