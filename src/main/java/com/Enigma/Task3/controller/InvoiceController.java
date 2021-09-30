package com.Enigma.Task3.controller;

import com.Enigma.Task3.domain.Invoice;
import com.Enigma.Task3.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

@RestController
public class InvoiceController {

    private final InvoiceService INVOICE_SERVICE;

    public InvoiceController(InvoiceService invoiceService){
        this.INVOICE_SERVICE=invoiceService;
    }

    @GetMapping("/get/{invoiceId}")
    public Object GetAnInvoice(@PathVariable String invoiceId){

        return  INVOICE_SERVICE.getAnInvoice(invoiceId);
    }
    @PostMapping("/post/{customerId}")
    public void createAnInvoice(@PathVariable String customerId, @RequestBody Invoice invoice){
        invoice.setCustomer(customerId);
        INVOICE_SERVICE.createAnInvoice(invoice);
    }

}
