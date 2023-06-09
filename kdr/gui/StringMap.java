package kdr.gui;

import java.util.*;

public class StringMap extends HashMap<String, String> {
    public StringMap() {
    }

    public String get(String s) {
        return (String) super.get(s);
    }
}
