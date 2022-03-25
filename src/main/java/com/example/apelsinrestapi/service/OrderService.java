package com.example.apelsinrestapi.service;

import com.example.apelsinrestapi.dto.*;
import com.example.apelsinrestapi.entity.*;
import com.example.apelsinrestapi.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
                           InvoiceRepository invoiceRepository, DetailRepository detailRepository,
                           ProductRepository productRepository) {
    public ApiResponse add(OrderDto orderDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(orderDto.getCustomerId());
        if (optionalCustomer.isEmpty()) {
            return new ApiResponse("NOT FOUND", false);
        }
        Order order = new Order();
        order.setCustomer(optionalCustomer.get());
        Order save = orderRepository.save(order);

        Invoice invoice = new Invoice();
        invoice.setAmount(orderDto.getAmount());
        invoice.setOrder(save);
        invoice.setDue(orderDto.getDue());
        invoiceRepository.save(invoice);

        List<DetailDto> details = orderDto.getDetails();
        if (details != null) {
            for (DetailDto detailDto : details) {
                Optional<Product> byId = productRepository.findById(detailDto.getProductId());
                if (byId.isEmpty()) {
                    return new ApiResponse("NOT FOUND", false);
                }
                Detail detail = new Detail();
                detail.setOrder(save);
                detail.setQuantity(detailDto.getQuantity());
                detail.setProduct(byId.get());
                detailRepository.save(detail);
            }
        }
        return new ApiResponse("ADDED", true);
    }

    public ApiResponse getCustomerLastOrder() {
        List<Order> orders = orderRepository.customerLastOrder();
        List<CustomerLastOrder> lastOrders = orders.stream().map(this::orderToLastOrder).toList();
        return new ApiResponse("Last order", true, lastOrders);
    }

    private CustomerLastOrder orderToLastOrder(Order order) {
        return new CustomerLastOrder(
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getDate()
        );
    }

    public ApiResponse getYearOrders() {
        List<Customer> customerList = customerRepository.getCustomer();
        List<CountryOrder> countryOrders = customerList.stream().map(this::orderToCountryOrder).toList();
        return new ApiResponse("Mana", true, countryOrders);
    }

    public CountryOrder orderToCountryOrder(Customer customer) {
        int count = 0;
        List<Order> orderList = orderRepository.findAllByCustomer_Id(customer.getId());
        for (Order order : orderList) {
            if (order.isActive()) {
                count++;
            }
        }
        return new CountryOrder(customer.getCountry(), count);
    }

    public ApiResponse getGeneralReports() {
        List<Detail> generalOrders = detailRepository.GeneralOrders();
        List<GeneralReport> generalReports = generalOrders.stream().map(this::detailToReport).toList();
        return new ApiResponse("Mana", true, generalReports);
    }

    public GeneralReport detailToReport(Detail detail) {
        return new GeneralReport(
                detail.getOrder().getId(),
                detail.getOrder().getDate(),
                detail.getQuantity() * detail.getProduct().getPrice()
        );
    }
}
