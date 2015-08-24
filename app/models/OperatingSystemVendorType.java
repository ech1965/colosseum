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

import de.uniulm.omi.cloudiator.sword.api.domain.OSFamily;

/**
 * Created by daniel on 04.11.14.
 */
public enum OperatingSystemVendorType {

    NIX("*nix", true, OSFamily.UNIX, 22),
    WINDOWS("windows", false, OSFamily.WINDOWS, 5985);

    private final String text;
    private final boolean supportsKeyPair;
    private final OSFamily osFamily;
    private final int port;

    OperatingSystemVendorType(String text, boolean supportsKeyPair, OSFamily osFamily, int port) {
        this.text = text;
        this.supportsKeyPair = supportsKeyPair;
        this.osFamily = osFamily;
        this.port = port;
    }

    @Override public String toString() {
        return text;
    }

    public boolean supportsKeyPair() {
        return supportsKeyPair;
    }

    public OSFamily osFamily() {
        return osFamily;
    }

    public int port() {
        return port;
    }

}
