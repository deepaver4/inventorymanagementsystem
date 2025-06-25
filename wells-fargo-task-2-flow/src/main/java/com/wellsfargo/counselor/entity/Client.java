package com.wellsfargo.counselor.entity;

import jakarta.persistence.*;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "advisor_id")
    private Advisor advisor;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Portfolio portfolio;

    protected Client() {}

    public Client(String name, String email, String phone, Advisor advisor, Portfolio portfolio) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.advisor = advisor;
        this.portfolio = portfolio;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public Advisor getAdvisor() { return advisor; }

    public void setAdvisor(Advisor advisor) { this.advisor = advisor; }

    public Portfolio getPortfolio() { return portfolio; }

    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }

}
