package org.hospital.dao.invoice;


import org.hospital.model.invoice.Invoice;

import java.util.List;

public interface InvoiceDao {
    List<Invoice> getAllInvoices();

    Invoice getInvoiceById(int id);

    void saveInvoice(Invoice invoice);

    void updateInvoice(Invoice invoice);
    void deleteInvoice(int id);
}
