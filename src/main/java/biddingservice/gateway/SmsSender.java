package biddingservice.gateway;

import biddingservice.Constants;
import com.google.inject.Inject;
import okhttp3.*;
import java.io.IOException;


public class SmsSender implements Sender {
    private OkHttpClient okHttpClient;
    private String url;
    private String authToken;

    @Inject
    public SmsSender(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        this.url = Constants.Gateway.TWILIO_URL;
        this.authToken = Constants.Gateway.TWILIO_AUTH_TOKEN;
    }

    @Override
    public void send(String receiver) throws IOException {

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = prepareRequest(url, authToken);
        Response response = makeCall(request);
    }

    private Response makeCall(Request request) throws IOException {
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    private Request prepareRequest(String url, String receiver) {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("To", Constants.Gateway.RECEIVER)
                .addFormDataPart("Channel", "sms")
                .build();
        return new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Authorization", String.format("Basic %s", authToken))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }

}
