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

package dtos.conversion;

import com.google.inject.TypeLiteral;
import dtos.api.Dto;
import models.generic.Model;

/**
 * Created by daniel on 16.03.15.
 */
public abstract class AbstractConverter<T extends Model, S extends Dto>
    implements DtoConverter<T, S> {

    private final ConversionBindings fieldBindings;
    private final Class<T> tType;
    private final Class<S> sType;
    private boolean configured = false;

    protected AbstractConverter(Class<T> t, Class<S> s) {
        fieldBindings = new ConversionBindings();
        tType = t;
        sType = s;
    }

    protected final <U, V> FromBindingBuilder<U, V> binding(Class<U> uClass, Class<V> vClass) {
        return fieldBindings.builder(uClass, vClass);
    }

    protected final FromBindingBuilder<Object, Object> binding() {
        return fieldBindings.builder(Object.class, Object.class);
    }

    protected final <U, V> FromBindingBuilder<U, V> binding(TypeLiteral<U> uTypeLiteral,
        TypeLiteral<V> vTypeLiteral) {
        //noinspection unchecked
        return fieldBindings
            .builder((Class<U>) uTypeLiteral.getRawType(), (Class<V>) vTypeLiteral.getRawType());
    }

    private synchronized void configureOnce() {
        if (!configured) {
            configure();
            configured = true;
        }
    }

    public abstract void configure();

    @Override public final T toModel(S dto) {
        configureOnce();
        T model = new TypeBuilder<T>().getInstance(tType);
        this.fieldBindings.bind(dto, model);
        return model;
    }

    @Override public final T toModel(S dto, T model) {
        configureOnce();
        this.fieldBindings.bind(dto, model);
        return model;
    }

    @Override public final S toDto(T model) {
        configureOnce();
        S dto = new TypeBuilder<S>().getInstance(sType);
        this.fieldBindings.bindReverse(dto, model);
        return dto;
    }

    @Override public final S toDto(T model, S dto) {
        configureOnce();
        this.fieldBindings.bindReverse(dto, model);
        return dto;
    }



}
