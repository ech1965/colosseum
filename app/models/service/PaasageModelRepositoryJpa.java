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
import models.PaasageModel;
import models.service.PaasageModelRepository;
import models.service.BaseModelRepositoryJpa;

import javax.persistence.Query;
import static models.service.util.JpaResultHelper.*;
/**
 * Created by etienne on 01.02.15.
 */
public class PaasageModelRepositoryJpa extends BaseModelRepositoryJpa<PaasageModel> implements PaasageModelRepository {
    @Override
    public PaasageModel findByName(String name) {
        String queryString = String.format("from %s where name = :name", type.getName());
        Query query = em().createQuery(queryString).setParameter("name",name);
        //noinspection unchecked
        return (PaasageModel) getSingleResultOrNull(query);

    }
    @Inject
    public PaasageModelRepositoryJpa(TypeLiteral<PaasageModel> type) {
        super(type);
    }
}
