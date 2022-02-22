package cn.edu.whu.zhuyuhan.scheduler.scheduler.context;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/21 10:26 下午
 **/
public class TaskContextHolder {

    /**
     * init Context for current thread
     */
    private static ThreadLocal<TaskContext> local = ThreadLocal.withInitial(TaskContext::new);

    private TaskContextHolder() {
    }

    public static TaskContext get() {
        if (local.get() == null) {
            local.set(new TaskContext());
        }
        return local.get();
    }

    public static void clear() {
        local.remove();
    }

    public static void remove() {
        local.remove();
    }

}
