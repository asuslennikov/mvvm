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
