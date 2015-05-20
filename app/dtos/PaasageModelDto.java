
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
import models.PaasageModel;
import play.data.validation.ValidationError;
import java.util.List;
import java.util.ArrayList;



public class PaasageModelDto extends ValidatableDto {

    public PaasageModelDto() {
        super();
    }

    public PaasageModelDto(String name, PaasageModel.State state, String subState, PaasageModel.Action action, String xmiModelEncoded) {
        super();
        this.name     = name;
        this.state    = state.toString();
        this.subState = subState;
        this.action   = action.toString();
        this.xmiModelEncoded = xmiModelEncoded;
    }

    public String name;
    public String state;
    public String subState;
    public String action;
    public String xmiModelEncoded;

    public PaasageModel.State getState()
    {
        return PaasageModel.State.fromString(state);
    }

    public PaasageModel.Action getAction()
    {
        return PaasageModel.Action.fromString(action);
    }

    @Override public void validation() {
        validator(String.class).validate(state).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(xmiModelEncoded).withValidator(new NotNullOrEmptyValidator());
        //TODO: Create a validator to validate string compliant with enum
    }

    private List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();
        validateState(errors);
        validateAction(errors);
        return errors;
    }

    private void validateState(List<ValidationError> errors)
    {
        if(this.state == null){
            errors.add(new ValidationError("state", "State must not be null"));
            return;
        }
        try {
            PaasageModel.State unused = PaasageModel.State.fromString(state);

        } catch(IllegalArgumentException ex) {
            errors.add(new ValidationError("state", "State does not map to a valid enum value"));
        }
    }
    private void validateAction(List<ValidationError> errors)
    {
        if(this.action == null){
            errors.add(new ValidationError("action", "Action must not be null"));
            return;
        }
        try {
            PaasageModel.Action unused = PaasageModel.Action.fromString(action);
        } catch(IllegalArgumentException ex) {
            errors.add(new ValidationError("action", "Action does not map to a valid enum value"));
        }
    }
}

