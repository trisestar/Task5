package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class HookahBar {
    private volatile static HookahBar instance;
    Map<Integer, Boolean> hookahs = new HashMap<>();
    Map<Integer, Integer> queue = new HashMap<>();
    Lock lock;
    Condition cond;
    ExecutorService executorService;


    public static HookahBar getInstance() {
        if (instance == null) {
            synchronized (HookahBar.class) {
                if (instance == null) {
                    instance = new HookahBar();
                }
            }
        }
        return instance;
    }


    public HookahBar() {
        hookahs.put(1, true);
        hookahs.put(2, true);
        hookahs.put(3, true);
        queue.put(1, -1);
        queue.put(2, -1);
        queue.put(3, -1);
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
        HookahBar room = HookahBar.getInstance();
        lock.lock();
        try {
            while (room.check() == -1) {
                //System.out.println("проверка");
                cond.await();
            }
            int number = room.check();
            hookahs.put(number, false);
            //System.out.println(number + "кальян стал занят");

            executorService.submit(new Visitor(id));
            return number;
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
        return -1;
    }

    public void put(int id) {
        HookahBar room = HookahBar.getInstance();
        lock.lock();
        try {
            while (!isQueueNotFull()) {
                //System.out.println("проверка");
                cond.await();
            }


            System.out.println(id + " зашёл в очередь");
            queue.put(queue.size(), id);
            executorService.submit(new Visitor(id));

        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }

    }

    public boolean isQueueNotFull() {
        if (queue.get(queue.size()) == -1) {
            return true;
        }
        return false;
    }

    public int unreserve(int number) {
        lock.lock();

        hookahs.put(number, true);
        //System.out.println(number + "кальян стал свободен");
        cond.signalAll();
        lock.unlock();
        return number;
    }

    public void unlock(){
        lock.lock();
        //System.out.println("unlock");
        cond.signalAll();
        lock.unlock();

    }

    public int push(int id) {
        //System.out.println(queue);
        for (int i = 1; i <= queue.size(); i++) {
            if (queue.get(i) == id) {
                //System.out.println("найден в слоте" + i);
                if (i == 1) {
                    int response = check();
                    if (response != -1) {
                        queue.put(1, -1);
                    }
                    return response;
                }

                if (isFree(i - 1)) {
                    queue.put(i, -1);
                    queue.put(i - 1, id);

                    //System.out.println(id + " продвинулся на место " + (i - 1));
                    return -1;
                }
            }
        }
        return -1;
    }


    public void reserveHookah(int number) {
        //System.out.println(number + " кальян занят");
        hookahs.put(number, false);
    }

    public boolean isFree(int number) {
        if (queue.get(number) == -1) {
            //System.out.println("свободен");
            return true;
        }
        //System.out.println("не свободен");
        return false;
    }

    public void end() {
        executorService.shutdown();
    }


}