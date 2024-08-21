package org.hospital.service.invoice;

import org.hospital.dao.invoice.InvoiceDao;
import org.hospital.model.invoice.Invoice;

import java.util.List;


public class InvoiceService {

    private final InvoiceDao invoiceDao;

    public InvoiceService(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceDao.getAllInvoices();
    }

    public Invoice getInvoiceById(int id) {
        return invoiceDao.getInvoiceById(id);
    }

    public void saveInvoice(Invoice invoice) {
        invoiceDao.saveInvoice(invoice);
    }

    public void updateInvoice(Invoice invoice){
        invoiceDao.updateInvoice(invoice);
    }

    public void deleteInvoice(int id) {
        invoiceDao.deleteInvoice(id);
    }
}
