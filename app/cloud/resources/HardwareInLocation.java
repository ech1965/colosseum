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

package cloud.resources;

import de.uniulm.omi.cloudiator.sword.api.domain.HardwareFlavor;

import javax.annotation.Nullable;

/**
 * Created by daniel on 28.05.15.
 */
public class HardwareInLocation extends AbstractLocationScopedResource<HardwareFlavor>
    implements HardwareFlavor {

    private final HardwareFlavor hardwareFlavor;

    public HardwareInLocation(HardwareFlavor resource, String cloud, String credential) {
        super(resource, cloud, credential);
        this.hardwareFlavor = resource;
    }

    @Override public int numberOfCores() {
        return hardwareFlavor.numberOfCores();
    }

    @Override public long mbRam() {
        return hardwareFlavor.mbRam();
    }

    @Nullable @Override public Float gbDisk() {
        return hardwareFlavor.gbDisk();
    }
}
