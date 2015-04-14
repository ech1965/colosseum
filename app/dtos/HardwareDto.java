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

package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullOrEmptyValidator;
import dtos.validation.NotNullValidator;
import models.Cloud;
import models.HardwareOffer;
import models.service.impl.generic.BaseModelService;

public class HardwareDto extends ValidatableDto {

    private Long cloud;
    private Long hardwareOffer;
    private String cloudUuid;

    public HardwareDto() {
        super();
    }

    @Override public void validation() {
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(hardwareOffer).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.hardwareOfferService.get()));
        validator(String.class).validate(cloudUuid).withValidator(new NotNullOrEmptyValidator());
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getHardwareOffer() {
        return hardwareOffer;
    }

    public void setHardwareOffer(Long hardwareOffer) {
        this.hardwareOffer = hardwareOffer;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
    }

    public static class References {
        @Inject public static Provider<BaseModelService<Cloud>> cloudService;
        @Inject public static Provider<BaseModelService<HardwareOffer>> hardwareOfferService;
    }
}
