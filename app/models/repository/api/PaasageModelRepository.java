package models.repository.api;

import com.google.inject.ImplementedBy;
import models.repository.api.generic.NamedModelRepository;
import models.PaasageModel;
import models.repository.impl.PaasageModelRepositoryJpa;

/**
 * Created by daniel on 31.10.14.
 */
@ImplementedBy(PaasageModelRepositoryJpa.class)
public interface PaasageModelRepository extends NamedModelRepository<PaasageModel> {
}
