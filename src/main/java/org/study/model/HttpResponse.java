package org.study.model;

import blade.kit.IOKit;
import blade.kit.StringKit;
import blade.kit.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/*
    response data
 */
public class HttpResponse {

    private String version = "HTTP/1.1";
    private Status status = Status.OK;
    private String reason = "";
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private InputStream response;
    private String body;
    private long length = 0;

    public HttpResponse() {}

    public HttpResponse(Status status, String body) {
        this.status = status;
        this.body = body;
        this.length = body.length();
    }

    public HttpResponse(Status status, InputStream inputStream) {
        this.status = status;
        this.response = inputStream;
        try {
            this.length = inputStream.available();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDefaultHeaders() {
        headers.put(Header.DATE, new Date().toString());
        headers.put(Header.SERVER, "gzh and lly");
        headers.put(Header.CONTENT_LENGTH, length + "");
        headers.put(Header.CONNECTION, "close");
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Status getStatus() {
        return status;
    }

    public int GetStatusCode() {
        return status.getCode();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpResponse reason(String reason) {
        this.reason = reason;
        return this;
    }

    public HttpResponse reason(Status httpStatus, String reason) {
        this.status = httpStatus;
        this.reason = reason;
        return this;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public InputStream getResponse() {
        return response;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] bytes() {
        if(StringKit.isNotBlank(body)) {
            return body.getBytes(StandardCharsets.UTF_8);
        }
            if(response != null) {
                try {
                    return IOKit.toByteArray(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        return null;
    }

    public String body() {
        if (StringKit.isNotBlank(body)) {
            return body;
        }
        if(response != null) {
            try {
                return CharStreams.toString(new InputStreamReader(response, "UTF_8"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
