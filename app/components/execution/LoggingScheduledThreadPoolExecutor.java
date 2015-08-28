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

package components.execution;

import play.Logger;

import java.util.concurrent.*;

/**
 * An extension of the ScheduledThreadPoolExecutor, that logs any errors occurring during
 * the execution of tasks, instead of silently ignoring them.
 */
public class LoggingScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
    public LoggingScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public LoggingScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    public LoggingScheduledThreadPoolExecutor(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    public LoggingScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory,
        RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    @Override protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                if (((Future) r).isDone() && !((Future) r).isCancelled()) {
                    ((Future) r).get();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                t = e.getCause();
            }
        }
        if (t != null) {
            Logger.error("Uncaught exception occurred during the execution of task.", t);
        }
    }
}
