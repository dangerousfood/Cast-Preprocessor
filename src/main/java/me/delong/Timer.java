package me.delong;

/**
 * Created by josephdelong on 12/21/16.
 */
public class Timer {
    final long startTime = System.currentTimeMillis();

    public Long stop(){
        final long endTime = System.currentTimeMillis();
        return (endTime - startTime);
    }
}
