package com.juno.ounapi.common.httpclient;

import com.juno.ounapi.common.httpclient.vo.PostRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class MyHttpClient {

    public HttpResponse<String> httpPostRequest(PostRequest postRequest) {
        HttpResponse<String> response = null;
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(postRequest.getUri()))
                .headers(postRequest.getHeaders())
                .POST(HttpRequest.BodyPublishers.ofString(postRequest.getBody()))
                .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("response body = {}", response.body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
