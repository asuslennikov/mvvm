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

import androidx.annotation.Nullable;

/**
 * Класс описывает одноразовое (возможно растянутое во времени) событие, которое необходимо
 * отобразить. Это может быть анимация, открытие клавиатуры или что-либо еще. Данное событие
 * будет выполнено лишь раз и, к примеру, при повороте экрана не будет повторено.
 * <p></p>
 * <b>Зона ответственности:</b> Описание одноразового события, которое необходимо отобразить
 */
public interface Effect {

    /**
     * Метод задает слушателя для событий выполнения эффекта (его запуска, завершения).
     * <p></p>
     * Дефолтовая реализация игнорирует переданное значение. Добавлена для минимизации
     * кода, необходимого при реализации эффекта (т.к. слушатели выполнения эффекта
     * требуются лишь в редких случаях).
     *
     * @param listener получатель событий выполнения эффекта
     */
    default void setListener(@Nullable EffectListener listener) {
        // ignore passed listener and do nothing
    }

    /**
     * Метод возвращает заданного слушателя для событий выполнения эффекта (его запуска, завершения).
     * <p></p>
     * <b>Не для всех эффектов можно однозначно определить события запуска и завершения.</b>
     * <p></p>
     * Дефолтовая реализация всегда возвращает <code>null</code>. Добавлена для минимизации
     * кода, необходимого при реализации эффекта (т.к. слушатели выполнения эффекта
     * требуются лишь в редких случаях).
     *
     * @return слушателя для событий эффекта
     */
    @Nullable
    default EffectListener getListener() {
        return null;
    }
}
