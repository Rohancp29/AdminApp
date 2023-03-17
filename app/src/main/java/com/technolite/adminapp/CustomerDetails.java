package com.technolite.adminapp;

public class CustomerDetails {
    private String custName;
    private String custMob;
    private String custAddress;
    private String custSubscription;
    private String custPayment;
    private String custJoinDate;

    public CustomerDetails(String custName, String custMob, String custAddress, String custSubscription, String custPayment, String custJoinDate) {
        this.custName = custName;
        this.custMob = custMob;
        this.custAddress = custAddress;
        this.custSubscription = custSubscription;
        this.custPayment = custPayment;
        this.custJoinDate = custJoinDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustMob() {
        return custMob;
    }

    public void setCustMob(String custMob) {
        this.custMob = custMob;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustSubscription() {
        return custSubscription;
    }

    public void setCustSubscription(String custSubscription) {
        this.custSubscription = custSubscription;
    }

    public String getCustPayment() {
        return custPayment;
    }

    public void setCustPayment(String custPayment) {
        this.custPayment = custPayment;
    }

    public String getCustJoinDate() {
        return custJoinDate;
    }

    public void setCustJoinDate(String custJoinDate) {
        this.custJoinDate = custJoinDate;
    }
}
