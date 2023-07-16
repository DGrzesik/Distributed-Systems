package com.restservice.controllers;
import com.restservice.models.CurrencyBrief;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tools.JSONReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jakarta.validation.ConstraintViolationException;

@Controller
@Validated
public class CurrencyBriefController {

    private final JSONReader jsonReader;

    public CurrencyBriefController() {
        jsonReader = new JSONReader();
    }

    @GetMapping("/currencies")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/currencies/form")
    public String allCryptoForm() {
        return "form";
    }

    @RequestMapping(value = "/currencies/results", method = RequestMethod.GET)
    public String allCryptoSubmit(
            @RequestParam(value = "amt", required = false)
            @Valid
            @Min(value = 1, message = "Choose the amount of results between 1-250")
            @Max(value = 250, message = "Choose the amount of results between 1-250")
            Integer amount,
            @RequestParam(value = "vc")
            @NotEmpty
            @Valid
            String valueCurrency,
            @RequestParam(value = "name") String searchQuery,
            Model model) throws IOException {
        URL url = new URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=" + valueCurrency + "&order=market_cap_desc&per_page=250&page=1&sparkline=false");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return getResponse(connection, searchQuery, amount, valueCurrency, model);
    }


    private String getResponse(HttpURLConnection connection, String searchQuery, Integer amount, String valueCurrency, Model model) throws IOException {
        System.out.println(connection.getURL());
        switch (connection.getResponseCode()) {
            case HttpURLConnection.HTTP_OK:
                JSONArray response = jsonReader.getJSONArray(connection);
                getProcessedBriefData(response, searchQuery, amount, valueCurrency, model);
                return "results";

            case HttpURLConnection.HTTP_NOT_FOUND:
                return "error-404";

            case HttpURLConnection.HTTP_BAD_REQUEST:
                return "error-400";
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                return "error-500";
            default:
                return "error";
        }

    }

    private void getProcessedBriefData(JSONArray currenciesJSON, String searchQuery, Integer amount, String valueCurrency, Model model) {
        List<CurrencyBrief> currenciesByName = new ArrayList<>();
        List<CurrencyBrief> currencies = new ArrayList<>();
        for (int i = 0; i < currenciesJSON.length(); i++) {
            CurrencyBrief currency = new CurrencyBrief(currenciesJSON.getJSONObject(i).getString("name"), currenciesJSON.getJSONObject(i).getDouble("current_price"), currenciesJSON.getJSONObject(i).getDouble("price_change_percentage_24h"));
            if (currenciesJSON.getJSONObject(i).getString("name").toLowerCase().startsWith(searchQuery.toLowerCase())) {
                currenciesByName.add(currency);
            }
            currencies.add(currency);
        }

        List<CurrencyBrief> currenciesPriceSorted = new ArrayList<>(currencies);
        List<CurrencyBrief> currenciesPriceChangePercentageSorted = new ArrayList<>(currencies);

        Collections.sort(currenciesPriceSorted, new Comparator<CurrencyBrief>() {
            public int compare(CurrencyBrief curr1, CurrencyBrief curr2) {
                return Double.compare(curr2.getPrice(), curr1.getPrice());
            }
        });
        Collections.sort(currenciesPriceChangePercentageSorted, new Comparator<CurrencyBrief>() {
            public int compare(CurrencyBrief curr1, CurrencyBrief curr2) {
                return Double.compare(curr2.getPriceChangePercentage24h(), curr1.getPriceChangePercentage24h());
            }
        });

        for (CurrencyBrief currency : currenciesByName) {
            currency.setPriceRank(currenciesPriceSorted.indexOf(currency));
            currency.setPriceChangePercentage24hRank(currenciesPriceChangePercentageSorted.indexOf(currency));
        }
        if (amount != null && !currenciesByName.isEmpty()) {
            currenciesByName = currenciesByName.subList(0, amount);
        }
        int currenciesAmount = currenciesByName.size();
        model.addAttribute("currenciesAmount", currenciesAmount);
        model.addAttribute("vc", valueCurrency);
        model.addAttribute("currencies", currenciesByName);
    }


    @ExceptionHandler
    public String handleException(ConstraintViolationException ex) {
        return "form";
    }
}