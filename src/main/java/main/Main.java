package main;

import entity.Room;
import entity.Visitor;
import generator.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    //private static final Logger logger = LogManager.getLogger();


    public static void main(String[] args) throws InterruptedException {
        Room room = Room.getInstance();
        List<Integer> visitorList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            visitorList.add(IdGenerator.generate());
        }

        for (int i = 0; i < visitorList.size(); i++) {
            while (room.check() == -1) {
                System.out.println("roomcheck");
                TimeUnit.SECONDS.sleep(1);
            }
            int num = room.reserve(room.check());
            System.out.println(visitorList.get(i) + " курит кальян номер" + num);
            executorService.submit(new Visitor(visitorList.get(i),num));

        }
        executorService.shutdown();

/*        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            executorService.submit(new Room());
        }*/

    }
}

