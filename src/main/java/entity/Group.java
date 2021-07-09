package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    private List<Visitor> visitorsList = new ArrayList<>();

    public Group() {
    }

    public Group(List<Visitor> visitorsList) {
        this.visitorsList = visitorsList;
    }

    public List<Visitor> getVisitorsList() {
        return visitorsList;
    }

    public void setVisitorsList(List<Visitor> visitorsList) {
        this.visitorsList = visitorsList;
    }

    public boolean add(Visitor newVisitor){
        return visitorsList.add(newVisitor);
    }

    public void removeAll(){
        visitorsList.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(visitorsList, group.visitorsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorsList);
    }

    @Override
    public String toString() {
        return "Group{" +
                "visitorsList=" + visitorsList +
                '}';
    }
}
