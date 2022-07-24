package com.ecommerce.blockchain.transaction;

import com.ecommerce.blockchain.util.ByteUtils;
import com.google.common.collect.Lists;
import lombok.Data;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;


@Data
public class MerkleTree {

   
    private Node root;
   
    private byte[][] leafHashes;

    public MerkleTree(byte[][] leafHashes) {
        constructTree(leafHashes);
    }

    
    private void constructTree(byte[][] leafHashes) {
        if (leafHashes == null || leafHashes.length < 1) {
            throw new RuntimeException("ERROR:Fail to construct merkle tree ! leafHashes data invalid ! ");
        }
        this.leafHashes = leafHashes;
        List<Node> parents = bottomLevel(leafHashes);
        while (parents.size() > 1) {
            parents = internalLevel(parents);
        }
        root = parents.get(0);
    }

    
    private List<Node> internalLevel(List<Node> children) {
        List<Node> parents = Lists.newArrayListWithCapacity(children.size() / 2);
        for (int i = 0; i < children.size() - 1; i += 2) {
            Node child1 = children.get(i);
            Node child2 = children.get(i + 1);

            Node parent = constructInternalNode(child1, child2);
            parents.add(parent);
        }

       
        if (children.size() % 2 != 0) {
            Node child = children.get(children.size() - 1);
            Node parent = constructInternalNode(child, null);
            parents.add(parent);
        }

        return parents;
    }

    
    private List<Node> bottomLevel(byte[][] hashes) {
        List<Node> parents = Lists.newArrayListWithCapacity(hashes.length / 2);

        for (int i = 0; i < hashes.length - 1; i += 2) {
            Node leaf1 = constructLeafNode(hashes[i]);
            Node leaf2 = constructLeafNode(hashes[i + 1]);

            Node parent = constructInternalNode(leaf1, leaf2);
            parents.add(parent);
        }

        if (hashes.length % 2 != 0) {
            Node leaf = constructLeafNode(hashes[hashes.length - 1]);
           
            Node parent = constructInternalNode(leaf, leaf);
            parents.add(parent);
        }

        return parents;
    }

   
    private static Node constructLeafNode(byte[] hash) {
        Node leaf = new Node();
        leaf.hash = hash;
        return leaf;
    }

   
    private Node constructInternalNode(Node leftChild, Node rightChild) {
        Node parent = new Node();
        if (rightChild == null) {
            parent.hash = leftChild.hash;
        } else {
            parent.hash = internalHash(leftChild.hash, rightChild.hash);
        }
        parent.left = leftChild;
        parent.right = rightChild;
        return parent;
    }

    
    private byte[] internalHash(byte[] leftChildHash, byte[] rightChildHash) {
        byte[] mergedBytes = ByteUtils.merge(leftChildHash, rightChildHash);
        return DigestUtils.sha256(mergedBytes);
    }

    
    @Data
    public static class Node {
        private byte[] hash;
        private Node left;
        private Node right;
    }

}
