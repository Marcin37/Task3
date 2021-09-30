package com.Enigma.Task3.service;

import com.Enigma.Task3.domain.Invoice;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final StripeService stripeService;
    public InvoiceService(StripeService stripeService){
        this.stripeService=stripeService;
    }
    public Object getAnInvoice(String invoiceId){
        return stripeService.getAnInvoice(invoiceId);
    }

    public void createAnInvoice(Invoice invoice){
        stripeService.createAnInvoice(invoice);
    }

}
