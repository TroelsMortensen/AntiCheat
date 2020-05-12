package dk.via.trmo.anticheat.model;

import dk.via.trmo.anticheat.model.algorithm.LongestCommonSubSequence;
import dk.via.trmo.anticheat.model.containers.ClassContainer;
import dk.via.trmo.anticheat.model.containers.PairResult;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class Model implements AntiCheatModel{

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void calculate(String path, String suffix, Double threshold) {
        Thread thread = new Thread(() -> {
            DataReader dr = new DataReader();
            List<ClassContainer> classContainers = dr.readAllClasses(path, suffix);
            System.out.println("Classes read: " + classContainers.size());
            support.firePropertyChange("OnClassesRead", 0, classContainers.size());
            compareAll(classContainers, threshold);
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void compareAll(List<ClassContainer> allClasses, Double threshold) {
        LongestCommonSubSequence l = new LongestCommonSubSequence();
        int whenToUpdate = 0;
        int totalCalculated = 0;
        double totalCalculationsRequired = allClasses.size() * allClasses.size();
        for (ClassContainer aClass : allClasses) {
            for (ClassContainer aClass2 : allClasses) {
                if(aClass.absolutePath.equals(aClass2.absolutePath)) continue;
                PairResult pairResult = l.calcLongestCommonSubSequence(aClass, aClass2);

                if(pairResult.similarity > threshold) {
                    support.firePropertyChange("OnResult", null, pairResult);
                }
                whenToUpdate++;
                totalCalculated++;
            }
            if(whenToUpdate >= 500) {
                double newValue = (double) totalCalculated / totalCalculationsRequired ;
                support.firePropertyChange("OnProgressChanged", 0.0, newValue);
                whenToUpdate = 0;
                System.out.println("Calc: " + totalCalculated + "; progress: " + newValue);
            }
        }
    }

    public void addListener(String evtName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(evtName, listener);
    }

}
