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

package dtos.conversion.converters;

import com.google.inject.Inject;
import dtos.HardwareDto;
import dtos.conversion.RemoteConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.ModelToListIdTransformer;
import models.*;
import models.service.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class HardwareConverter extends RemoteConverter<Hardware, HardwareDto> {

    private final ModelService<HardwareOffer> hardwareOfferModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<Location> locationModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject protected HardwareConverter(ModelService<HardwareOffer> hardwareOfferModelService,
        ModelService<Cloud> cloudModelService, ModelService<Location> locationModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(Hardware.class, HardwareDto.class);
        this.hardwareOfferModelService = hardwareOfferModelService;
        this.cloudModelService = cloudModelService;
        this.locationModelService = locationModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public void configure() {

        super.configure();

        builder().from(Long.class, "hardwareOffer").to(HardwareOffer.class, "hardwareOffer")
            .withTransformation(new IdToModelTransformer<>(hardwareOfferModelService));
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        builder().from("locations").to("locations").withUnsafeTransformation(
            new ModelToListIdTransformer<>(new IdToModelTransformer<>(locationModelService)));
        builder().from("cloudCredentials").to("cloudCredentials").withUnsafeTransformation(
            new ModelToListIdTransformer<>(
                new IdToModelTransformer<>(cloudCredentialModelService)));
    }
}
