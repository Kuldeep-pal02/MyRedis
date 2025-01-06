package org.example.advancedsa.sortedsets;


import java.util.List;
import java.util.Random;


class SkipListNode{
    String key;
    double score;
    SkipListNode[] forward;
    SkipListNode above;

}
public class SkipList implements ISkipList {


    private static final int MAX_LEVEL = 32;
    private static final double PROBABILITY = 0.5;
    private SkipListNode head;
    private int level;
    private Random random;



    //TODO Implement the skiplist.
    @Override
    public void add(String value, double score) {

    }

    @Override
    public boolean remove(String value, double score) {
        return false;
    }

    @Override
    public boolean contains(String value, double score) {
        return false;
    }

    @Override
    public List<String> rangeQuery(double m, double n) {
        return List.of();
    }


    private int randomLevel() {
        int lvl = 1;
        while (random.nextDouble() < PROBABILITY && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }
}
