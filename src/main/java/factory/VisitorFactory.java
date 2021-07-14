package factory;


import entity.Visitor;

public class VisitorFactory {
    public static Visitor createVisitor(int id) {
        return new Visitor(id);
    }

}

