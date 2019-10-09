package com.study.web;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AdvanceAi {

    public static void main(String[] args) throws IOException {

        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1\\");
        map1.put("k2", "v2");
        Map<String, String> map2 = new HashMap<>();
        map2.put("A", "XXXX");
        Map<String, String> map3 = new HashMap<>();
        Map<String, String>[] maps = new Map[]{map1, map2, map3};
        String text = store(maps);
        System.out.println(text);
        System.out.println(load(text));
        List<Node> nodeList = new ArrayList<>();
        Node node1 = new Node("node_1", null);
        Node node2 = new Node("node_2", node1);
        Node node3 = new Node("node_3", node1);
        Node node4 = new Node("node_4", node1);
        Node node5 = new Node("node_5", node2);
        Node node6 = new Node("node_6", node2);
        Node node7 = new Node("node_7", node3);
        Node node8 = new Node("node_8", node4);
        Node node9 = new Node("node_9", node7);
        Node node10 = new Node("node_10", node8);
        Node node11 = new Node("node_11", node8);
        Node node12 = new Node("node_12", node9);
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        nodeList.add(node4);
        nodeList.add(node5);
        nodeList.add(node6);
        nodeList.add(node7);
        nodeList.add(node8);
        nodeList.add(node9);
        nodeList.add(node10);
        nodeList.add(node11);
        nodeList.add(node12);
        System.out.println(nodeList);
        nodeList = simplify(nodeList);
        System.out.println(nodeList);
    }

    private static String store(Map<String, String>[] maps) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(maps).forEach((map) -> {
            if (map.size() == 0) {
                return;
            }
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            Map.Entry entry = iterator.next();
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            iterator.forEachRemaining((stringEntry) -> {
                sb.append(";");
                sb.append(stringEntry.getKey());
                sb.append("=");
                sb.append(stringEntry.getValue());
            });
            sb.append("\n");
        });
        return sb.toString();
    }

    private static List<Map<String, String>> load(String text) {
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        StringBuilder sbK = new StringBuilder();
        StringBuilder sbV = new StringBuilder();
        boolean k = true;
        boolean v = false;
        int i = 0;
        for (char c : text.toCharArray()) {
            if (c == '=') {
                v = true;
                k = false;
                continue;
            }
            if (c == ';') {
                map.put(sbK.toString(), sbV.toString());
                k = true;
                v = false;
                sbK.setLength(0);
                sbV.setLength(0);
                continue;
            }
            if (c == '\n') {
                map.put(sbK.toString(), sbV.toString());
                mapList.add(map);
                map = new HashMap<>();
                k = true;
                v = false;
                sbK.setLength(0);
                sbV.setLength(0);
                i++;
                continue;
            }
            if (k) {
                sbK.append(c);
            }
            if (v) {
                sbV.append(c);
            }
        }
        return mapList;
    }

    private static List<Node> simplify (List<Node> nodeList) {
        List<Node> simplifyNodeList = new ArrayList<>();
        nodeList.forEach(node -> {
            List<Node> childs = nodeList.stream().filter(node1 -> {
                if (node1.parent == null) {
                    return false;
                } else {
                    return node.name.equals(node1.parent.name);
                }
            }).collect(Collectors.toList());
            System.out.println(node.name + childs.size());
            if (childs.size() != 1) {
                simplifyNodeList.add(node);
            } else {
                childs.get(0).parent = node.parent;
                System.out.println("remove " + node.name);
            }
        });
        return simplifyNodeList;
    }

    static class Node {

        private String name;
        private Node parent;

        public Node(String name, Node parent) {
            this.name = name;
            this.parent = parent;
        }

        @Override
        public String toString() {
            String parentName = parent == null ? "" : parent.name;
            return name + " parent: " + parentName;
        }
    }

}
