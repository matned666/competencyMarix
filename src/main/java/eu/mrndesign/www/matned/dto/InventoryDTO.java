package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.company.Inventory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
public class InventoryDTO implements Serializable, DTOEintityDescriptionImplementation {

    public static InventoryDTO applyWithAudit(Inventory entity){
        return new InventoryDTO(
                entity.getEntityDescription() != null ? entity.getEntityDescription().getName() : null,
                entity.getEntityDescription() != null ? entity.getEntityDescription().getDescription() : null,
                entity.getPrice(),
                AuditInterface.apply(entity));
    }

    public static InventoryDTO apply(Inventory entity){
        return new InventoryDTO(
                entity.getEntityDescription() != null ? entity.getEntityDescription().getName() : null,
                entity.getEntityDescription() != null ? entity.getEntityDescription().getDescription() : null,
                entity.getPrice()
        );
    }


    private String name;
    private String description;
    private BigDecimal price;
    private AuditDTO auditDTO;



    public InventoryDTO() {
    }

    public InventoryDTO(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public InventoryDTO(String name, String description, BigDecimal price, AuditDTO audit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.auditDTO = audit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryDTO that = (InventoryDTO) o;
        return name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }

    public AuditDTO getAuditDTO() {
        return auditDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", audit=" + auditDTO +
                '}';
    }
}
