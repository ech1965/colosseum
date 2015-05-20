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

import models.generic.Model;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;


@Entity public class HardwareOffer extends Model {

    @Column(nullable = false, updatable = false) private Integer numberOfCores;

    @Column(nullable = false, updatable = false) private Long mbOfRam;

    @Nullable private Long localDiskSpace;

    @OneToMany(mappedBy = "hardwareOffer", cascade = CascadeType.REMOVE) private List<Hardware>
        hardware;

    private HardwareOffer() {
    }

    public HardwareOffer(Integer numberOfCores, Long mbOfRam, @Nullable Long localDiskSpace) {
        this.numberOfCores = numberOfCores;
        this.mbOfRam = mbOfRam;
        this.localDiskSpace = localDiskSpace;
    }

    public Integer getNumberOfCores() {
        return numberOfCores;
    }

    public Long getMbOfRam() {
        return mbOfRam;
    }

    @Nullable public Long getLocalDiskSpace() {
        return localDiskSpace;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }
}
