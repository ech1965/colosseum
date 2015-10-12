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

package cloud.sync.solutions;

import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.LocationProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Location;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 04.05.15.
 */
public class CreateLocationInDatabase implements Solution {

    private final ModelService<Location> locationModelService;
    private final ModelService<Cloud> cloudModelService;

    @Inject public CreateLocationInDatabase(ModelService<Location> locationModelService,
        ModelService<Cloud> cloudModelService) {
        this.locationModelService = locationModelService;
        this.cloudModelService = cloudModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof LocationProblems.LocationNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        LocationProblems.LocationNotInDatabase locationNotInDatabase =
            (LocationProblems.LocationNotInDatabase) problem;

        Cloud cloud =
            cloudModelService.getByUuid(locationNotInDatabase.getLocationInCloud().cloud());

        String remoteId = locationNotInDatabase.getLocationInCloud().id();
        Location location =
            new Location(remoteId, locationNotInDatabase.getLocationInCloud().cloudProviderId(),
                cloud, null, null, null, locationNotInDatabase.getLocationInCloud().isAssignable());

        this.locationModelService.save(location);
    }


}
