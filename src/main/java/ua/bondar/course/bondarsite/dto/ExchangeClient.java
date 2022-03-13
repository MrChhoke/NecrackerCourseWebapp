package ua.bondar.course.bondarsite.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.exceptions.TemplateInputException;
import ua.bondar.course.bondarsite.model.CurrencyList;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
public class ExchangeClient {

    public static CurrencyList getCurrency() {
        String url = "http://localhost:8198/nbu?json";
        try {
            RestTemplate restTemplate = new RestTemplate();
            CurrencyList currencyList = restTemplate.getForObject(new URI(url), CurrencyList.class);
            return currencyList;
        } catch (Exception e) {
            log.error("Problem with ExchangeClient: " + e);
            return null;
        }
    }
}
