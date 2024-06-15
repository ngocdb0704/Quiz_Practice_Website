/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author admin
 */
public class Transaction {
    private String transactionCode;
    private String transactionTime;
    private String transactionAccount;
    private String transactionContent;
    private float transactionMoney;
    private int transactionStatus;

    public Transaction() {
    }

    public Transaction(String transactionCode, String transactionTime, String transactionAccount, String transactionContent, float transactionMoney, int transactionStatus) {
        this.transactionCode = transactionCode;
        this.transactionTime = transactionTime;
        this.transactionAccount = transactionAccount;
        this.transactionContent = transactionContent;
        this.transactionMoney = transactionMoney;
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getTransactionAccount() {
        return transactionAccount;
    }

    public String getTransactionContent() {
        return transactionContent;
    }

    public float getTransactionMoney() {
        return transactionMoney;
    }

    public int getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public void setTransactionAccount(String transactionAccount) {
        this.transactionAccount = transactionAccount;
    }

    public void setTransactionContent(String transactionContent) {
        this.transactionContent = transactionContent;
    }

    public void setTransactionMoney(float transactionMoney) {
        this.transactionMoney = transactionMoney;
    }

    public void setTransactionStatus(int transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

}
