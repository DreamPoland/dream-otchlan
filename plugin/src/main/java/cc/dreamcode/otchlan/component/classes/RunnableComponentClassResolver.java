package cc.dreamcode.otchlan.component.classes;


import cc.dreamcode.otchlan.OtchlanPlugin;
import cc.dreamcode.otchlan.component.resolvers.ComponentClassResolver;
import cc.dreamcode.otchlan.exception.PluginRuntimeException;
import cc.dreamcode.otchlan.stereotypes.Scheduler;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;

import java.util.Map;

public class RunnableComponentClassResolver extends ComponentClassResolver<Class<? extends Runnable>> {

    private @Inject OtchlanPlugin otchlanPlugin;

    @Override
    public boolean isAssignableFrom(@NonNull Class<? extends Runnable> runnableClass) {
        return Runnable.class.isAssignableFrom(runnableClass);
    }

    @Override
    public String getComponentName() {
        return "runnable";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<? extends Runnable> runnableClass) {
        Scheduler scheduler = runnableClass.getAnnotation(Scheduler.class);
        if (scheduler == null) {
            throw new PluginRuntimeException("Runnable are not have a Scheduler annotation.");
        }

        return new ImmutableMap.Builder<String, Object>()
                .put("delay-time", scheduler.delay())
                .put("repeat-time", scheduler.repeat())
                .put("async", scheduler.async())
                .build();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<? extends Runnable> runnableClass) {
        final Runnable runnable = injector.createInstance(runnableClass);

        Scheduler scheduler = runnable.getClass().getAnnotation(Scheduler.class);
        if (scheduler == null) {
            throw new PluginRuntimeException("Runnable are not have a Scheduler annotation.");
        }

        if (scheduler.async()) {
            this.otchlanPlugin.getServer().getScheduler().runTaskTimerAsynchronously(this.otchlanPlugin,
                    runnable, scheduler.delay(), scheduler.repeat());
        }
        else {
            this.otchlanPlugin.getServer().getScheduler().runTaskTimer(this.otchlanPlugin,
                    runnable, scheduler.delay(), scheduler.repeat());
        }

        return runnable;
    }

}
