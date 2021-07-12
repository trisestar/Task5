package main;

import entity.Room2;
import generator.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> visitorList = new ArrayList<>();
        Room2 room = Room2.getInstance();
        for (int i = 0; i < 10; i++) {
            room.put(IdGenerator.generate());
        }

        room.end();
    }
}

