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

import com.github.asuslennikov.mvvm.api.presentation.State;

/**
 * Маркерный интерфейс, описывающий данные, необходимые для работы бизнес-сценария.
 * <p></p>
 * <b>Зона ответственности:</b> Представление входных данных для {@link UseCase}.
 * <p></p>
 * Реализация не должна одновременно имплементимровать интерфейс {@link State}.
 */
public interface UseCaseInput {
}
