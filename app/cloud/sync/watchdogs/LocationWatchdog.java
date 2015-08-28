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

package cloud.sync.watchdogs;

import cloud.CloudService;
import cloud.resources.LocationInCloud;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import cloud.sync.problems.LocationProblems;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import components.execution.Stable;
import models.CloudCredential;
import models.Location;
import models.service.LocationModelService;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 05.05.15.
 */
@Stable public class LocationWatchdog extends AbstractCloudServiceWatchdog {

    private final LocationModelService locationModelService;

    @Inject protected LocationWatchdog(CloudService cloudService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue,
        LocationModelService locationModelService) {
        super(cloudService, simpleBlockingQueue);
        this.locationModelService = locationModelService;
    }



    @Override protected void watch(CloudService cloudService) {

        for (LocationInCloud location : cloudService.getDiscoveryService().listLocations()) {
            Location modelLocation = locationModelService.getByRemoteId(location.id());

            if (modelLocation == null) {
                this.report(new LocationProblems.LocationNotInDatabase(location));
            } else {
                CloudCredential credentialToSearchFor = null;
                for (CloudCredential cloudCredential : modelLocation.getCloudCredentials()) {
                    if (cloudCredential.getUuid().equals(location.credential())) {
                        credentialToSearchFor = cloudCredential;
                        break;
                    }
                }

                if (credentialToSearchFor == null) {
                    this.report(new LocationProblems.LocationMissesCredential(location));
                }
            }

        }
    }

    @Override public long period() {
        return 1;
    }

    @Override public long delay() {
        return 0;
    }

    @Override public TimeUnit timeUnit() {
        return TimeUnit.MINUTES;
    }
}
