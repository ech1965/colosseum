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

package dtos.validation.generic;

import dtos.validation.api.ReferenceValidator;
import dtos.validation.api.ValidationException;
import dtos.validation.api.Validator;

import javax.annotation.Nullable;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 20.03.15.
 */
public class GenericReferenceValidator<T> implements ReferenceValidator {

    private final Validator<T> validator;
    @Nullable private final T t;

    public GenericReferenceValidator(@Nullable T t, Validator<T> validator) {
        checkNotNull(validator);
        this.validator = validator;
        this.t = t;
    }

    @Override public Collection<ValidationError> validate() throws ValidationException {
        return this.validator.validate(t);
    }
}
