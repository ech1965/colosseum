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

import dtos.generic.ValidatableDto;
import dtos.validation.NotNullOrEmptyValidator;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class ApiDto extends ValidatableDto {

    private String internalProviderName;
    private String name;

    public ApiDto() {
        super();
    }

    public ApiDto(String name) {
        this.name = name;
    }

    public String getInternalProviderName() {
        return internalProviderName;
    }

    public void setInternalProviderName(String internalProviderName) {
        this.internalProviderName = internalProviderName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public void validation() {
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(this.internalProviderName)
            .withValidator(new NotNullOrEmptyValidator());
    }
}
