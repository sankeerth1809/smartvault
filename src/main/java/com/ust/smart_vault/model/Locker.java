package com.ust.smart_vault.model;

import jakarta.persistence.*;

@Entity
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String lockerNumber;
    private String lockerPassword;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLockerNumber() {
        return lockerNumber;
    }

    public void setLockerNumber(String lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    public String getLockerPassword() {
        return lockerPassword;
    }

    public void setLockerPassword(String lockerPassword) {
        this.lockerPassword = lockerPassword;
    }

    public Card getOwner() {
        return owner;
    }

    public void setOwner(Card owner) {
        this.owner = owner;
    }
}
