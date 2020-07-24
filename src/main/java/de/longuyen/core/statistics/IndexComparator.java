package de.longuyen.core.statistics;

import java.util.Arrays;

public class IndexComparator<T extends Comparable<T>>{
    private final T[] array;

    public IndexComparator(T[] array) {
        if(array.length == 0){
            throw new RuntimeException("Empty array");
        }
        this.array = array;
    }

    public Integer[] indexArray() {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            indexes[i] = i;
        }
        return indexes;
    }

    public Integer[] sortedIndexArray(){
        Integer [] indices = indexArray();
        T [] array = Arrays.copyOf(this.array, this.array.length);
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for(int i = 0; i < array.length - 1; i++){
                if (array[i].compareTo(array[i + 1]) > 0) {
                    exchange(array, i, i + 1);
                    exchange(indices, i, i + 1);
                    sorted = false;
                }
            }
        }
        return indices;
    }

    public <K>void exchange(K [] array, int a, int b){
        K temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
