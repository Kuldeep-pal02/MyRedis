package org.example.advancedsa.bloomfilter;

import java.util.BitSet;
import java.util.function.Function;

public class BloomFilter {

    private final BitSet bitSet;
    //Learn Generics or change this to List.
    private final Function<String,Integer> [] hashFunctions;

    @SuppressWarnings("unchecked")
    public BloomFilter(int bitSetSize, int hashFunctionCount) {
        this.bitSet = new BitSet( bitSetSize );
        this.hashFunctions = (Function<String, Integer>[])new Function[ hashFunctionCount ];

        for (int i = 0; i < hashFunctionCount; i++) {
            final int seed = i;
            //this.hashFunctions[i] = element -> Math.abs( getHash(element, seed) % bitSetSize);
            this.hashFunctions[i] = element -> Math.abs( getMurMurHash(element, seed) % bitSetSize);
        }
        //HashingUtil.initHashFunctionBloomFilter( this );
    }

    private int getHash(String element, int seed) {
        return (element+seed).hashCode();
    }

    private int getMurMurHash(String element, int seed) {
        return HashingUtil.murMurHash( element.getBytes(), seed) ;
    }

    public void add( String input ){
        for( Function<String,Integer> hashFunction : hashFunctions ){
            int index = hashFunction.apply( input );
            bitSet.set( index );
        }
    }
    //Reply False with 100% Guarantee that input is not Present / Seen.
    //If True is returned, it does not Guarantee the present of input.
    public boolean mightContains( String input ){
        for( Function<String,Integer> hashFunction : hashFunctions ){
            int index = hashFunction.apply( input );
            if( !bitSet.get( index ) ){
                return false;
            }
        }
        return true;
    }
}
