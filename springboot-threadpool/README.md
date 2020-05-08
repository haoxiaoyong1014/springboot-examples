执行 http://localhost:8080/user 注意控制台打印:

```yaml
2020-05-08 10:27:17.239  INFO 4404 --- [nio-8080-exec-1] c.h.t.springboot.TestController          : start submit user
2020-05-08 10:27:17.246  INFO 4404 --- [nio-8080-exec-1] c.h.t.springboot.TestController          : end submit user
2020-05-08 10:27:17.246  INFO 4404 --- [pool-1-thread-1] c.h.t.springboot.ThreadServiceImpl       : start executeAsync user
2020-05-08 10:27:18.248  INFO 4404 --- [pool-1-thread-1] c.h.t.springboot.ThreadServiceImpl       : end executeAsync user
```
有打印结果可以看出是异步执行;不等service层执行结果如何！

执行 http://localhost:8080/order  控制台打印：

```yaml
2020-05-08 10:40:50.109  INFO 7576 --- [nio-8080-exec-4] c.h.t.springboot.TestController          : start submit order
2020-05-08 10:40:50.110  INFO 7576 --- [nio-8080-exec-4] c.h.t.springboot.ThreadServiceImpl       : start executeAsync order 
2020-05-08 10:40:50.111  INFO 7576 --- [nio-8080-exec-4] c.h.t.springboot.ThreadServiceImpl       : end executeAsync order
2020-05-08 10:40:50.112  INFO 7576 --- [nio-8080-exec-4] c.h.t.springboot.TestController          : end submit order
2020-05-08 10:40:50.112  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : start... 0
order thread do something......
2020-05-08 10:40:50.112  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : end... 0
2020-05-08 10:40:50.112  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : start... 1
order thread do something......
2020-05-08 10:40:50.112  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : end... 1
2020-05-08 10:40:50.112  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : start... 2
order thread do something......
2020-05-08 10:40:50.113  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : end... 2
2020-05-08 10:40:50.113  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : start... 3
order thread do something......
2020-05-08 10:40:50.113  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : end... 3
2020-05-08 10:40:50.113  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : start... 4
order thread do something......
2020-05-08 10:40:50.113  INFO 7576 --- [pool-2-thread-1] c.h.t.springboot.OrderQueueThread        : end... 4
```

