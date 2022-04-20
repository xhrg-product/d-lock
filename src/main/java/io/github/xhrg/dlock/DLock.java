package io.github.xhrg.dlock;

public interface DLock {

    /**
     * 支持等待的排他锁
     * 
     * @param lockName, 加锁的名称, 当存在多个锁场景的时候, 用来区分
     * @param timeout, 锁超时时间，单位秒
     * @throws DLockException 异常
     */
    public void lock(String lockName, int timeout) throws DLockException;

    /**
     * 不等待的排他锁
     * 
     * @param lockName, 锁名称
     * @param timeout, 超时时间,单位秒
     * @return
     * @throws DLockException
     */
    public boolean tryLock(String lockName, int timeout) throws DLockException;

    /**
     * 释放锁
     * 
     * @param lockName， 锁名称
     * @throws DLockException
     */
    public void unlock(String lockName) throws DLockException;

}
