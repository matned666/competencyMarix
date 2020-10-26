package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.validation.UniqueEmail;
import eu.mrndesign.www.matned.model.AuditInterface;
import eu.mrndesign.www.matned.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    public static UserDTO applyWithOutAudit(User entity) {
        return new UserDTO(entity.getLogin());
    }

    public static UserDTO apply(User entity){
        return new UserDTO(entity.getLogin(), AuditInterface.apply(entity));
    }

    @UniqueEmail(message = "It should be a unique email")
    @NotEmpty(message = "The value cannot be empty")
    @Size(min = 5, message = "The login must be at least {min} signs long")
    @Pattern(
            regexp = ".{1,}@.{1,}[.].{2,3}",
            message = "It should be a valid email address"
    )
    private String login;
    private AuditDTO auditDTO;

    public UserDTO() {
    }

    public UserDTO(String login, AuditDTO auditDTO) {
        this.login = login;
        this.auditDTO = auditDTO;
    }

    public UserDTO(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public AuditDTO getAuditDTO() {
        return auditDTO;
    }

    public void setAuditDTO(AuditDTO auditDTO) {
        this.auditDTO = auditDTO;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "login='" + login + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }
}
