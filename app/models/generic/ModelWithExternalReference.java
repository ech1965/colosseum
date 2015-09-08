package models.generic;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ModelWithExternalReference extends Model {

    @OneToMany(cascade = CascadeType.ALL) private List<ExternalReference>
        externalReferences;

    public List<ExternalReference> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(List<ExternalReference> externalReferences) {
        this.externalReferences = externalReferences;
    }
}
