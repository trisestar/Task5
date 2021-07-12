package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Room {
    private volatile static Room instance;
    Map<Integer, Boolean> hookahs = new HashMap<>();


    public static Room getInstance() {
        if (instance == null) {
            synchronized (Room.class) {
                if (instance == null) {
                    instance = new Room();
                }
            }
        }
        return instance;
    }


    public Room() {
        hookahs.put(1, true);
        hookahs.put(2, true);
        hookahs.put(3, true);
    }

    public int check() {
        for (int i = 1; i <= hookahs.size(); i++) {
            if (hookahs.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public int reserve(int number) {
        hookahs.put(number, false);
        return number;
    }

    public int unreserve(int number) {
        hookahs.put(number, true);
        return number;
    }




}