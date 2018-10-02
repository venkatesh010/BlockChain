package edu.blockchain;

import edu.block.Block;
import edu.block.BlockHeader;
import edu.node.Node;
import edu.txn.Transaction;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class Blockchain {
    private List<Block> chain;
    private List<Transaction> currentTxns;
    private Node node;

    public List<Block> getChain() {
        return chain;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }

    public List<Transaction> getCurrentTxns() {
        return currentTxns;
    }

    public void setCurrentTxns(List<Transaction> currentTxns) {
        this.currentTxns = currentTxns;
    }

    private static Blockchain singleChain = null;

    private Blockchain(){
        chain = new ArrayList<>();
        currentTxns = new ArrayList<>();
        Block genesis = new Block();
        chain.add(genesis);
        node = Node.getInstance();
    }

    public static Blockchain getInstance(){
        if(singleChain == null){
            singleChain = new Blockchain();
        }
        return singleChain;
    }
    public Block mine(String prevHash) throws NoSuchAlgorithmException {
        Block block = new Block();
        BlockHeader header = new BlockHeader();

        this.currentTxns.add(new Transaction("0", node.getName(), 100));
        header.setPreviousBlockHash(prevHash);
        header.setTxnsHash(getHash(this.currentTxns));
        header.setProof(calculateProofOfWork(header));


        block.setIndex(chain.size());
        block.setTimestamp(LocalTime.now().toString());
        block.setTxns(new ArrayList<>(this.currentTxns));
        block.setBlockHeader(header);

        addNewBlock(block);
        this.currentTxns.clear();
        return block;
    }

    private List<Block> addNewBlock(Block block){
        this.chain.add(block);
        return this.chain;
    }

    public List<Transaction> addNewTxn(String sender, String receiver, long amount){
        Transaction txn = new Transaction(sender,receiver,amount);
        this.currentTxns.add(txn);
        return this.currentTxns;
    }

    public Block peek(){
        return this.chain.get(this.chain.size()-1);
    }

    private long calculateProofOfWork(BlockHeader header) throws NoSuchAlgorithmException {
        long proof = 0;
        String encoded;
        do{

            header.setProof(proof);
            encoded = getHash(header);

            proof += 1;
        }while(!encoded.substring(0,2).equals("00"));

        return proof-1;
    }

    public String getHash(Object object) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(object.toString().getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);

    }

    public boolean isValidChain(List<Block> chain) throws NoSuchAlgorithmException {

        int index = chain.size()-1;
        while(index <= 1){
            Block currentBlock = chain.get(index);
            Block prevBlock = chain.get(index-1);
            if(!(currentBlock.getBlockHeader().getPreviousBlockHash().equals(getHash(prevBlock)))){
                return false;
            }
        }
        return true;
    }


}
