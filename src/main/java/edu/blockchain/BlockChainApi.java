package edu.blockchain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.block.Block;
import edu.node.Node;
import edu.txn.Transaction;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

@RestController
public class BlockChainApi {

    private Blockchain blockChain = Blockchain.getInstance();
    private Node node = Node.getInstance();

    @RequestMapping(path = "/mine", method= RequestMethod.GET, produces = "application/json")
    public Block mineBlock() throws NoSuchAlgorithmException {
        return blockChain.mine(blockChain.getHash(blockChain.peek()));
    }

    @RequestMapping(path = "/chain", method= RequestMethod.GET, produces="application/json")
    public List<Block> getChain(){
        return blockChain.getChain();
    }

    @RequestMapping(method = RequestMethod.POST, path="/addTransaction", produces = "application/json")
    public List<Transaction> addTransaction(@RequestBody Transaction Txn){
        //System.out.println(Txn.getReceiver());
        blockChain.getCurrentTxns().add(Txn);
        return blockChain.getCurrentTxns();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/registerPeers", produces = "application/json")
    public Set<String> registerNodes(@RequestBody Node newNode){

        Set<String> peers = node.getPeerNames();
        if(!peers.contains(newNode.getName())) {
            peers.add(newNode.getName());
        if((newNode.getPeerNames() != null) && (newNode.getPeerNames().size()>0)) {
            peers.addAll(newNode.getPeerNames());
        }
        }
        return peers;
    }

    @RequestMapping(method=RequestMethod.GET, path="/resolve")
    public String  resolveConflicts() throws IOException {
        String request;
        boolean foundConflict = false;
        if(node.getPeerNames()!= null && node.getPeerNames().size()>0){
            for(String address: node.getPeerNames()){
                request = "http://" + address+"/chain";
                List<Block> chainOfPeer = hitTheRequest(request);
                if(chainOfPeer.size() > blockChain.getChain().size()){
                    blockChain.setChain(chainOfPeer);
                    foundConflict = true;
                }
            }
        }

        if(foundConflict){
            return "Conflict was found and it has been resolved by changing the chain, please see new chain by hitting /chain";
        }
        else{
            return "No conflict, Your chain is the longest one";
        }
    }

    private List<Block> hitTheRequest(String request) throws IOException {
        List<Block> items=null;
        URL url = new URL(request);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
            //System.out.println("Conn"+ con.getInputStream());
            items = parseResponse(con.getInputStream());
        }
        return items;
    }

    private List<Block> parseResponse(InputStream in) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(in);
        //System.out.println("Content: "+ json.toString());
        return mapper.readValue(
                json.toString(),
                mapper.getTypeFactory().constructParametricType(List.class, Block.class)
        );
    }

}
