package http;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by astrate on 03.10.2014.
 */
public class Get {

    private final String username;
    private final String password;
    private final String baseUrl;

    public Get(String username, String password, String baseUrl) {
        this.username = username;
        this.password = password;
        if (baseUrl.endsWith("/")) {
            this.baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        } else {
            this.baseUrl = baseUrl;
        }
    }

    public JSONObject execute(String urlString) throws IOException, ParseException {
    	URL url = new URL(baseUrl);
    	HttpHost httpHost = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        
        AuthCache authCache = new BasicAuthCache();
        authCache.put(httpHost, new BasicScheme());
        BasicHttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.AUTH_CACHE, authCache);
        
        HttpGet get = new HttpGet(baseUrl + "/" + urlString);

        get.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpHost, get, httpContext);

        System.out.println("\nSending 'GET' request to URL : " + baseUrl + "/" + urlString);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return (JSONObject) new JSONParser().parse(result.toString());
    }
}
