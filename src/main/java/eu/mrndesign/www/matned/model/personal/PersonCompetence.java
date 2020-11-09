package eu.mrndesign.www.matned.model.personal;

import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PersonCompetence  extends BaseEntity implements AuditInterface {

    private Integer level;

    @ManyToMany
    private List<Document> documents;

    @ManyToOne
    private Person person;
    @ManyToOne
    private Competence competence;

    public PersonCompetence() {
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    @Override
    public String toString() {
        return "PersonCompetence{" +
                "level=" + level +
                ", documents=" + documents +
                ", person=" + person +
                ", competence=" + competence +
                '}';
    }
}
