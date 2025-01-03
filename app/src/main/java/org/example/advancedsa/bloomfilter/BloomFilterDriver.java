package org.example.advancedsa.bloomfilter;

public class BloomFilterDriver {
    public static void main(String[] args) {
        BloomFilter bloomFilter = new BloomFilter( 100, 50);
        bloomFilter.add( "Kuldeep");
        bloomFilter.add("Santosh");
        bloomFilter.add("Pramod");
        System.out.println( bloomFilter.mightContains("Pramod") );
        System.out.println( bloomFilter.mightContains("Ritesh") );
        bloomFilter.add("Ritesh");
        System.out.println( bloomFilter.mightContains("Ritesh") );

    }
}
