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
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.GeoLocationDto;
import dtos.conversion.ModelDtoConversionService;
import models.GeoLocation;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 09.04.15.
 */
public class GeoLocationController
    extends GenericApiController<GeoLocation, GeoLocationDto, GeoLocationDto, GeoLocationDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public GeoLocationController(ModelService<GeoLocation> modelService,
        TypeLiteral<GeoLocation> typeLiteral, ModelDtoConversionService conversionService) {
        super(modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.GeoLocationController.get(id).absoluteURL(request());
    }
}
