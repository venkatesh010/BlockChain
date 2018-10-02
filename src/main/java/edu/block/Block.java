package edu.block;

import edu.txn.Transaction;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;
import java.util.List;

@XmlRootElement
public class Block {
    private long index;
    private BlockHeader blockHeader;
    private String timestamp;
    private List<Transaction> txns;
    public Block(){
        this.index = 0;
        this.timestamp = LocalTime.now().toString();
    }

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public List<Transaction> getTxns() {
        return txns;
    }

    public void setTxns(List<Transaction> txns) {
        this.txns = txns;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", blockHeader=" + blockHeader +
                ", timestamp=" + timestamp +
                ", txns=" + txns +
                '}';
    }
}
