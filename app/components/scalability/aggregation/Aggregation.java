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

package components.scalability.aggregation;

import components.execution.Prioritized;
import components.scalability.AggregationAccessService;
import de.uniulm.omi.cloudiator.axe.aggregator.AggregatorService;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.AggregatorServiceAccess;
import models.Monitor;

/**
 * Created by Frank on 30.07.2015.
 */
public interface Aggregation<T extends Monitor> extends Prioritized {
    T getObject();
    void execute(AggregatorServiceAccess service);
}
