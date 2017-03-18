package com.moggot.commonalarmclock;

/*
 * Google Inc. Все права защищены.
 *
 * Лицензия Apache версии 2.0 (далее "Лицензия");
 * этот файл можно использовать только в соответствии с Лицензией.
 * Чтобы получить ее копию, перейдите на страницу
 *
 *     http://www.apache.org/licenses/LICENSE-2.0.
 * Если иное не требуется действующим законодательством или не оговорено в письменном виде,
 * программное обеспечение, распространяемое по Лицензии, распространяется "КАК ЕСТЬ",
 * БЕЗ КАКИХ-ЛИБО ГАРАНТИЙ И УСЛОВИЙ, явных или подразумеваемых.
 * Разрешения и ограничения в рамках Лицензии приводятся в Лицензии для каждого конкретного языка.
 */

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Это подкласс {@link Application}, с помощью которого приложению передаются общие объекты,
 * например {@link Tracker}.
 */
public class AnalyticsApplication extends Application {
    private Tracker mTracker;

    /**
     * Получает счетчик {@link Tracker}, используемый по умолчанию для этого приложения {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // Чтобы включить ведение журнала отладки, используйте adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }
}
