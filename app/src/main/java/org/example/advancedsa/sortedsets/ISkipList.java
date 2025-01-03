package org.example.advancedsa.sortedsets;

import java.util.List;

public interface ISkipList {
    public void add( String value, double score);
    public boolean remove( String value, double score);

    public boolean contains( String value, double score);

    public List<String> rangeQuery(double m, double n);
}
