package me.delong.FileOperations;

/**
 * Created by josephdelong on 12/19/16.
 */
public class TemporarySaveHandler implements SaveHandler{
    @Override
    public void onSuccess(String path) {
        System.err.println("Successfully saved");
    }

    @Override
    public void onFail(int errorCode, String error) {
        System.err.println("Save Failed");
    }
}
