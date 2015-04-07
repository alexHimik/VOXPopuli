package kz.voxpopuli.voxapplication;

import android.app.Application;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusBuilder;

/**
 * Created by user on 24.03.15.
 */
public class VoxApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).
                installDefaultEventBus();
    }
}
