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

package models.service.api;

import com.google.inject.ImplementedBy;
import models.Cloud;
import models.CloudHardware;
import models.Hardware;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.CloudHardwareFlavorService;

/**
 * Created by daniel on 03.11.14.
 */
@ImplementedBy(CloudHardwareFlavorService.class)
public interface CloudHardwareFlavorServiceInterface extends ModelServiceInterface<CloudHardware>{

    public CloudHardware getByCloudAndHardwareFlavor(Cloud cloud, Hardware hardware);


}
