package com.example

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking

class ProxyFunction : HttpFunction {

    override fun service(request: HttpRequest, httpResponse: HttpResponse) {
        httpResponse.appendHeader("Access-Control-Allow-Origin", "*")

        // Cors headers to accept calls from any websites
        if ("OPTIONS" == request.method) {
            httpResponse.appendHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS")
            httpResponse.appendHeader("Access-Control-Allow-Headers", "*")
            httpResponse.appendHeader("Access-Control-Max-Age", "3600")
            httpResponse.setStatusCode(HttpStatusCode.NoContent.value)
            return
        }

        // Retrieve body parameters
        // val gson = Gson()
        // val body: JsonObject? = gson.fromJson(request.reader, JsonObject::class.java)

        // Get query parameters
        val city = request.queryParameters?.get("city")?.firstOrNull() ?: "Amsterdam"

        runBlocking {
            val url = "https://goweather.herokuapp.com/weather/$city"
            val httpClient = basicHttpClient()
            val response = httpClient.get(url)

            httpResponse.outputStream.write(response.readBytes())
            response.headers.forEach { key, values ->
                values.forEach { value ->
                    httpResponse.appendHeader(key, value)
                }
            }
            httpResponse.setStatusCode(response.status.value)
        }
    }

    private fun basicHttpClient(): HttpClient = HttpClient {
        install(DefaultRequest)
        install(HttpTimeout) {
            socketTimeoutMillis = 30_000
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }
        install(ContentNegotiation) {
            json()
        }
    }
}