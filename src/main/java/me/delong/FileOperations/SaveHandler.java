package me.delong.FileOperations;

/**
 * Created by josephdelong on 12/18/16.
 */
public interface SaveHandler {
    public void onSuccess(String path);
    public void onFail(int errorCode, String error);
}
