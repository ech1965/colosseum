package models.service;

import com.google.inject.ImplementedBy;
import models.PaasageModel;

/**
 * Created by daniel on 31.10.14.
 */
@ImplementedBy(PaasageModelRepositoryJpa.class)
public interface PaasageModelRepository extends ModelRepository<PaasageModel> {
    PaasageModel findByName(String name);
}
