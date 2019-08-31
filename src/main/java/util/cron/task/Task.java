package util.cron.task;

@FunctionalInterface
public interface Task {
    void schedule() throws Exception;
}
