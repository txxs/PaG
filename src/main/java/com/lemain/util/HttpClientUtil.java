package com.lemain.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by jianghuimin on 2017/1/19.
 * http请求的工具类
 */
public class HttpClientUtil {

    private static final Logger log = getLogger(HttpClientUtil.class);

    public static final String UTF_8 = "UTF-8";
    /**
     *连接默认超时时间2秒
     */
    private static final int CONNECTION_TIMEOUT = 2000;

    /**
     * 服务器默认响应时间2秒
     */
    private static final int SOCKET_TIMEOUT = 2000;

    /**
     * 连接池最大连接数1000
     */
    private static final int MAX_CONNECTIONS = 1000;

    /**
     * 每个路由的最大连接数为200
     */
    private static final int MAX_PER_ROUTE = 200;

    /**
     * 是一个复杂的实现来管理客户端连接池，它也可以从多个执行线程中服务连接请求
     */
    private static PoolingHttpClientConnectionManager connectionManager;

    private static CloseableHttpClient httpClient;

    /**
     * 完成初始化操作，只要调用就可以，不用new
     */
    static{
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).setRetryHandler(new DefaultHttpRequestRetryHandler(3, false)).build();

        /**
         * 使用后台线程删除失效的连接和30秒以上没有使用的连接
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        synchronized (this) {
                            wait(5000);
                            // Close expired connections
                            connectionManager.closeExpiredConnections();
                            // Optionally, close connections
                            // that have been idle longer than 30 sec
                            connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                        }
                    }
                } catch (InterruptedException ex) {
                    log.info("IdleConnectionMonitorThread");
                }
            }
        });
    }

    public static String post(String uri, Map<String,Object> urlParams,Map<String,Object> headerMap,String entityBody,int connectionTimeout)throws  Exception{
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (urlParams != null && !urlParams.isEmpty()) {
            //uri = addParams(uri, urlParams, UTF_8);
            for (String o : urlParams.keySet()) {
                parameters.add(new BasicNameValuePair(o, urlParams.get(o).toString()));
            }

        }
        HttpPost post = new HttpPost(uri);
        if (!parameters.isEmpty()) {
            post.setEntity(new UrlEncodedFormEntity(parameters, UTF_8));
        }
        if (StringUtils.isNotBlank(entityBody)) {
            post.setEntity(new StringEntity(entityBody, UTF_8));
        }
        return request(post, urlParams, headerMap, connectionTimeout);
    }

    public static String get(String uri,Map<String,Object> urlParams,Map<String,Object> headerMap,int connectiontimeout){
        return null;
    }

    private static String request(HttpRequestBase reqsust, Map<String, Object> urlParams, Map<String, Object> headerMap,
                                  int connectionTimeout) throws ClientProtocolException, IOException {
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                reqsust.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        RequestConfig reqConf = RequestConfig.custom().setConnectTimeout(connectionTimeout == -1 ? CONNECTION_TIMEOUT : connectionTimeout).build();
        reqsust.setConfig(reqConf);
        long start = System.currentTimeMillis();
        HttpResponse httpResponse = httpClient.execute(reqsust);
        long cost = System.currentTimeMillis() - start;
        HttpEntity respEntity = httpResponse.getEntity();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (respEntity == null) {
            log.warn("{} response entity is null, url:{}, statusCode:{}, cost:{}",
                    new Object[]{reqsust.getMethod(), reqsust.getURI(), statusCode, cost});
            return null;
        }
        String resultString = EntityUtils.toString(respEntity, UTF_8);
        log.info("{} request, url:{}, statusCode:{}, Result:{}, cost:{}",
                new Object[]{reqsust.getMethod(), reqsust.getURI(), statusCode, resultString, cost});
        EntityUtils.consume(respEntity);
        return resultString;
    }
}
