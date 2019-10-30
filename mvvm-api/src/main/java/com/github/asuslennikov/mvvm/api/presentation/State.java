/*
 * Copyright 2019 Suslennikov Anton
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.asuslennikov.mvvm.api.presentation;

import com.github.asuslennikov.mvvm.api.domain.UseCaseInput;
import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput;

/**
 * It describes a screen ({@link Screen}) state at specific point in time. In fact it is the main
 * source of information for presentation (the screen).
 * <p></p>
 * In most cases, an implementation is just a POJO object. It's good idea to make it immutable (so
 * only getters and builder / constructor with all fields). Any implementation of this interface
 * should never implement the {@link UseCaseInput} or {@link UseCaseOutput} interface at the same
 * time, so screen state never will be used as an input (output) for use case.
 * <p></p>
 * This class shouldn't have any logic, such as mapping, converting and so on. In order to achieve
 * ability to store (save) this class instances, it is recommended to implement the
 * {@link android.os.Parcelable} interface or apply the {@link org.parceler.Parcel} annotation:
 * <pre>
 * &#64Parcel(Parcel.Serialization.BEAN)
 * public class MyScreenState implements State {
 *     private String text;
 * }
 * </pre>
 * Successors of this interface should have fields with descriptive name, For example, instead of
 * <code>showRequestLoader</code> consider using the <code>requestLoaderVisible</code>.
 */
public interface State {
}
