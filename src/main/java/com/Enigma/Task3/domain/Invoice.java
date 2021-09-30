package com.Enigma.Task3.domain;

import com.Enigma.Task3.service.StripeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;

public class Invoice {
    private String customer;
    private boolean auto_advance;
    private String collection_method;
    private final Logger LOGGER = LoggerFactory.getLogger(Invoice.class);

    public Invoice() {
    }

    public Invoice(String customer) {
        this.customer = customer;
    }

    public Invoice(String customer, boolean auto_advance, String collection_method) {
        this(customer);
        this.auto_advance = auto_advance;
        this.collection_method = collection_method;


    }

    public Invoice(String customer, boolean auto_advance) {
        this(customer);
        this.auto_advance = auto_advance;

    }

    public Invoice(String customer, String collection_method) {
        this(customer);
        this.collection_method = collection_method;
    }

    public String getCollection_method() {
        return collection_method;
    }

    public void setCollection_method(String collection_method) {
        this.collection_method = collection_method;
    }

    public boolean isAuto_advance() {
        return auto_advance;
    }

    public void setAuto_advance(boolean auto_advance) {
        this.auto_advance = auto_advance;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Invoice {" +
                "customer='" + customer + '\'' +
                ", auto_advance=" + auto_advance +
                ", collection_method='" + collection_method + '\'' +
                " }";
    }
}
