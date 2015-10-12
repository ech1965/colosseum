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

package cloud.sync;

import cloud.CloudService;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.Schedulable;
import components.execution.SimpleBlockingQueue;
import play.db.jpa.Transactional;

import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;



/**
 * Created by daniel on 27.04.15.
 */
public abstract class AbstractCloudServiceWatchdog implements Schedulable {

    private final CloudService cloudService;
    private final SimpleBlockingQueue<Problem> problemQueue;

    @Inject protected AbstractCloudServiceWatchdog(CloudService cloudService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue) {
        checkNotNull(cloudService);
        checkNotNull(simpleBlockingQueue);
        this.cloudService = cloudService;
        this.problemQueue = simpleBlockingQueue;
    }

    @Transactional(readOnly = true) @Override public void run() {
        watch(this.cloudService);
    }

    protected abstract void watch(CloudService cloudService);

    protected void report(Problem problem) {
        this.problemQueue.add(problem);
    }

    @Override public long period() {
        return 20;
    }

    @Override public long delay() {
        return 0;
    }

    @Override public TimeUnit timeUnit() {
        return TimeUnit.SECONDS;
    }
}
