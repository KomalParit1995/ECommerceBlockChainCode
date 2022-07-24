package com.ecommerce.blockchain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import com.ecommerce.blockchain.util.Base58Check;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TXOutput {

   
    private int value;
   
    private byte[] pubKeyHash;

    public TXOutput(int value2, byte[] pubKeyHash2) {
		this.value=value2;
		this.pubKeyHash=pubKeyHash2;
	}

	
    public static TXOutput newTXOutput(int value, String address) {
       
        byte[] versionedPayload = Base58Check.base58ToBytes(address);
        byte[] pubKeyHash = Arrays.copyOfRange(versionedPayload, 1, versionedPayload.length);
        return new TXOutput(value, pubKeyHash);
    }

   
    public boolean isLockedWithKey(byte[] pubKeyHash) {
        return Arrays.equals(this.getPubKeyHash(), pubKeyHash);
    }

	byte[] getPubKeyHash() {
		
		return pubKeyHash;
	}

	public int getValue() {
	
		return value;
	}

}
