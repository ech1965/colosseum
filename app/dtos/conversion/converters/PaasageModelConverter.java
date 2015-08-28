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

import dtos.PaasageModelDto;
import dtos.conversion.AbstractConverter;
import models.PaasageModel;

public class PaasageModelConverter extends AbstractConverter<PaasageModel, PaasageModelDto> {

    protected PaasageModelConverter() {
        super(PaasageModel.class, PaasageModelDto.class);
    }

    @Override public void configure() {
        binding().fromField("name").toField("name");
        binding().fromField("action").toField("action");
        binding().fromField("state").toField("state");
        binding().fromField("subState").toField("subState");
        binding().fromField("xmiModelEncoded").toField("xmiModelEncoded");
    }
}
