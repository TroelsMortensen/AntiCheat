package dk.via.trmo.anticheat.model;

import java.beans.PropertyChangeListener;

public interface AntiCheatModel {

    void calculate(String path, String suffix, Double threshold);
    void addListener(String evtName, PropertyChangeListener listener);

}
