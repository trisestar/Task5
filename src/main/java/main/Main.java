package main;

import entity.HookahBar;
import generator.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> visitorList = new ArrayList<>();
        HookahBar room = HookahBar.getInstance();
        for (int i = 0; i < 10; i++) {
            room.put(IdGenerator.generate());
        }

        room.end();
    }
}

