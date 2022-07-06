package ua.bondar.course.bondarsite.dto;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.bondar.course.bondarsite.model.currency.CurrencyList;

import java.net.URI;

@Service
@Slf4j
@Data
public class ExchangeClient {

    private static String url;

    private static int timeoutConnectionMilliseconds;

    @Value("${api.exchange.time.connection.milliseconds}")
    public void setStaticTimeoutConnectionMilliseconds(int nonStaticTimeoutConnectionMilliseconds){
        ExchangeClient.timeoutConnectionMilliseconds = nonStaticTimeoutConnectionMilliseconds;
    }

    @Value("${api.exchange.url.json}")
    public void setStaticUrl(String nonStaticUrl){
        ExchangeClient.url = nonStaticUrl;
    }

    public static CurrencyList getCurrency() {
        try {
            SimpleClientHttpRequestFactory clientHttpRequestFactory
                    = new SimpleClientHttpRequestFactory();
            clientHttpRequestFactory.setConnectTimeout(timeoutConnectionMilliseconds);
            clientHttpRequestFactory.setReadTimeout(timeoutConnectionMilliseconds);
            RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
            return restTemplate.getForObject(new URI(url), CurrencyList.class);
        } catch (Exception e) {
            log.error("Problem with ExchangeClient: " + e);
            return null;
        }
    }
}
