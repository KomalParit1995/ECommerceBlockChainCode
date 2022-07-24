package com.ecommerce.blockchain.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

import com.ecommerce.blockchain.pow.PowResult;
import com.ecommerce.blockchain.pow.ProofOfWork;
import com.ecommerce.blockchain.transaction.MerkleTree;
import com.ecommerce.blockchain.transaction.Transaction;
import com.ecommerce.blockchain.util.ByteUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Block {

    
    private String hash;
    
    private String prevBlockHash;
    
    private Transaction[] transactions;
    
    private long timeStamp;
   
    private long nonce;
    
    private int i=0;
    private String abc="";

    public Block(String string, String previousHash, Transaction[] transactions2, long epochSecond, int i) {
		this.prevBlockHash=previousHash;
		this.transactions=transactions2;
		this.timeStamp=epochSecond;
		this.i=i;
		this.abc=string;
	}

	
    public static Block newGenesisBlock(Transaction coinbase) {
        return Block.newBlock(ByteUtils.ZERO_HASH, new Transaction[]{coinbase});
    }

    
    public static Block newBlock(String previousHash, Transaction[] transactions) {
        Block block = new Block("", previousHash, transactions, Instant.now().getEpochSecond(), 0);
        ProofOfWork pow = ProofOfWork.newProofOfWork(block);
        PowResult powResult = pow.run();
        block.setHash(powResult.getHash());
        block.setNonce(powResult.getNonce());
        return block;
    }

    private void setNonce(long nonce2) {
		this.nonce=nonce2;
		
	}

	private void setHash(String hash2) {
		this.hash=hash2;
		
	}

	
    public MerkleTree hashTransaction() {
        byte[][] txIdArrays = new byte[this.getTransactions().length][];
        for (int i = 0; i < this.getTransactions().length; i++) {
            txIdArrays[i] = this.getTransactions()[i].hash();
        }
        return new MerkleTree(txIdArrays);
    }

	public Transaction[] getTransactions() {
		
		return transactions;
	}

	public String getHash() {
	
		return hash;
	}

	public String getPrevBlockHash() {
		
		return prevBlockHash;
	}
}
