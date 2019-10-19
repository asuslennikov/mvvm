package com.github.asuslennikov.mvvm.api.presentation;

import androidx.annotation.NonNull;

import com.github.asuslennikov.mvvm.api.domain.UseCase;

import io.reactivex.Observable;

/**
 * Интерфейс обработчика события для экрана. Отвечает за логику работы экрана, хранит его состояние отдельным объектом.
 * Также осуществляет взаимодействие с domain слоем приложения (запускает {@link  UseCase}).
 * <p></p>
 * <b>Зона ответственности:</b> Управление состоянием экрана и реакция на его события
 * <p></p>
 *
 * @param <STATE> Класс, описывающий состояние данного экрана (см. {@link State})
 */
public interface ViewModel<STATE extends State> {

    /**
     * Метод позволяет зарегестрировать слушателя для получения текущего состояния экрана.
     *
     * @param screen объект, для которого планируется получать состояние экрана.
     *               Реализация <code>ViewModel</code> самостоятельно решает как именно
     *               использовать переданный экземпляр
     * @return RxJava имплементацию слушателя
     */
    @NonNull
    Observable<STATE> getState(@NonNull Screen<STATE> screen);

    /**
     * Метод позволяет зарегестрировать слушателя для получения эффектов экрана.
     *
     * @param screen объект, для которого планируется получать эффекты экрана.
     *               Реализация <code>ViewModel</code> самостоятельно решает как именно
     *               использовать переданный экземпляр
     * @return RxJava имплементацию слушателя
     */
    @NonNull
    Observable<Effect> getEffect(@NonNull Screen<STATE> screen);
}
