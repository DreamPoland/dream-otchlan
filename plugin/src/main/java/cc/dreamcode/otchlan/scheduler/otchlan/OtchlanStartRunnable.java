package cc.dreamcode.otchlan.scheduler.otchlan;

import cc.dreamcode.otchlan.OtchlanPlugin;
import cc.dreamcode.otchlan.OtchlanService;
import cc.dreamcode.otchlan.OtchlanState;
import cc.dreamcode.otchlan.scheduler.annotations.Scheduler;
import eu.okaeri.injector.annotation.Inject;

import java.util.concurrent.TimeUnit;

@Scheduler(start = 1, interval = 1, unit = TimeUnit.SECONDS)
public class OtchlanStartRunnable implements Runnable {

    private @Inject OtchlanPlugin otchlanPlugin;
    private @Inject OtchlanService otchlanService;

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        if (!this.otchlanService.getOtchlanState().equals(OtchlanState.CLOSED)) {
            return;
        }

        this.otchlanService.setOtchlanState(OtchlanState.WAITING);

        if (this.otchlanService.getRunTime().get() == 0L) {
            this.otchlanService.getRunTime().set(System.currentTimeMillis());
        }

        this.otchlanPlugin.getServer().getScheduler().runTask(this.otchlanPlugin, () ->
                this.otchlanService.start());
    }
}
