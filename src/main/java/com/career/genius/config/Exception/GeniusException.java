package com.career.genius.config.Exception;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-05-25 10:21
 * @discription
 **/
public class GeniusException extends Exception {
    private static final long serialVersionUID = 8348130244513472709L;

    public GeniusException() {
    }

    public GeniusException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
