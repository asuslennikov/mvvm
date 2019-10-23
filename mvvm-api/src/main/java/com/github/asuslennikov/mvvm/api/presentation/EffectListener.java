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
 * Интерфес слушателя, позволяющий получать информацию о выполнении эффекта: его запуске, завершении.
 * <p></p>
 * <b>Не для всех эффектов можно однозначно определить события запуска и завершения.</b>
 */
public interface EffectListener {
    /**
     * Метод вызывается при старте эффекта.
     *
     * @param effect запускаемый эффект
     */
    void effectStarted(Effect effect);

    /**
     * Метод вызывается после завершения эффекта.
     *
     * @param effect завершенный эффект
     */
    void effectFinished(Effect effect);
}
