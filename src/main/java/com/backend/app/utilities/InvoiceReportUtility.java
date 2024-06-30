package com.backend.app.utilities;

import com.backend.app.persistence.entities.*;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InvoiceReportUtility {

    @Autowired
    private OrderDishItemRepository orderDishItemRepository;

    public JasperPrint generateReport(
            UserEntity user,
            OrderDishEntity orderDish,
            PaymentEntity payment
    ) throws FileNotFoundException, JRException {



        final File file = ResourceUtils.getFile("classpath:reports/UserInvoiceReport.jrxml");
        final JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        final File logoCompany = ResourceUtils.getFile("classpath:reports/images/logoCompany.png");

        List<OrderDishItemEntity> orderDishItems = orderDishItemRepository.findByOrderDish(orderDish);

        String total = "S/." + payment.getAmount();

        // Generate report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoCompany", new FileInputStream(logoCompany));
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
