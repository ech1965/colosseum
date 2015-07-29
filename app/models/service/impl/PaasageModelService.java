package models.service.impl;

import com.google.inject.Inject;
import models.PaasageModel;
import models.service.PaasageModelRepository;
import models.service.api.PaasageModelServiceInterface;
import models.service.BaseModelService;

/**
 * Created by ec on 9/02/15.
 */
public class PaasageModelService extends BaseModelService<PaasageModel> implements PaasageModelServiceInterface  {

    @Inject
    public PaasageModelService(PaasageModelRepository paasageModelRepository) {
        super(paasageModelRepository);
    }
}
