package io.advantageous.qbit.example.servers;

import io.advantageous.qbit.http.HttpClient;
import io.advantageous.qbit.http.HttpRequest;
import io.advantageous.qbit.http.HttpTextResponse;
import org.boon.core.Sys;

import static io.advantageous.qbit.http.HttpClientBuilder.httpClientBuilder;
import static io.advantageous.qbit.http.HttpRequestBuilder.httpRequestBuilder;
import static org.boon.Boon.puts;

/**
 * Created by rhightower on 2/2/15.
 */
public class CPUIntensiveServerClient {


    static volatile int count = 0;

    public static void main(String... args) throws Exception {


        final HttpClient httpClient = httpClientBuilder()
                .setPort(8080).setPoolSize(500).setRequestBatchSize(100).setPipeline(true).setKeepAlive(true)
                .build().start();

        Sys.sleep(1_000);


        final long start = System.currentTimeMillis();


        final HttpRequest httpRequest = httpRequestBuilder().setUri("/services/myservice/ping").setTextResponse(new HttpTextResponse() {
            @Override
            public void response(int code, String mimeType, String body) {

                puts("body", body);
                count++;
            }
        }).build();


//        for (int index =  0; index < 10; index++) {
//            httpClient.sendHttpRequest(httpRequest);
//        }


        for (int index =  0; index < 100; index++) {

            final String key = "" + (index % 10);

            final HttpRequest httpRequestCPUKey1 = httpRequestBuilder().setUri("/services/myservice/addkey/").setTextResponse(new HttpTextResponse() {
                @Override
                public void response(int code, String mimeType, String body) {

                    puts("key", key, "body", body);
                    count++;
                }
            }).addParam("key", key).addParam("value", "hi mom ").build();

            httpClient.sendHttpRequest(httpRequestCPUKey1);
        }

        while (true) Sys.sleep(1000);



//
//        while (count < 490_000) {
//            Sys.sleep(100);
//            if (count > 100) {
//                puts(count);
//            }
//            if (count > 500) {
//                puts(count);
//            }
//
//            if (count > 750) {
//                puts(count);
//            }
//
//            if (count > 950) {
//                puts(count);
//            }
//            if (count > 2000) {
//                puts(count);
//            }
//
//            if (count > 10_000) {
//                puts(count);
//            }
//
//            if (count > 15_000) {
//                puts(count);
//            }
//
//
//            if (count > 100_000) {
//                puts(count);
//            }
//        }
//
//
//        final long stop = System.currentTimeMillis();
//
//        puts(count, stop - start);
    }
}
