
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
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import java.util.List;
import java.util.ArrayList;


public class PaasageModelDto extends NamedDto {

    public PaasageModelDto() {
        super();
    }

    public PaasageModelDto(String name) {
        super(name);
    }

    public String state;
    public String subState;
    public String action;

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        if(this.state.isEmpty()){
            errors.add(new ValidationError("state", "State must not be empty"));
        }

        if(this.subState.isEmpty()){
            errors.add(new ValidationError("subState", "SubState must not be empty"));
        }

        if(this.action.isEmpty()){
            errors.add(new ValidationError("action", "Action must not be empty"));
        }
        return errors;
    }
}

