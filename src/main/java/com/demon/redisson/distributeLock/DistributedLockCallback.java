package com.demon.redisson.distributeLock;

/**
 * @Description:
 * @Auther Demon
 * @Date 2017/12/14 10:57 星期四
 */
public interface DistributedLockCallback<T> {

    /**
     * 调用者必须在此方法中实现需要加分布式锁的业务逻辑
     *
     * @return
     */
    public T process();

    /**
     * 得到分布式锁名称
     *
     * @return
     */
    public String getLockName();

}
