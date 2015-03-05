
/**
 * Created by ec on 9/02/15.
 */

/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import controllers.security.SecuredToken;
import dtos.PaasageModelDto;
import dtos.convert.api.ModelDtoConversionService;
import models.PaasageModel;
import models.service.api.PaasageModelServiceInterface;
import play.mvc.Security;


/**
 * Implementation of the GenericApiController for the PaaSageModel model class.
 *
 * @author Etienne Charlier
 */
@Security.Authenticated(SecuredToken.class)
public class PaasageModelController extends GenericApiController<PaasageModel, PaasageModelDto> {

    /**
     * Constructor.
     *
     * @param PaasageModelService       Model service for PaasageModel model.
     * @param conversionService         Model <-> DTO conversion service.
     */
    @Inject
    protected PaasageModelController(PaasageModelServiceInterface PaasageModelService, ModelDtoConversionService conversionService) {
        super(PaasageModelService, conversionService);
    }


    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.PaasageModelController.get(id).absoluteURL(request());
    }

    @Override
    protected BeforeAfterResult beforeUpdate(PaasageModel current, PaasageModelDto wanted)
    {

        return BeforeAfterResult.CONTINUE;
    }
}
