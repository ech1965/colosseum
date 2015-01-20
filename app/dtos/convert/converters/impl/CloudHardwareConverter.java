/*
 * Copyright (c) 2015 University of Ulm
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

package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.CloudHardwareDto;
import dtos.builders.CloudHardwareDtoBuilder;
import models.Cloud;
import models.CloudHardware;
import models.Hardware;
import models.service.impl.CloudService;
import models.service.impl.HardwareService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 09.12.2014.
 */
public class CloudHardwareConverter extends BaseConverter<CloudHardware, CloudHardwareDto> {

    private final CloudService cloudService;
    private final HardwareService hardwareService;

    @Inject
    CloudHardwareConverter(CloudService cloudService, HardwareService hardwareService) {

        checkNotNull(cloudService);
        checkNotNull(hardwareService);


        this.cloudService = cloudService;
        this.hardwareService = hardwareService;
    }

    protected CloudHardware setDto(CloudHardware cloudHardware, CloudHardwareDto cloudHardwareDto) {
        cloudHardware.setCloudUuid(cloudHardwareDto.cloudUuid);

        Cloud cloud = cloudService.getById(cloudHardwareDto.cloud);
        checkState(cloud != null, "Could not retrieve cloud for id: " + cloudHardwareDto.cloud);
        cloudHardware.setCloud(cloud);

        Hardware hardware = hardwareService.getById(cloudHardwareDto.hardware);
        checkState(hardware != null, "Could not retrieve hardware for id: " + cloudHardwareDto.hardware);
        cloudHardware.setHardware(hardware);

        return cloudHardware;
    }

    @Override
    public CloudHardware toModel(CloudHardwareDto dto) {
        checkNotNull(dto);
        return this.setDto(new CloudHardware(), dto);
    }

    @Override
    public CloudHardware toModel(CloudHardwareDto dto, CloudHardware model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public CloudHardwareDto toDto(CloudHardware model) {
        checkNotNull(model);
        return new CloudHardwareDtoBuilder()
                .cloudUuid(model.getCloudUuid())
                .cloud(model.getCloud().getId())
                .hardware(model.getHardware().getId())
                .build();
    }
}
