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
import dtos.generic.NamedDto;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullValidator;
import models.OperatingSystem;
import models.service.impl.generic.BaseModelService;

public class ImageOfferDto extends NamedDto {

    protected Long operatingSystem;

    public ImageOfferDto() {
        super();
    }

    public ImageOfferDto(String name, Long operatingSystem) {
        super(name);
        this.operatingSystem = operatingSystem;
    }

    @Override public void validation() {
        super.validation();
        validator(Long.class).validate(operatingSystem).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.operatingSystemService.get()));
    }

    public Long getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(Long operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public static class References {
        @Inject public static Provider<BaseModelService<OperatingSystem>> operatingSystemService;
    }
}
