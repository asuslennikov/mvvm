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


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Интерфейс для взаимодейтсвия с экраном.
 * <p></p>
 * <b>Зона ответственности:</b> Отрисовка UI компонента для конкретного {@link State} и применение {@link Effect}.
 * <p></p>
 * Реализация данного класса:
 * <ul>
 * <li>осуществляют всю работу по отрисовке</li>
 * <li>отвечает за загрузку ресурсов, необходимых для отрисовки (картинок, строк)</li>
 * <li>никогда самостоятельно не изменяет {@link State} (это зона ответственности {@link ViewModel})</li>
 * <li>не содержит логики поведения. Максимум - делегация вызовов {@link ViewModel}</li>
 * </ul>
 *
 * @param <STATE> Класс, описывающий состояние данного экрана (см. {@link State})
 */
public interface Screen<STATE extends State> {

    /**
     * Метод позволяет вернуть текущее сохраненное состояние экрана. Например, восстановить после пересоздания activity.
     * Как правило используется для начальной инициализации {@link ViewModel}.
     *
     * @return текущее сохраненное состояние экрана, может быть {@code null}
     */
    @Nullable
    STATE getSavedState();

    /**
     * Запускает перерисовку экрана в соответствии с переданным состоянием.
     * Вызов метода должен осуществляться из {@code main} потока, т.к. в методе может происходить
     * взаимодействие с иерархией {@link android.view.View}.
     *
     * @param screenState состояние экрана, которое необходимо отрисовать
     */
    void render(@NonNull STATE screenState);

    /**
     * Применяет указанный эффект к экрану. Как правило эффект - это некое событие, которое нет
     * необходимости хранить в состоянии экрана, например запуск анимации, открытие клавиатуры и т.д.
     *
     * @param screenEffect определенный эффект, о котором известно данному экрану
     */
    void applyEffect(@NonNull Effect screenEffect);

}
