package com.antiaction.zwave;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class GenericQueue<T> {

	protected Semaphore lock = new Semaphore(1);

	protected Semaphore available = new Semaphore(0);

	protected List<T> objects = new LinkedList<T>();

	public GenericQueue() {
	}

	public void insert(T obj) {
		boolean bAquired = false;
		while (!bAquired) {
			try {
				bAquired = lock.tryAcquire(1, TimeUnit.SECONDS);
			}
			catch (InterruptedException e) {
			}
		}
		objects.add(obj);
		available.release();
		lock.release();
	}

	public T remove() {
		boolean bAquired = false;
		while (!bAquired) {
			try {
				bAquired = available.tryAcquire(1, TimeUnit.SECONDS);
			}
			catch (InterruptedException e) {
			}
		}
		bAquired = false;
		while (!bAquired) {
			try {
				bAquired = lock.tryAcquire(1, TimeUnit.SECONDS);
			}
			catch (InterruptedException e) {
			}
		}
		T obj = objects.remove(0);
		lock.release();
		return obj;
	}

}
