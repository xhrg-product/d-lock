package io.github.xhrg.dlock;

public interface DLock {

    public void lock(String lockName, int timeout) throws DLockException;

    public boolean tryLock(String lockName, int timeout) throws DLockException;

    public void unlock(String lockName) throws DLockException;

}
