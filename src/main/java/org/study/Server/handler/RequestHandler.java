package org.study.Server.handler;

import org.study.model.HttpRequest;
import org.study.model.HttpResponse;

public interface RequestHandler {
    public HttpResponse handle(HttpRequest request);
}
