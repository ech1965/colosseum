
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
import dtos.generic.impl.NamedDto;
import models.PaasageModel;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import java.util.List;
import java.util.ArrayList;



public class PaasageModelDto extends NamedDto {

    public PaasageModelDto() {
        super();
    }

    public PaasageModelDto(String name, PaasageModel.State state, String subState, PaasageModel.Action action) {
        super(name);
        this.state    = state.toString();
        this.subState = subState;
        this.action   = action.toString();
    }

    public String state;
    public String subState;
    public String action;

    public PaasageModel.State getState()
    {
        return PaasageModel.State.fromString(state);
    }

    public PaasageModel.Action getAction()
    {
        return PaasageModel.Action.fromString(action);
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        validateState(errors);
        validateAction(errors);
        validateSubState(errors);
        return errors;
    }

    private void validateSubState(List<ValidationError> errors) {
        if(this.subState == null){
            errors.add(new ValidationError("subState", "SubState must not be null"));
            return;
        }
        if (this.subState.isEmpty()) {
            errors.add(new ValidationError("subState", "SubState must not be empty"));
        }

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

