
/**
 * Copyright (c) 2014-2015 CETIC ASBL
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
import dtos.validation.EnumRepresentationValidator;
import dtos.validation.NotNullValidator;
import models.PaasageModel;



public class PaasageModelDto extends ValidatableDto {

    public PaasageModelDto() {
        super();
    }

    public PaasageModelDto(String name, PaasageModel.State state, String subState, PaasageModel.Action action, String xmiModelEncoded) {
        super();
        this.name     = name;
        this.state    = state;
        this.subState = subState;
        this.action   = action;
        this.xmiModelEncoded = xmiModelEncoded;
    }

    public String name;
    public PaasageModel.State state;
    public String subState;
    public PaasageModel.Action action;
    public String xmiModelEncoded;

    public PaasageModel.State getState()
    {
        return state;
    }

    public PaasageModel.Action getAction()
    {
        return action;
    }

    @Override public void validation() {
        validator(PaasageModel.Action.class).validate(action).withValidator(new NotNullValidator());
        validator(PaasageModel.State.class).validate(state).withValidator(new NotNullValidator());
        validator(String.class).validate(xmiModelEncoded).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(subState).withValidator(new NotNullOrEmptyValidator());
    }
}

