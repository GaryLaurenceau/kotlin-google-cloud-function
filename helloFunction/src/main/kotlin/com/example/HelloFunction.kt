package com.example

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse

class HelloFunction : HttpFunction {

    override fun service(request: HttpRequest, httpResponse: HttpResponse) {
        httpResponse.appendHeader("Access-Control-Allow-Origin", "*")

        // Cors headers to accept calls from any websites
        if ("OPTIONS" == request.method) {
            httpResponse.appendHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS")
            httpResponse.appendHeader("Access-Control-Allow-Headers", "*")
            httpResponse.appendHeader("Access-Control-Max-Age", "3600")
            httpResponse.setStatusCode(204)
            return
        }

        httpResponse.writer.write("Hello world!")
        httpResponse.setStatusCode(200)
    }
}