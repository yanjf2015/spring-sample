package com.study.web;

import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RedBlackTreeTests {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        TreeMap<Integer, String> treeMap = new TreeMap();
        treeMap.put(1, "a");
        treeMap.put(2, "b");
        treeMap.put(3, "c");
        treeMap.put(4, "d");
        treeMap.put(5, "e");
        treeMap.put(6, "f");
        treeMap.put(7, "g");
        treeMap.put(8, "h");
        Class aClass =  Class.forName("java.util.TreeMap");
        Class[] declaredClasses = aClass.getDeclaredClasses();
        Method getEntry = aClass.getDeclaredMethod("getEntry", Object.class);
        getEntry.setAccessible(true);
        for (Class c : declaredClasses) {
            if (c.getCanonicalName().equals("java.util.TreeMap.Entry")) {
                for (Integer key : treeMap.keySet()) {
                    Field color = c.getDeclaredField("color");
                    Field left = c.getDeclaredField("left");
                    Field right = c.getDeclaredField("right");
                    Field parent = c.getDeclaredField("parent");
                    color.setAccessible(true);
                    left.setAccessible(true);
                    right.setAccessible(true);
                    parent.setAccessible(true);
                    boolean b = color.getBoolean(getEntry.invoke(treeMap, key));
                    System.out.println(left.get(getEntry.invoke(treeMap, key)));
                    System.out.println(right.get(getEntry.invoke(treeMap, key)));
                    System.out.println(parent.get(getEntry.invoke(treeMap, key)));
                    System.out.println(key + ":" + b);
                    System.out.println("-----");
                }
            }
        }
    }


}
