package com.github.xhrg.dlock;

public interface Dlock {

    public void lock(String lockName, String remark);

    public boolean tryLock();

    public void unlock();

}
