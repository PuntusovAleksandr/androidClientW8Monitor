package com.w8.w8monitor.android.api.event;

/**
 * Created by AleksandrP on 17.11.2016.
 */

public class NetworkResponseEvent<T> {

    private int id;

    private T data;
    private boolean sucess;

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public T getData() {
        return data;
    }

    public void setData(T mData) {
        data = mData;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean mSucess) {
        sucess = mSucess;
    }


    @Override
    public String toString() {
        return "NetworkResponseEvent{" +
                "id=" + id +
                "data=" + data +
                ", sucess=" + sucess +
                '}';
    }
}
