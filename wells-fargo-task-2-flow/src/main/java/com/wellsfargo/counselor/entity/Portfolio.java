package com.wellsfargo.counselor.entity;

import jakarta.persistence.*;

import java.util.List;

public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Security> securities;

    public Portfolio(Client client, List<Security> securities) {
        this.client = client;
        this.securities = securities;
    }

    protected Portfolio() {}

    public Long getId() { return id; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public List<Security> getSecurities() { return securities; }

    public void setSecurities(List<Security> securities) { this.securities = securities; }
}


