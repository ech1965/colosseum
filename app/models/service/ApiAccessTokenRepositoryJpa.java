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

package models.service;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import models.ApiAccessToken;
import models.FrontendUser;

import java.util.List;

/**
 * Created by daniel on 19.12.14.
 */
class ApiAccessTokenRepositoryJpa extends BaseModelRepositoryJpa<ApiAccessToken>
    implements ApiAccessTokenRepository {

    @Inject ApiAccessTokenRepositoryJpa(TypeLiteral<ApiAccessToken> type) {
        super(type);
    }

    @Override public List<ApiAccessToken> findByFrontendUser(final FrontendUser frontendUser) {
        //noinspection unchecked
        return em().createQuery("from ApiAccessToken aat where frontendUser = :frontendUser")
            .setParameter("frontendUser", frontendUser).getResultList();
    }
}
