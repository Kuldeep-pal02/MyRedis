package org.example.advancedsa.sortedsets;


import java.util.List;


class SkipListNode{
    String value;
    double score;
    SkipListNode[] forward;
}
public class SkipList implements ISkipList {


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
}
