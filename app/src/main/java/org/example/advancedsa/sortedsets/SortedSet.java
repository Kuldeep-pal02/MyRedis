package org.example.advancedsa.sortedsets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortedSet {

    private final SkipList skipList = new SkipList();
    private final Map<String, Double> scoreMap = new HashMap<>();

    public void add( String key, Double score){
        if(scoreMap.containsKey( key ) ){
            double oldScore = scoreMap.get( key );
            skipList.remove( key ,oldScore);
        }
        skipList.add( key, score );
        scoreMap.put( key, score );
    }
    public boolean remove( String key ){
        if( scoreMap.containsKey( key ) ){
            double score = scoreMap.get( key );
            scoreMap.remove( key );
            return skipList.remove( key, score );

        }
        return false;
    }

    public double getScore( String key ){
        return scoreMap.getOrDefault( key , Double.NaN );
    }

    public List<String> rangeQuery(double minScore, double maxScore){
        return skipList.rangeQuery( minScore , maxScore);
    }
}
