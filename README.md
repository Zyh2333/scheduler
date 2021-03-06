# scheduler
easy way for schedule task run parallel, both sync and async.

## Annotation

### @TaskScheduleComponent

you can annotate it at class type to make it a component with Spring

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface TaskScheduleComponent {

    String cron() default "";

    String name() default "";

    boolean async() default false;

    boolean schedule() default true;

}
```

### @Task

you can annotate it at task method, make sure the method return type is Runnable or the subclass of it.

```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Task {

    String cron() default "";

    String name() default "";

    boolean async() default false;

}
```

### @Async

you can annotate it at class or method type to make all tasks in this class or the annotated method to be execute async.

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Async {
}
```

### Attention

when conflict among @TaskScheduleComponent @Task @Async 's async field,  async first.

when conflict between @TaskScheduleComponent @Task 's similar field, @Task first.

## How to use

### maven

```xml
<dependency>
    <groupId>cn.edu.whu.zhuyuhan</groupId>
    <artifactId>scheduler-spring-boot-starter</artifactId>
    <version>0.0.4</version>
</dependency>
```

### demo1

```java
@TaskScheduleComponent(cron = "0/5 * * * * ?")
public class TestSyncService {

    @Task(cron = "0/3 * * * * ?") // 3s per task and sync
    public Runnable syncTask1() throws InterruptedException {
        Thread.sleep(3000L);
        return () -> {
            System.out.println(Thread.currentThread().getName() + ":" + "sync task1 execute");
        };
    }

    @Async // 5s per task and async
    @Task
    public Runnable syncTask2() {
        return () -> {
            System.out.println(Thread.currentThread().getName() + ":" + "sync task2 execute");
        };
    }

}
```

### demo2

```java
@TaskScheduleComponent(cron = "0/5 * * * * ?", async = true)
public class TestAsyncService {

    @Task
    public Runnable asyncTask1() {
        return () -> {
            System.out.println(Thread.currentThread().getName() + ":" + "async task1 execute");
        };
    }

    @Task
    public Runnable asyncTask2() {
        return () -> {
            System.out.println(Thread.currentThread().getName() + ":" + "async task2 execute");
        };
    }

}
```

## result example

```
2021-08-01 22:37:05.260  INFO 292956 --- [           main] c.e.w.z.s.scheduler.TaskSchedulerBean    : register task method public java.lang.Runnable cn.edu.whu.ai.core.service.schedule.TestTriggerService.syncTask1() throws java.lang.InterruptedException in bean cn.edu.whu.ai.core.service.schedule.TestTriggerService@6249a08d success
2021-08-01 22:37:05.260  INFO 292956 --- [           main] c.e.w.z.s.scheduler.TaskSchedulerBean    : register task method public java.lang.Runnable cn.edu.whu.ai.core.service.schedule.TestTriggerService.syncTask2() in bean cn.edu.whu.ai.core.service.schedule.TestTriggerService@6249a08d success
2021-08-01 22:37:05.264  INFO 292956 --- [           main] c.e.w.z.s.s.AbstractTaskScheduler        : TaskScheduleComponent init success with cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance@cea67b1
2021-08-01 22:37:05.280  INFO 292956 --- [           main] c.e.w.z.s.s.AbstractTaskScheduler        : TaskScheduleComponent init success with cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance@35f22eef
2021-08-01 22:37:05.281  INFO 292956 --- [           main] c.e.w.z.s.scheduler.TaskSchedulerBean    : register task method public java.lang.Runnable cn.edu.whu.ai.core.service.schedule.TestAsyncTriggerService.asyncTask2() in bean cn.edu.whu.ai.core.service.schedule.TestAsyncTriggerService@7ed8b44 success
2021-08-01 22:37:05.282  INFO 292956 --- [           main] c.e.w.z.s.scheduler.TaskSchedulerBean    : register task method public java.lang.Runnable cn.edu.whu.ai.core.service.schedule.TestAsyncTriggerService.asyncTask1() in bean cn.edu.whu.ai.core.service.schedule.TestAsyncTriggerService@7ed8b44 success
2021-08-01 22:37:05.283  INFO 292956 --- [           main] c.e.w.z.s.s.AbstractTaskScheduler        : TaskScheduleComponent inited success with taskInstancecn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance@36fe83d
2021-08-01 22:37:05.284  INFO 292956 --- [           main] c.e.w.z.s.s.AbstractTaskScheduler        : TaskScheduleComponent inited success with taskInstancecn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance@5cb6abc8
scheduler-asyncThreadGroup-pool-3-1:async task1 execute
scheduler-asyncThreadGroup-pool-4-1:async task2 execute
scheduler-syncThreadGroup-pool-1-1:sync task1 execute
scheduler-syncThreadGroup-pool-1-1:sync task1 execute
scheduler-asyncThreadGroup-pool-5-1:async task2 execute
scheduler-asyncThreadGroup-pool-6-1:async task1 execute
scheduler-syncThreadGroup-pool-2-1:sync task2 execute
scheduler-syncThreadGroup-pool-1-1:sync task1 execute
scheduler-asyncThreadGroup-pool-7-1:async task2 execute
scheduler-asyncThreadGroup-pool-8-1:async task1 execute
scheduler-syncThreadGroup-pool-1-1:sync task1 execute
scheduler-syncThreadGroup-pool-2-1:sync task2 execute
scheduler-syncThreadGroup-pool-1-1:sync task1 execute
scheduler-asyncThreadGroup-pool-9-1:async task1 execute
scheduler-asyncThreadGroup-pool-10-1:async task2 execute
```

the async task will be scheduled in a new thread pool **per schedule**.

the sync task will be scheduled in a new thread pool **per task**.