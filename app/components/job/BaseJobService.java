/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package components.job;

import cloud.CloudService;
import cloud.colosseum.ColosseumComputeService;
import cloud.strategies.KeyPairStrategy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import models.Instance;
import models.Tenant;
import models.VirtualMachine;
import models.service.ModelService;

/**
 * Created by daniel on 03.07.15.
 */
@Singleton public class BaseJobService implements JobService {

    private final ModelService<VirtualMachine> virtualMachineModelService;
    private final ModelService<Tenant> tenantModelService;
    private final ModelService<Instance> instanceModelService;
    private final ColosseumComputeService colosseumComputeService;
    private final SimpleBlockingQueue<Job> jobQueue;
    private final KeyPairStrategy keyPairStrategy;

    @Inject public BaseJobService(ModelService<VirtualMachine> virtualMachineModelService,
        CloudService cloudService, ModelService<Tenant> tenantModelService,
        ModelService<Instance> instanceModelService,
        @Named("jobQueue") SimpleBlockingQueue<Job> jobQueue, KeyPairStrategy keyPairStrategy) {
        this.virtualMachineModelService = virtualMachineModelService;
        this.tenantModelService = tenantModelService;
        this.instanceModelService = instanceModelService;
        this.keyPairStrategy = keyPairStrategy;
        this.colosseumComputeService = cloudService.computeService();
        this.jobQueue = jobQueue;
    }

    @Override public void newVirtualMachineJob(VirtualMachine virtualMachine, Tenant tenant) {
        this.jobQueue.add(new CreateVirtualMachineJob(virtualMachine, virtualMachineModelService,
            tenantModelService, colosseumComputeService, tenant, keyPairStrategy));
    }

    @Override public void newInstanceJob(Instance instance, Tenant tenant) {
        this.jobQueue.add(new CreateInstanceJob(instance, instanceModelService, tenantModelService,
            colosseumComputeService, tenant));
    }

}
