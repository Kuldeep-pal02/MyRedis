package org.example.advancedsa.bloomfilter;

public class HashingUtil {

    public static void initHashFunctionBloomFilter (BloomFilter bf ){

    }



    //simplified example of MurmurHash3 (32-bit)
    public static int murMurHash(byte[] data, int seed) {
        int c1 = 0xcc9e2d51;
        int c2 = 0x1b873593;

        int length = data.length;
        int h1 = seed;
        int roundedEnd = (length & 0xfffffffc);  // Round down to the nearest multiple of 4

        for (int i = 0; i < roundedEnd; i += 4) {
            int k1 = ((data[i] & 0xff))
                    | ((data[i + 1] & 0xff) << 8)
                    | ((data[i + 2] & 0xff) << 16)
                    | ((data[i + 3] & 0xff) << 24);

            k1 *= c1;
            k1 = Integer.rotateLeft(k1, 15);
            k1 *= c2;

            h1 ^= k1;
            h1 = Integer.rotateLeft(h1, 13);
            h1 = h1 * 5 + 0xe6546b64;
        }

        // Process remaining bytes
        int k1 = 0;
        for (int i = length & 0xfffffffc; i < length; i++) {
            k1 ^= (data[i] & 0xff) << (i & 3) * 8;
        }

        k1 *= c1;
        k1 = Integer.rotateLeft(k1, 15);
        k1 *= c2;
        h1 ^= k1;

        // Finalization
        h1 ^= length;
        h1 ^= (h1 >>> 16);
        h1 *= 0x85ebca6b;
        h1 ^= (h1 >>> 13);
        h1 *= 0xc2b2ae35;
        h1 ^= (h1 >>> 16);

        return h1;
    }
}
