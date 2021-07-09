package main;


import entity.Group;
import entity.Visitor;
import entity.VisitorQueue;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger();


    public static void main(String[] args) {
        Group group = new Group();
        group.add(new Visitor(1234,"Mike"));
        group.add(new Visitor(1235,"Joe"));
        group.add(new Visitor(1236,"Liz"));
        List list = group.getVisitorsList();
        logger.log(Level.INFO, list);
    }
}
