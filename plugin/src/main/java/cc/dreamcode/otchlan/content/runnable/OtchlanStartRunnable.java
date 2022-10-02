package cc.dreamcode.otchlan.content.runnable;

import cc.dreamcode.otchlan.OtchlanPlugin;
import cc.dreamcode.otchlan.content.OtchlanService;
import cc.dreamcode.otchlan.content.OtchlanState;
import cc.dreamcode.otchlan.stereotypes.Scheduler;
import eu.okaeri.injector.annotation.Inject;

@Scheduler(delay = 20, repeat = 20)
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
