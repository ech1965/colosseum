/*
 * Copyright (c) 2015 University of Ulm
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
import dtos.CloudImageDto;
import dtos.convert.api.ModelDtoConversionService;
import models.CloudImage;
import models.service.api.CloudImageServiceInterface;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class CloudImageController extends GenericApiController<CloudImage, CloudImageDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param cloudImageService the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected CloudImageController(CloudImageServiceInterface cloudImageService, ModelDtoConversionService conversionService) {
        super(cloudImageService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.CloudImageController.get(id).absoluteURL(request());
    }
}
