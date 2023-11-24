package com.example.lab02.backend.models;

import com.example.lab02.backend.enums.CustomerStatus;
import com.example.lab02.backend.enums.EmployeeStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@AttributeOverride(name = "Id", column = @Column(name = "customer_id"))
@NamedQueries({
    @NamedQuery(name = "Customer.getCustomersActive", query = "SELECT c FROM Customer c WHERE c.status = :status"),
    @NamedQuery(name = "Customer.deleteCustomer", query = "UPDATE Customer c SET c.status = 'IN_ACTIVE' WHERE c.id = :id")
})
public class Customer extends Person{
    @Column(name = "status", columnDefinition = "enum('ACTIVE', 'IN_ACTIVE')")
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Customer(String fullName, LocalDateTime dob, String email, String phone, String address, CustomerStatus status) {
        super(fullName, dob, email, phone, address);
        this.status = status;
    }

    public Customer(){
        super();
    }

    public Customer(long id){
        super(id);
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
