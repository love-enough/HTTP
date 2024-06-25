package org.study.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
/*
    HttpStatusCode
 */
public enum Status {
    OK("OK", 200),
    NOT_FOUND("Not Found", 404),
    INTERNAL_SERVER_ERROR("Internal Server Error", 500);

    private static Map<Integer, Status> codes = new HashMap<Integer, Status>();

    private Integer code;
    private String text;

    static {
        for(Status status : Status.values()) {
            codes.put(status.getCode(), status);
        }
    }

    //get the concrete data by code
    public static Status forCode(int code) {
        return codes.get(code);
    }

    //constructor
    private Status(String text, Integer code) {
        this.text = text;
        this.code = code;
    }

    //get code
    public int getCode() {
        return code;
    }
}
