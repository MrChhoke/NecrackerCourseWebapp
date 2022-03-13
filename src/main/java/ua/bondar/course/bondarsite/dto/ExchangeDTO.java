package ua.bondar.course.bondarsite.dto;

import org.springframework.stereotype.Service;

@Service
public class ExchangeDTO {

    private ExchangeDTO(){}

    public static double exchangeUSDforUAH(double priceUSD){
        if(ExchangeClient.getCurrency() != null)
            return priceUSD * ExchangeClient.getCurrency().getCurrency().
                stream().filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH();
        return 0;
    }
}
