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

package models.generic;

import com.google.common.collect.ImmutableList;
import models.Cloud;
import models.CloudCredential;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by daniel on 22.09.15.
 */
@Entity public abstract class RemoteResourceInCloud extends RemoteResource {

    @ManyToOne(optional = false) private Cloud cloud;
    @ManyToMany private List<CloudCredential> cloudCredentials;

    /**
     * No-args constructor for hibernate.
     */
    protected RemoteResourceInCloud() {
    }

    public RemoteResourceInCloud(Cloud cloud) {
        super(null, null);
        this.cloud = cloud;
    }

    public RemoteResourceInCloud(@Nullable String remoteId, @Nullable String cloudProviderId,
        Cloud cloud) {
        super(remoteId, cloudProviderId);
        this.cloud = cloud;
    }

    public Cloud cloud() {
        return cloud;
    }

    public List<CloudCredential> cloudCredentials() {
        return ImmutableList.copyOf(cloudCredentials);
    }

    public void addCloudCredential(CloudCredential cloudCredential) {
        this.cloudCredentials.add(cloudCredential);
    }
}
