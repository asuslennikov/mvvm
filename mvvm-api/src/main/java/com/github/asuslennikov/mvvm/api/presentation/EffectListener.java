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
