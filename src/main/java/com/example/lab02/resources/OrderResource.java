package com.example.lab02.resources;

import com.example.lab02.converters.ObjectMapperContextResolver;
import com.example.lab02.models.Order;
import com.example.lab02.serializes.OrderSerializer;
import com.example.lab02.services.OrderDetailService;
import com.example.lab02.services.OrderService;
import com.example.lab02.services.impl.OrderDetailServiceImpl;
import com.example.lab02.services.impl.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Path("orders")
public class OrderResource {
    private final OrderService service;
    private final OrderDetailService orderDetailService;
    private
    OrderResource(){
        service = new OrderServiceImpl();
        orderDetailService = new OrderDetailServiceImpl();
    }
    @GET
    @Produces("application/json")
    public Response getAllOrder(){
        return Response.ok(service.getAll(Order.class)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") long oid){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("OrderSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(Order.class, new OrderSerializer());
        mapper.registerModule(module);
        Optional<Order> order = service.get(oid, Order.class);
        if (order.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        try {
            String orderStr = mapper.writeValueAsString(order.get());
            return Response.ok(orderStr).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response insert(Order order) {
        if (order == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        service.insert(order);
        return Response.ok("Thêm thành công").build();
    }

//    @POST
//    @Produces("application/json")
//    @Consumes("application/json")
//    @Path("/{id}")
//    public Response updateOrder(@ PathParam("id") long id,Order order){
//        service.update(order);
//        return Response.ok(order).build();
//    }

    @GET
    @Produces("application/json")
    @Path("/{id}/detail")
    public Response getOrderDetail(@PathParam("id") long id){
        return Response.ok(orderDetailService.getListOrderDetailByOrder(id)).build();
    }
}
