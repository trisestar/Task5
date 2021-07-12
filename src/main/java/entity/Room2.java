package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Room2 {
    private volatile static Room2 instance;
    Map<Integer, Boolean> hookahs = new HashMap<>();
    Map<Integer, Boolean> queue = new HashMap<>();
    Lock lock;
    Condition cond;
    ExecutorService executorService;


    public static Room2 getInstance() {
        if (instance == null) {
            synchronized (Room2.class) {
                if (instance == null) {
                    instance = new Room2();
                }
            }
        }
        return instance;
    }


    public Room2() {
        hookahs.put(1, true);
        hookahs.put(2, true);
        hookahs.put(3, true);
        queue.put(1, true);
        queue.put(2, true);
        queue.put(3, true);
        lock = new ReentrantLock();
        cond = lock.newCondition();
        executorService = Executors.newFixedThreadPool(3);
    }

    public int check() {
        for (int i = 1; i <= hookahs.size(); i++) {
            if (hookahs.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public int reserve(int id) throws InterruptedException {
        Room2 room = Room2.getInstance();
        lock.lock();
        try {
            while (room.check() == -1) {
                //System.out.println("проверка");
                cond.await();
            }
            int number = room.check();
            hookahs.put(number, false);
            //System.out.println(number + "кальян стал занят");

            executorService.submit(new Visitor2(id, number));
            return number;
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
        return -1;
    }

    public int unreserve(int number) {
        lock.lock();

        hookahs.put(number, true);
        //System.out.println(number + "кальян стал свободен");
        cond.signalAll();
        lock.unlock();
        return number;
    }

    public void end() {
        executorService.shutdown();
    }


}