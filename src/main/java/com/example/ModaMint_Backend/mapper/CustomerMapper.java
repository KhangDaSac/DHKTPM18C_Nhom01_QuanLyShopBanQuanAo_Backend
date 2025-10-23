package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.customer.CustomerRequest;
import com.example.ModaMint_Backend.dto.response.customer.CustomerResponse;
import com.example.ModaMint_Backend.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomerMapper {
    
    @Mapping(target = "user", ignore = true)
    Customer toCustomer(CustomerRequest request);
    
    @Mapping(target = "addresses", expression = "java(customer.getAddresses() != null ? customer.getAddresses().stream().map(address -> CustomerResponse.AddressResponse.builder().id(address.getId()).city(address.getCity()).ward(address.getWard()).addressDetail(address.getAddressDetail()).build()).collect(java.util.stream.Collectors.toSet()) : null)")
    @Mapping(target = "cart", expression = "java(customer.getCart() != null ? CustomerResponse.CartResponse.builder().id(customer.getCart().getId()).sessionId(customer.getCart().getSessionId()).build() : null)")
    @Mapping(target = "orders", expression = "java(customer.getOrders() != null ? customer.getOrders().stream().map(order -> CustomerResponse.OrderResponse.builder().id(order.getId()).orderCode(order.getOrderCode()).orderStatus(order.getOrderStatus() != null ? order.getOrderStatus().name() : null).paymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null).build()).collect(java.util.stream.Collectors.toSet()) : null)")
    @Mapping(target = "reviews", expression = "java(customer.getReviews() != null ? customer.getReviews().stream().map(review -> CustomerResponse.ReviewResponse.builder().id(review.getId()).rating(review.getRating()).comment(review.getComment()).build()).collect(java.util.stream.Collectors.toSet()) : null)")
    CustomerResponse toCustomerResponse(Customer customer);
}
