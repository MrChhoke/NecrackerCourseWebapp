package ua.bondar.course.bondarsite.dto;

import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.CurrencyList;

@Service
public class ExchangeDTO {

    private ExchangeDTO(){}

    public static double exchangeUSDforUAH(double priceUSD){
        CurrencyList list = ExchangeClient.getCurrency();
        if(list != null)
            return priceUSD * list.getCurrency().
                stream().filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH();
        return -1;
    }
}
