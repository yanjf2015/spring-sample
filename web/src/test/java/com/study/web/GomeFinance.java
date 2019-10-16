package com.study.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.math.RandomUtils;

import java.util.HashSet;
import java.util.Set;

public class GomeFinance {

    public static void main(String[] args) {

        int n = 199;
        int[] ints = new int[n];

        for (int i = 0; i < n; i++) {
            ints[i] = RandomUtils.nextInt(500);
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            System.out.println(ints[i]);
            set.add(ints[i]);
        }
        System.out.println("------------------");
        for (int i : set) {
            System.out.println(i);
        }

        for (int i = 0; i < 3000; i++) {
            Integer integer = i;
            System.out.println(integer.hashCode());
        }
    }

}
