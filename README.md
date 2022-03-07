# scheduler

easy way for schedule task run parallel, sync, async, managed and distributed.

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

### @Special

you can annotate a task method with @Special to build a new thread pool for its schedule, so that it can avoid task block or time wait in common thread pool by default.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Special {
}
```

### @Distributed(to be continued)

you can annotate a task method or class with @Distributed to make task schedule distributed.

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Distributed {
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
    <version>0.0.12</version>
</dependency>
```

### demo1(Sync&Async on task method)

```java
@TaskScheduleComponent(cron = "0/5 * * * * ?")
public class TestSyncService {

    @Task(cron = "0/2 * * * * ?") // 2s per task and sync
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

### demo2(Sync&Async on task class)

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

the sync task will be scheduled in a common thread (new if annotated with @Special) pool **per task**.

## Manage Function

scheduler can build a task manage db automated to manage task and offer some information for users in direction or by sdk function.

### DB Supported

```sql
CREATE TABLE IF NOT EXISTS `scheduler`
(
    `id`            bigint(5)    NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `name`          varchar(128) NOT NULL COMMENT '名称',
    `cron`          varchar(32) COMMENT '定时规则',
    `parent_name`   varchar(64)  NOT NULL COMMENT '父级名称',
    `execute_count` Integer(5)   NOT NULL DEFAULT 1 COMMENT '执行次数',
    `status`        Integer(5)   NOT NULL DEFAULT 0 COMMENT '状态',
    `gmt_update`    timestamp(0) NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    `gmt_create`    timestamp(0) NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
```

![img](https://picturebed-1301866798.cos.ap-chengdu.myqcloud.com/Scheduler/Scheduler-01.png)

### Sdk Function

1. you can inject a spring bean called `SchedulerTemplate` and its sdk function can help you get some manage info during schedule.

```java
     // get task info by its name
    public SchedulerDO getByName(String name) {
        return schedulerDAO.getByName(name);
    }

    // get task last schedule time to control increasing data
    public Long getLastScheduleTime() {
        return TaskContextHolder.get().getSchedulerDO().getGmtUpdate().getTime();
    }
```

demo:

```java
@TaskScheduleComponent
public class AwardSkillExcellenceScheduleService {
    private static long lastCurrent = 0;
    private static long current = 0;
    @Autowired
    AwardSkillExcellenceService awardSkillExcellenceService;
    @Autowired
    ScanExcelToolService scanExcelToolService;
    @Autowired
    SchedulerTemplate schedulerTemplate;
    @Task(cron = "0 0/5 * * * ?")
    public Runnable refresh() {
        return () -> {
            //
            lastCurrent = schedulerTemplate.getLastScheduleTime();
            current = new Date().getTime();

            AwardSkillExcellenceFilterMapper awardSkillExcellenceFilterMapper = new AwardSkillExcellenceFilterMapper();
            awardSkillExcellenceFilterMapper.gmtCreateFrom = lastCurrent;
            awardSkillExcellenceFilterMapper.gmtCreateTo = current;
            for (AwardSkillExcellence awardSkillExcellence:awardSkillExcellenceService.getListByFilter(awardSkillExcellenceFilterMapper)) {
                scanExcelToolService.postPointAndNotify(awardSkillExcellence.getUserCode(),awardSkillExcellence.getPoint(),9,awardSkillExcellence.getId(),ActivityTypeFiveRelationEnum.SKILL_EXCELLENCE);
            }
        };
    }

}
```

2. you can manage task info using `TaskContextHolder` class to get info from `TaskContext` class:

```java
public class TaskContext {

    // task begin date 
    private Long beginDate;

    // task end date
    private Long endDate;

    // task manage metadata
    private ScheduleComponentTaskInstance taskInstance;
    
    // task metadata when schedule
    private Map<String, Object> metadata;

    // task db metadata
    private SchedulerDO schedulerDO;
}
```

```java
TaskContext taskContext = TaskContextHolder.get()
```