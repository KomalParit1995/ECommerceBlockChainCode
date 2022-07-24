package com.ecommerce.blockchain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import com.ecommerce.blockchain.util.BtcAddressUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TXInput {

   
    private byte[] txId;
   
    private int txOutputIndex;
  
    private byte[] signature;
   
    private byte[] pubKey;

    
   
	public TXInput(byte[] txId2, int txOutputIndex2,  byte[] signature,  byte[] pubKey) {
		this.txId=txId2;
		this.txOutputIndex=txOutputIndex2;
		this.signature=signature;
		this.pubKey=pubKey;
		
	}

	
    public boolean usesKey(byte[] pubKeyHash) {
        byte[] lockingHash = BtcAddressUtils.ripeMD160Hash(this.getPubKey());
        return Arrays.equals(lockingHash, pubKeyHash);
    }

	byte[] getPubKey() {
		
		return pubKey;
	}

	public byte[] getTxId() {
		
		return txId;
	}

	public int getTxOutputIndex() {
		
		return txOutputIndex;
	}

	public void setPubKey(byte[] pubKeyHash) {
		this.pubKey=pubKeyHash;
		
	}

	public void setSignature(byte[] object) {
		this.signature=object;
	}

	public byte[] getSignature() {
		
		return signature;
	}

}
