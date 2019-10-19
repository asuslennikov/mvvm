package com.github.asuslennikov.mvvm.presentation;

import com.github.asuslennikov.mvvm.api.presentation.Effect;
import com.github.asuslennikov.mvvm.api.presentation.EffectListener;

/**
 * Вспомогательный класс, позволяющий задать слушателя только для какого-то одного события {@link Effect}.
 * Например, если требуется осуществить какое-либо действие по оканчанию выполнения эффекта, то можно
 * использовать следующий код:
 * <pre>
 * public void editButtonClicked() {
 *     Effect openKeyboardEffect = new OpenKeyboardEffect();
 *     openKeyboardEffect.setListener(new LambdaEffectListener()
 *         .setFinishEffectListener(effect -> doSomething())
 *     screenEffectPublisher.onNext(openKeyboardEffect);
 * }
 * </pre>
 */
public final class LambdaEffectListener implements EffectListener {

    private EffectEventListener startEffectListener;
    private EffectEventListener finishEffectListener;

    @Override
    public void effectStarted(Effect effect) {
        if (startEffectListener != null) {
            startEffectListener.onEvent(effect);
        }
    }

    @Override
    public void effectFinished(Effect effect) {
        if (finishEffectListener != null) {
            finishEffectListener.onEvent(effect);
        }
    }

    /**
     * Замена стандартного интерфейса {@link java.util.function.Consumer} для совместимости со
     * старыми версиями Android
     */
    @FunctionalInterface
    public interface EffectEventListener {
        void onEvent(Effect effect);
    }

    /**
     * Слушатель, получающий нотификации при старте эффекта
     */
    public LambdaEffectListener setStartEffectListener(EffectEventListener startEffectListener) {
        this.startEffectListener = startEffectListener;
        return this;
    }

    /**
     * Слушатель, получающий нотификации при завершении эффекта
     */
    public LambdaEffectListener setFinishEffectListener(EffectEventListener finishEffectListener) {
        this.finishEffectListener = finishEffectListener;
        return this;
    }
}
