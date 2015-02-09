package models.service.impl;

import com.google.inject.Inject;
import models.PaasageModel;
import models.repository.api.PaasageModelRepository;
import models.service.api.PaasageModelServiceInterface;
import models.service.impl.generic.NamedModelService;

/**
 * Created by ec on 9/02/15.
 */
public class PaasageModelService extends NamedModelService<PaasageModel> implements PaasageModelServiceInterface  {

    @Inject
    public PaasageModelService(PaasageModelRepository paasageModelRepository) {
        super(paasageModelRepository);
    }
}
