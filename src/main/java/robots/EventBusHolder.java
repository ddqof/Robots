package robots;

import com.google.common.eventbus.EventBus;

public class EventBusHolder {

    private static final EventBus bus = new EventBus();

    public static EventBus get() {
        return bus;
    }
}