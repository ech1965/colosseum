package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.ApplicationDto;
import dtos.conversion.ModelDtoConversionService;
import models.Application;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class ApplicationController
    extends GenericApiController<Application, ApplicationDto, ApplicationDto, ApplicationDto> {

    @Inject public ApplicationController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<Application> modelService,
        TypeLiteral<Application> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ApplicationController.get(id).absoluteURL(request());
    }
}
