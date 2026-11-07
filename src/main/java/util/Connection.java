package util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Properties;

public class Connection {

    String target;

    HttpClient client ;









    /**
     * TLS hostname verification is ON by default. It can only be switched off explicitly for
     * local development (e.g. a self-signed certificate without an IP SAN) by starting the JVM
     * with -Dgossiper.tls.insecureSkipHostnameVerification=true. Never do this in production:
     * it allows any certificate holder to impersonate a backend service (man-in-the-middle).
     */
    static final String INSECURE_SKIP_HOSTNAME_VERIFICATION = "gossiper.tls.insecureSkipHostnameVerification";

    private void initConnectionProps()
    {
        if (Boolean.getBoolean(INSECURE_SKIP_HOSTNAME_VERIFICATION)) {
            final Properties props = System.getProperties();
            props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
            System.err.println("WARNING: TLS hostname verification disabled via -D" + INSECURE_SKIP_HOSTNAME_VERIFICATION);
        }
    }

    public Connection(String target)
    {
        this.target = target;
        initConnectionProps();
        client = HttpClient.newHttpClient();
    }



    public String send(String request,String action) throws Exception
    {
        return post(request,action);
    }


    public String send(String action) throws Exception
    {
        return get(action);
    }






    private String post(String str , String url) throws Exception
    {

        HttpRequest httprequest = HttpRequest.newBuilder()
                .uri(URI.create(target + url))
                .header("Content-Type", "application/json")
                 //.header("HMAC" , HMACUtil.calculateHMAC(str,HmacKey))
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .timeout(Duration.ofMinutes(1))
                .build();


        HttpResponse<String> response =
                client.send(httprequest, HttpResponse.BodyHandlers.ofString());

        return response.body();


    }



    public String get( String url) throws Exception
    {

        HttpRequest httprequest = HttpRequest.newBuilder()
                .uri(URI.create(target + url))
                .GET()
                .timeout(Duration.ofMinutes(1))
                .build();


        HttpResponse<String> response =
                client.send(httprequest, HttpResponse.BodyHandlers.ofString());

        return response.body();


    }







}
