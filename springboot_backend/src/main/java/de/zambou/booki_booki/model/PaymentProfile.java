package de.zambou.booki_booki.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_profiles")
public class PaymentProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;
    @Column(nullable = false, length = 120) private String recipient;
    @Column(nullable = false, length = 180) private String street;
    @Column(nullable = false, length = 20) private String postalCode;
    @Column(nullable = false, length = 100) private String city;
    @Column(nullable = false, length = 80) private String country;
    @Column(nullable = false, length = 30) private String paymentMethod;
    @Column(length = 120) private String paymentEmail;

    protected PaymentProfile() {}
    public PaymentProfile(Customer customer) { this.customer = customer; }
    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentEmail() { return paymentEmail; }
    public void setPaymentEmail(String paymentEmail) { this.paymentEmail = paymentEmail; }
}
