package org.hospital.controller.invoice;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.invoice.Invoice;
import org.hospital.service.invoice.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceController implements IController {
    private final InvoiceService invoiceService;

    private final Logger logger = LoggerFactory.getLogger(InvoiceController.class);


    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/invoices", ctx -> {
            ctx.json(invoiceService.getAllInvoices());
            logger.info("All invoices retrieved successfully");
        });

        app.get("/invoice/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Invoice invoice = invoiceService.getInvoiceById(id);
            if (invoice != null) {
                ctx.json(invoice);
                logger.info("Invoice successfully retrieved with ID {}", id);
            } else {
                ctx.status(404).json("Invoice not found");
                logger.debug("Invoice not found with ID {}", id);
            }
        });

        app.post("/insert-invoice", ctx -> {
            Invoice invoice = ctx.bodyAsClass(Invoice.class);
            invoiceService.saveInvoice(invoice);
            ctx.status(201).json("Invoice added successfully");
            logger.info("Invoice added successfully");
        });

        app.put("/update-invoice/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Invoice invoice = ctx.bodyAsClass(Invoice.class);
            invoice.setInvoiceId(id);
            invoiceService.updateInvoice(invoice);
            ctx.status(200).json("Invoice updated successfully");
            logger.info("Invoice updated successfully with ID {}", id);
        });


        app.delete("/delete-invoice/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            invoiceService.deleteInvoice(id);
            ctx.status(200).json("Invoice deleted successfully");
            logger.info("Invoice deleted successfully with ID {}", id);
        });
    }
}
