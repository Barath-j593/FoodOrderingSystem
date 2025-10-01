package com.foodapp.util;

import java.io.*;

public class SerializationUtil {
    public static void save(Object obj, String path) {
        try {
            File f = new File(path);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
                oos.writeObject(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object load(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) return null;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                return ois.readObject();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
