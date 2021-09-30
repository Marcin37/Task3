package com.Enigma.Task3.service;

import com.Enigma.Task3.domain.Invoice;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class StripeService {

    private final String BASIC_URL = "https://api.stripe.com/v1/invoices";
    private String apiKey;
    private final Logger LOGGER = LoggerFactory.getLogger(StripeService.class);
    private String apiKeyTest="sk_test_51JeqChJUazgIwnrrb0sbD2ioOLHWlPlKBxDBCMQAXLfjEKedIdCcnjQ0iRSgRjmanm3X48xMPwCHzUhZwOFzjw3z00J6KLUnDf";
    private RestTemplate restTemplate;

    public StripeService(RestTemplate restTemplate, @Value("${api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }


    private HttpHeaders createBasicHttpHeaders() {
        String base64Creds = encodeApiKey();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        LOGGER.info("Created a basic Http Header");
        return headers;
    }

    private String encodeApiKey() {
        String plainCreds = apiKey;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }

    public Object getAnInvoice(String invoiceId) {
        HttpEntity<?> httpEntity = new HttpEntity<>(createBasicHttpHeaders());
        String url = BASIC_URL + "/" + invoiceId;
        try {
            Object invoiceFromApi = restTemplate.postForObject(url, httpEntity,Object.class);
            LOGGER.info("Created an Invoice");
            return invoiceFromApi;
        } catch (HttpClientErrorException httpClientErrorException) {
            LOGGER.error("This invoiceId isn't correct " + httpClientErrorException);
            return "This invoiceId isn't correct";
        }
    }

    public void createAnInvoice(Invoice invoice) {
        MultiValueMap<String, String> map = createHeadersMap(invoice);
        HttpHeaders headers = createBasicHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<?> httpEntity = new HttpEntity<>(map, headers);
        LOGGER.info("Created a HTTP Entity Object");
        tryToPostForInvoice(httpEntity);
    }

    private boolean tryToPostForInvoice(HttpEntity<?> httpEntity) {
        try {
            restTemplate.postForObject(BASIC_URL, httpEntity, String.class);
            return true;
        } catch (HttpClientErrorException httpClientErrorException) {
            LOGGER.error("This values aren't allowed " + httpClientErrorException);
            return false;
        }
    }

    private MultiValueMap<String, String> createHeadersMap(Invoice invoice) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.convertValue(invoice, new TypeReference<Map<String, String>>() {
        });
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        map.entrySet()
                .stream()
                .forEach(entry -> multiValueMap.add(entry.getKey(), entry.getValue()));
        return multiValueMap;
    }
}
