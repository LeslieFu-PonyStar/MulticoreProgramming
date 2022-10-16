# The art of multipropcessor programming

> 该代码基于书籍《多处理器编程的艺术》中的伪代码，结合课堂上讲授知识进行编程实现

## 自旋锁与争用
### 自旋锁
这部分测试的是书上的TAS，TTAS，后退锁，ALock，CLH队列锁，MCS队列锁，counter100万次计数的任务按照线程数平分，作业里所测的时间是线程里运行的总时间（本来想测试cpu执行时间的，但是找到的java测试线程cpu执行时间的方法跟总时间出入比较大，自己也没有想到比较好的方法，这里有点问题）

``` java
long start = System.currentTimeMillis();
//execution code
long exeTime = System.currentTimeMillis() - start;
```
