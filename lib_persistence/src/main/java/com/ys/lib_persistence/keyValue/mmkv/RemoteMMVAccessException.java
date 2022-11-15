package com.ys.lib_persistence.keyValue.mmkv;


public class RemoteMMVAccessException extends RuntimeException {
    public RemoteMMVAccessException() {

    }

    public RemoteMMVAccessException(String detailMessage) {
        super(detailMessage);
    }

    public RemoteMMVAccessException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RemoteMMVAccessException(Throwable throwable) {
        super(throwable);
    }
}
