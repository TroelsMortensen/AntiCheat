package dk.via.trmo.anticheat.model.containers;

public class ClassContainer {
    public String absolutePath;
    public String classContent;

    public ClassContainer(String absolutePath, String classContent) {
        this.absolutePath = absolutePath;
        this.classContent = classContent;
    }
}
