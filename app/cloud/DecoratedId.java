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

import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 06.07.15.
 */
public class DecoratedId {

    private final static String SEPARATOR = "/";

    private final String cloud;
    @Nullable private final String location;
    private final String id;


    private DecoratedId(String cloud, @Nullable String location, String id) {
        this.cloud = cloud;
        this.location = location;
        this.id = id;
    }

    static public DecoratedId of(String cloud, String id) {
        return new DecoratedId(cloud, null, id);
    }

    static public DecoratedId of(String cloud, String location, String id) {
        return new DecoratedId(cloud, location, id);
    }

    static public DecoratedId of(String id) {
        checkNotNull(id);
        final String[] split = id.split(SEPARATOR);
        switch (split.length) {
            case 2:
                return new DecoratedId(split[0], null, split[1]);
            case 3:
                return new DecoratedId(split[0], split[1], split[2]);
            default:
                throw new IllegalArgumentException();
        }
    }

    public String colosseumLocation() {
        if (location != null) {
            return cloud + SEPARATOR + location;
        } else {
            return cloud;
        }
    }

    public String colosseumId() {
        return colosseumLocation() + SEPARATOR + id;
    }

    public String swordId() {
        return IdScopeByLocations.from(location, id).getIdWithLocation();
    }



}
