## d-lock

d为db指的是数据库，或者"Distributed lock"中的d。

在实际业务开发中，并不一定所有的场景都需要高性能的分布式锁，比如基于redis，zookeeper。有点时候，也需要基于数据库的分布式锁。
对性能要求不高，但是因为项目本身就已经用到了mysql，没有必要再引用一个多余的中间件产品。


## 设计过程的一些思考

#### 要不要绑定spring事务上下文

因为锁本身是必须在finally中unlock的，所以不需要和spring事务关联，并且就算业务失败，分布式锁也不应该回退到之前的数据。
绑定spring事务参考"DataSourceUtils.getConnection(dataSource);" 和 "spring jdbctemplate"


#### 要不要select for update

会占用mysql的链接，所以不使用， 比较适合等待排他锁，但是不适合不等待的排他锁


#### 加锁insert，解锁delete

这种方法没有考虑到锁超时，如果出现锁超时，又需要检查数据。