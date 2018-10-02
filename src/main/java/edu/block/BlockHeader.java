package edu.block;

import edu.txn.Transaction;

import java.util.List;

public class BlockHeader {
    private String previousBlockHash;
    private long proof;
    private String txnsHash;

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public long getProof() {
        return proof;
    }

    public void setProof(long proof) {
        this.proof = proof;
    }

    public String getTxnsHash() {
        return txnsHash;
    }

    public void setTxnsHash(String txnsHash) {
        this.txnsHash = txnsHash;
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "previousBlockHash='" + previousBlockHash + '\'' +
                ", proof=" + proof +
                ", txnsHash='" + txnsHash + '\'' +
                '}';
    }
}
