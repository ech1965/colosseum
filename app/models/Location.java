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

import static com.google.common.base.Preconditions.checkNotNull;

@Entity public class Location extends RemoteModel {

    @ManyToOne(optional = false) private Cloud cloud;

    @Nullable @ManyToOne(optional = true) private GeoLocation geoLocation;

    @ManyToOne @Nullable private Location parent;

    @OneToMany(mappedBy = "parent") private List<Location> children;

    @Nullable @Column(updatable = false) @Enumerated(EnumType.STRING) private LocationScope
        locationScope;

    @Column(nullable = false, updatable = false) private Boolean isAssignable;

    @ManyToMany private List<CloudCredential> cloudCredentials;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    @ManyToMany(mappedBy = "locations", cascade = CascadeType.REMOVE) private List<Image> images;

    @ManyToMany(mappedBy = "locations", cascade = CascadeType.REMOVE) private List<Hardware>
        hardwareList;

    /**
     * Empty constructor for hibernate.
     */
    protected Location() {
    }

    public Location(Cloud cloud, String remoteId, @Nullable GeoLocation geoLocation,
        @Nullable Location parent, @Nullable LocationScope locationScope, boolean isAssignable) {
        super(remoteId);

        checkNotNull(cloud);
        this.cloud = cloud;
        this.geoLocation = geoLocation;
        this.parent = parent;
        this.locationScope = locationScope;
        this.isAssignable = isAssignable;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    @Nullable public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(@Nullable GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Nullable public Location getParent() {
        return parent;
    }

    public void setParent(@Nullable Location parent) {
        this.parent = parent;
    }

    public List<Location> getChildren() {
        return children;
    }

    public void setChildren(List<Location> children) {
        this.children = children;
    }

    @Nullable public LocationScope getLocationScope() {
        return locationScope;
    }

    public void setLocationScope(@Nullable LocationScope locationScope) {
        this.locationScope = locationScope;
    }

    public boolean isAssignable() {
        return isAssignable;
    }

    public void setIsAssignable(boolean isAssignable) {
        this.isAssignable = isAssignable;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Hardware> getHardwareList() {
        return hardwareList;
    }

    public void setHardwareList(List<Hardware> hardwareList) {
        this.hardwareList = hardwareList;
    }
}
