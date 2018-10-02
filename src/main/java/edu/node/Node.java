package edu.node;

import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
public class Node {

    private String name;
    private Set<String> peerNames;
    private static Node node = null;


    @Value("${server.port}")
    private int port;

    private Node() {
        System.out.println("PORT: "+port);
        name = "localhost:"+(Integer.toString(port));
        peerNames = new HashSet<>();
    }

    public static Node getInstance(){
        if(node == null){
            node = new Node();
        }
        return node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPeerNames() {
        return peerNames;
    }

    public void setPeers(Set<String> peerNames) {
        this.peerNames = peerNames;
    }
}
