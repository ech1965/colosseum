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

package models;

import models.generic.RemoteModel;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity public class Image extends RemoteModel {

    @Nullable @Column(updatable = false, nullable = true) private String name;

    @Nullable @ManyToOne(optional = true) private OperatingSystem operatingSystem;

    @ManyToOne(optional = false) private Cloud cloud;

    @ManyToMany private List<Location> locations;

    @ManyToMany private List<CloudCredential> cloudCredentials;

    @OneToMany(mappedBy = "image", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    /**
     * Empty constructor for hibernate.
     */
    protected Image() {
    }

    public Image(String remoteId, @Nullable String name, Cloud cloud,
        @Nullable OperatingSystem operatingSystem) {
        super(remoteId);
        checkNotNull(cloud);

        if (name != null) {
            checkArgument(!name.isEmpty());
        }


        this.name = name;
        this.cloud = cloud;
        this.operatingSystem = operatingSystem;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<CloudCredential> getCloudCredentials() {
        return cloudCredentials;
    }

    public void setCloudCredentials(List<CloudCredential> cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return virtualMachineTemplates;
    }

    public void setVirtualMachineTemplates(List<VirtualMachineTemplate> virtualMachineTemplates) {
        this.virtualMachineTemplates = virtualMachineTemplates;
    }

    @Nullable public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(@Nullable OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @Nullable public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
