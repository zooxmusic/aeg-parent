package com.aeg.ims.transfer;

import java.io.Serializable;

/**
 * Created by bszucs on 4/4/2016.
 */
public class TransferResult implements Serializable{

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "Failed";

    private String message;

    private TransferResult(final String message){
        this.message = message;
    }
    public static TransferResult success() {
        return new TransferResult(SUCCESS);
    }
    public static TransferResult failed() {
        return new TransferResult(FAILED);
    }
    public String getMessage() {
        return message;
    }

}
