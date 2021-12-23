package com.peakmain.basiclibary.network;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/20 0020 上午 10:28
 * mail : 2726449200@qq.com
 * describe ：
 */
public class DataResponse<T> {
    /**
     * data : ...
     * errorCode : 0
     * errorMsg :
     */
    private T data;
    private int errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
