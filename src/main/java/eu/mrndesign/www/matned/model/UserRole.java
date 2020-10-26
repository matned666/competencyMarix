package eu.mrndesign.www.matned.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserRole  extends BaseEntity {



    private String roleName;

    public UserRole() {
    }

    public UserRole(Role roleName){
        this.roleName = "ROLE_"+roleName.name();
    }

    public static UserRole apply(Role roleName){
        return new UserRole(roleName);
    }

    public String getRoleName() {
        return roleName;
    }

    public enum Role {
        ADMIN,
        CEO,
        MANAGER,
        PUBLISHER,
        USER,
        BANNED;

        public String roleName(){
            return "ROLE_" + this.name();
        }
    }
}
