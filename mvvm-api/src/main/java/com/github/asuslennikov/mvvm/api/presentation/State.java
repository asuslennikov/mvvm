/**
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

/**
 * Маркерный интерфейс, означающий что класс описывает состояние экрана ({@link Screen}) в
 * какой-либо момент времени.
 * <p></p>
 * <b>Зона ответственности:</b> Хранение информации о текущем состоянии UI компонента (Screen).
 * <p></p>
 * Класс, реализующий данный интерфейс, обычно представляет собой POJO и не содержит никакой
 * логики. Для удобства сохранения объектов данного класса (иначе говоря, состояний экрана),
 * рекомендуется реализовать интерфейс {@link android.os.Parcelable} или воспользоваться
 * аннотацией {@link org.parceler.Parcel}:
 * <pre>
 * &#64Parcel(Parcel.Serialization.BEAN)
 * public class MyScreenState implements State {
 *     private String text;
 * }
 * </pre>
 * Рекомендуется давать описательные названия для полей класса. Например, вместо
 * <code>showRequestLoader</code> использовать <code>requestLoaderVisible</code>.
 */
public interface State {
}
