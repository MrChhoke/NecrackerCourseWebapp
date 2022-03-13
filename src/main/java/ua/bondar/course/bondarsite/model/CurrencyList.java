package ua.bondar.course.bondarsite.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Scope("prototype")
public class CurrencyList {
    @JacksonXmlElementWrapper(localName = "listCurrency")
    private List<Currency> currency;
    private String date;
}