package com.github.xhrg.dlock;

public interface Dlock {

    public void lock(String lockName, int timeout) throws DlockException;

    public boolean tryLock(String lockName, int timeout) throws DlockException;

    public void unlock(String lockName) throws DlockException;

}
