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

package cloud;

import de.uniulm.omi.cloudiator.sword.api.logging.Logger;
import de.uniulm.omi.cloudiator.sword.api.logging.LoggerFactory;
import de.uniulm.omi.cloudiator.sword.core.logging.AbstractLoggingModule;

/**
 * Created by daniel on 29.07.15.
 */
public class SwordLoggingModule extends AbstractLoggingModule {

    @Override protected LoggerFactory getLoggerFactory() {
        return SwordColosseumLogger::new;
    }

    private static class SwordColosseumLogger implements Logger {

        private final play.Logger.ALogger Logger;

        public SwordColosseumLogger(String s) {
            this.Logger = play.Logger.of(s);
        }

        @Override public boolean isTraceEnabled() {
            return Logger.isTraceEnabled();
        }

        @Override public boolean isDebugEnabled() {
            return Logger.isDebugEnabled();
        }

        @Override public boolean isInfoEnabled() {
            return Logger.isInfoEnabled();
        }

        @Override public boolean isWarnEnabled() {
            return Logger.isWarnEnabled();
        }

        @Override public boolean isErrorEnabled() {
            return Logger.isErrorEnabled();
        }

        @Override public boolean isFatalEnabled() {
            return Logger.isErrorEnabled();
        }

        @Override public void trace(String s) {
            Logger.trace(s);
        }

        @Override public void debug(String s) {
            Logger.debug(s);
        }

        @Override public void info(String s) {
            Logger.info(s);
        }

        @Override public void warn(String s) {
            Logger.warn(s);
        }

        @Override public void warn(String s, Throwable throwable) {
            Logger.warn(s, throwable);
        }

        @Override public void error(String s) {
            Logger.error(s);
        }

        @Override public void error(String s, Throwable throwable) {
            Logger.error(s, throwable);
        }

        @Override public void fatal(String s) {
            Logger.error(s);
        }

        @Override public void fatal(String s, Throwable throwable) {
            Logger.error(s, throwable);
        }
    }
}
