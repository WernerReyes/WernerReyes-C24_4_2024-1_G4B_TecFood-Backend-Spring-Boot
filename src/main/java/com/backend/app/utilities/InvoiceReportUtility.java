package com.backend.app.utilities;

import com.backend.app.persistence.entities.*;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class InvoiceReportUtility {

    private final OrderDishItemRepository orderDishItemRepository;

    public JasperPrint generateReport(
            UserEntity user,
            OrderDishEntity orderDish,
            PaymentEntity payment
    ) throws JRException {
        final InputStream reportStream = this.getClass().getResourceAsStream("/reports/UserInvoiceReport.jrxml");
        final JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        InputStream logoStream = this.getClass().getResourceAsStream("/reports/images/logoCompany.png");

        List<OrderDishItemEntity> orderDishItems = orderDishItemRepository.findByOrderDish(orderDish);

        String total = "S/." + payment.getAmount();

        // Generate report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoCompany", logoStream);
        parameters.put("firstName", user.getFirstName());
        parameters.put("lastName", user.getLastName());
        parameters.put("dni", user.getDni() != null ? user.getDni() : "No registered");
        parameters.put("phoneNumber", user.getPhoneNumber() != null ? user.getPhoneNumber() : "No registered");
        parameters.put("email", user.getEmail());
        parameters.put("status", payment.getStatus().name());
        parameters.put("subTotal", total);
        parameters.put("igv", String.valueOf(0));
        parameters.put("totalPayment", total);
        parameters.put("ds", new JRBeanArrayDataSource(
                orderDishItems.stream().map(orderDishItem -> new ReportEntity(
                orderDishItem.getId(),
                orderDishItem.getDish().getName(),
                orderDishItem.getQuantity(),
                orderDishItem.getDish().getPrice(),
                Math.round(orderDishItem.getQuantity() * orderDishItem.getDish().getPrice() * 100.0) / 100.0
        )
                ).toArray()
        ));

        return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

    }

    @Data
    @AllArgsConstructor
    public static class ReportEntity {
        private Long idDish;
        private String name;
        private int quantity;
        private Double price;
        private Double total;

    }



}
