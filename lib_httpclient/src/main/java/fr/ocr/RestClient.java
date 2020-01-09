/*
* componet
* gere les échanges entre service - lib : HttpClient
 */

package fr.ocr;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

@Component
@Getter
public class RestClient {

    private  HttpClient httpClient;

    public RestClient() {
        httpClient = HttpClient
                .newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public Builder requestBuilder(URI uri, Map<String, String> additionalHeaders) {

        Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json");

        if ((additionalHeaders != null) && !additionalHeaders.isEmpty()) additionalHeaders.forEach(builder::header);

        return builder;
    }

    public HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
