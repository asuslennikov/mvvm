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
package com.github.asuslennikov.mvvm.api.domain;


import androidx.annotation.NonNull;

import io.reactivex.Observable;

/**
 * Контракт сценария бизнес-логики.
 * <p></p>
 * <b>Зона ответственности:</b> Содержит бизнес-логику приложения (обработка данных по определенным правилам).
 * <p></p>
 * Предполагается, что реализации содержат в себе бизнес-правила и логику работы приложения,
 * взаимодействуют с data-слоем приложения (менеджерами).
 *
 * @param <IN>  Представление входных данных
 * @param <OUT> Представление результата работы
 */
public interface UseCase<IN extends UseCaseInput, OUT extends UseCaseOutput> {
    /**
     * Запускает бизнес-сценарий (выполняет какую-либо команду). Во время работы данного метода
     * изменяются данные, выполняются запросы и т.д. Поток выполнения определяется самим сценарием.
     * Может занимать продолжительное время (зависит от реализации).
     * <p></p>
     * В большинстве реализаций, выполнение сценария начнется только после регистрации слушателя
     * для результата работы.
     *
     * @param useCaseInput входные данные, используемые внутри сценария
     * @return объект для регистрации получателя результата работы (а также промежуточных данных)
     */
    Observable<OUT> execute(@NonNull IN useCaseInput);
}
