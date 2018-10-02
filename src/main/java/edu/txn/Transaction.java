package edu.txn;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
    private String sender;
    private String receiver;
    private long amount;

    public Transaction(){

    }

    public Transaction(String sender, String receiver, long amount){
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
