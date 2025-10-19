package com.example.gestor_tareas.service;

import com.example.gestor_tareas.dto.QuoteDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String QUOTE_API_URL = "https://zenquotes.io/api/today";

    public QuoteDTO getQuoteOfTheDay() {
        try {
            QuoteDTO[] quotes = restTemplate.getForObject(QUOTE_API_URL, QuoteDTO[].class);
            if (quotes != null && quotes.length > 0) {
                return quotes[0];
            }
        } catch (Exception e) {
            System.err.println("Error fetching quote: " + e.getMessage());
        }
        
        QuoteDTO defaultQuote = new QuoteDTO();
        defaultQuote.setQ("La simplicidad es la máxima sofisticación.");
        defaultQuote.setA("Leonardo da Vinci");
        return defaultQuote;
    }
}