package com.github.xhrg.dlock;

public interface Dlock {

    public void lock(String lockName, int timeoutms, String remark) throws DlockException;

    public boolean tryLock(String lockName, int timeoutms, String remark) throws DlockException;

    public void unlock() throws DlockException;

}
