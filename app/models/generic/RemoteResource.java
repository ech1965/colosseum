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

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.05.15.
 */
@Entity @Inheritance(strategy = javax.persistence.InheritanceType.TABLE_PER_CLASS)
public abstract class RemoteResource extends Model {

    private RemoteState remoteState;
    @Nullable @Column(unique = true, nullable = true) private String remoteId;
    @Nullable @Column(nullable = true) private String cloudProviderId;

    public RemoteState getRemoteState() {
        return remoteState;
    }

    public void setRemoteState(RemoteState remoteState) {
        this.remoteState = remoteState;
    }

    public Optional<String> remoteId() {
        return Optional.ofNullable(remoteId);
    }

    public void bindRemoteId(String remoteId) {
        checkNotNull(remoteId, "Binding null remoteId is not allowed.");
        if (this.remoteId != null) {
            throw new IllegalStateException("RemoteId was already bound.");
        }
        this.remoteId = remoteId;
    }

    public RemoteResource() {

    }

    public RemoteResource(@Nullable String remoteId, @Nullable String cloudProviderId) {
        this.remoteId = remoteId;
        this.cloudProviderId = cloudProviderId;
    }

    public Optional<String> cloudProviderId() {
        return Optional.ofNullable(cloudProviderId);
    }

    public void bindCloudProviderId(String cloudProviderId) {
        checkNotNull(cloudProviderId, "Binding null cloudProviderId is not allowed");
        if (this.cloudProviderId != null) {
            throw new IllegalStateException("Changing the cloudProviderId is not allowed.");
        }
        this.cloudProviderId = cloudProviderId;
    }
}
