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

package models.service;

import com.google.inject.Inject;
import models.Cloud;
import models.KeyPair;
import models.Tenant;

import javax.annotation.Nullable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 28.08.15.
 */
public class DefaultKeyPairModelService extends BaseModelService<KeyPair>
    implements KeyPairModelService {

    @Inject public DefaultKeyPairModelService(ModelRepository<KeyPair> modelRepository) {
        super(modelRepository);
    }

    @Nullable @Override public Optional<KeyPair> getKeyPair(Cloud cloud, Tenant tenant) {

        checkNotNull(cloud);
        checkNotNull(tenant);

        for (KeyPair keyPair : getAll()) {
            if (keyPair.getCloud().equals(cloud) && keyPair.getTenant().equals(tenant)) {
                return Optional.of(keyPair);
            }
        }
        return Optional.empty();
    }
}
