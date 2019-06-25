package com.sharingif.cube.communication.http.transport;

import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.http.request.DynamicUrlRequest;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.request.RequestContextResolver;

import java.util.Locale;

public class DynamicUrlRequestContextResolver implements RequestContextResolver<Object[], RequestContext<Object[]>> {

    @Override
    public RequestContext<Object[]> resolveRequest(Object[] request) {
        Object[] args = (Object[]) request[1];
        DynamicUrlRequest DynamicUrlRequest = (DynamicUrlRequest)args[0];

        RequestContext<Object[]> requestContext = new RequestContext<Object[]>(MediaType.APPLICATION_JSON.toString(), DynamicUrlRequest.getUrl(), Locale.getDefault(), RequestMethod.POST.name(), new Object[]{DynamicUrlRequest.getBody()});

        return requestContext;
    }

}
