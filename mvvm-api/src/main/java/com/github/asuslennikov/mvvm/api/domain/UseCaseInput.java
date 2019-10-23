/**
 * Copyright 2019 Suslennikov Anton
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.asuslennikov.mvvm.api.domain;

import com.github.asuslennikov.mvvm.api.presentation.State;

/**
 * It describes input data, which is needed for use case ({@link UseCase}) during its work. There is
 * no way to predict which data will be required, so it is just a marker.
 * <p></p>
 * In most cases, an implementation is just a POJO object. It's good idea to make it immutable (so
 * only getters and builder / constructor with all fields). Any implementation of this interface
 * should never implement the {@link State} interface at the same time, so screen state never will
 * be used as an input for use case.
 */
public interface UseCaseInput {
}
