package com.github.asuslennikov.mvvm.api.domain;

import com.github.asuslennikov.mvvm.api.presentation.State;

/**
 * Маркерный интерфейс, описывающий результат работы бизнес-сценария.
 * <p></p>
 * <b>Зона ответственности:</b> Представление результатов работы сценария {@link UseCase}.
 * <p></p>
 * Реализация не должна одновременно имплементимровать интерфейс {@link State}.
 */
public interface UseCaseOutput {
    /**
     * Перечисление возможных состояний сценария бизенс-логики {@link UseCase}
     */
    enum Status {
        /**
         * Сценарий выполняется в текущий момент
         */
        IN_PROGRESS,
        /**
         * Сценарий успешно выполнен
         */
        SUCCESS,
        /**
         * Сценарий завершился с ошибкой
         */
        FAILURE
    }
}
