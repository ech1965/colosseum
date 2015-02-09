package models.service.api;

import com.google.inject.ImplementedBy;
import models.PaasageModel;
import models.service.api.generic.NamedModelServiceInterface;
import models.service.impl.PaasageModelService;

/**
 * Created by ec on 9/02/15.
 */
@ImplementedBy(PaasageModelService.class)
public interface PaasageModelServiceInterface extends NamedModelServiceInterface<PaasageModel> {

}