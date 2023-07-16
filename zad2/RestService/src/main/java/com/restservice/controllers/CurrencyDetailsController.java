package com.restservice.controllers;

import com.restservice.models.CurrencyDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tools.JSONReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.validation.ConstraintViolationException;

@Controller
@Validated
public class CurrencyDetailsController {

    private final JSONReader jsonReader;

    public CurrencyDetailsController() {
        jsonReader = new JSONReader();
    }


    @GetMapping(value = "/currencies/detailsform")
    public String allCryptoForm() {
        return "details-form";
    }

    @RequestMapping(value = "/currencies/detailedresults", method = RequestMethod.GET)
    public String allCryptoSubmit(
            @RequestParam(value = "vc")
            @NotEmpty
            @Valid
            String valueCurrency,
            @RequestParam(value = "name") @NotEmpty @Valid String searchQuery,
            Model model) throws IOException {
        URL urlAll = new URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=" + valueCurrency + "&order=market_cap_desc&per_page=250&page=1&sparkline=false");
        HttpURLConnection connection = (HttpURLConnection) urlAll.openConnection();
        String crypto_id = getResponseWithID(connection, searchQuery);
        switch (crypto_id) {
            case "":
            case "error-400":
                return "error-400";
            case "error-404":
                return "error-404";
            case "error-500":
                return "error-500";
            case "error":
                return "error";
        }
        URL urlDetails = new URL("https://api.coingecko.com/api/v3/coins/" + crypto_id);
        HttpURLConnection connection1 = (HttpURLConnection) urlDetails.openConnection();
        return getResponse(connection1, valueCurrency, model);
    }

    private String getResponseWithID(HttpURLConnection connection, String searchQuery) throws IOException {
        System.out.println(connection.getURL());
        switch (connection.getResponseCode()) {
            case HttpURLConnection.HTTP_OK:
                JSONArray response = jsonReader.getJSONArray(connection);
                return getCryptoID(response, searchQuery);
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

    public String getCryptoID(JSONArray currenciesJSON, String searchQuery) {
        String id = "";
        for (int i = 0; i < currenciesJSON.length(); i++) {
            if (currenciesJSON.getJSONObject(i).getString("name").equalsIgnoreCase(searchQuery)) {
                id = currenciesJSON.getJSONObject(i).getString("id");
                break;
            }
        }
        return id;
    }

    private String getResponse(HttpURLConnection connection, String valueCurrency, Model model) throws IOException {
        System.out.println(connection.getURL());
        switch (connection.getResponseCode()) {
            case HttpURLConnection.HTTP_OK:
                JSONObject response = jsonReader.getJSONObject(connection);
                getProcessedData(response, valueCurrency, model);
                return "detailed-results";

            case HttpURLConnection.HTTP_NOT_FOUND:
                return "error-404";

            case HttpURLConnection.HTTP_BAD_REQUEST:
                return "error-400";

            default:
                return "error";
        }

    }

    private void getProcessedData(JSONObject currencyJSON, String valueCurrency, Model model) {
        JSONObject descriptions = currencyJSON.getJSONObject("description");
        JSONObject marketData = currencyJSON.getJSONObject("market_data");
        JSONObject prices = marketData.getJSONObject("current_price");
        JSONObject marketCap = marketData.getJSONObject("market_cap");
        CurrencyDetails currency = new CurrencyDetails(currencyJSON.getString("name"),
                descriptions.getString("en"), prices.getDouble(valueCurrency), marketCap.getLong(valueCurrency));
        model.addAttribute("currency", currency);
        model.addAttribute("vc", valueCurrency);
    }


    @ExceptionHandler
    public String handleException(ConstraintViolationException ex) {
        return "details-form";

    }
}
