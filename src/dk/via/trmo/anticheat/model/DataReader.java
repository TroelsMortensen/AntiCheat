package dk.via.trmo.anticheat.model;

import dk.via.trmo.anticheat.model.containers.ClassContainer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataReader {

    private String suffix;

    public List<ClassContainer> readAllClasses(String path, String suffix) {
        this.suffix = suffix;
        List<ClassContainer> allClasses = new ArrayList<>();
        File f = new File(path);
        readFileRecursive(f, allClasses);

        return allClasses;
    }

    private void readFileRecursive(File f, List<ClassContainer> allClasses) {
        if(isZipFile(f)) return;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                readFileRecursive(file, allClasses);
            }
        } else {
            if (f.getName().contains(suffix)) {
                String content = readContent(f.getAbsolutePath());
                if(!"".equals(content))
                    allClasses.add(new ClassContainer(f.getAbsolutePath(), content));
            }
        }
    }

    private boolean isZipFile(File f) {
        if(f.getName().contains(".7z")) return true;
        if(f.getName().contains(".zip")) return true;
        if(f.getName().contains(".rar")) return true;
        return false;
    }

    private String readContent(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath)/*, StandardCharsets.UTF_8)*/)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (Exception e) {
            System.out.println("Error: " + filePath);
            return "";
        }
        return contentBuilder.toString().replace(" ", "").replace("\t", "").replace("\n", "");
    }
}
