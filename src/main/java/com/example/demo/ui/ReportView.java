package com.example.demo.ui;

import com.example.demo.backend.Book;
import com.example.demo.backend.BookService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.vaadin.reports.PrintPreviewReport;

import javax.annotation.security.RolesAllowed;

@Route("report")
@RolesAllowed("ADMIN")
public class ReportView extends VerticalLayout {

    public ReportView(BookService service) {
        var report = new PrintPreviewReport<>(Book.class, "title", "published", "rating");
        report.setItems(service.findAll());
        report.getReportBuilder().setTitle("Books");

        StreamResource pdf = report.getStreamResource("books.pdf", service::findAll, PrintPreviewReport.Format.PDF);
        StreamResource csv = report.getStreamResource("books.csv", service::findAll, PrintPreviewReport.Format.CSV);

        add(
                new HorizontalLayout(
                        new Anchor(pdf, "PDF"),
                        new Anchor(csv, "CSV")
                ),
                report
        );
    }
}
