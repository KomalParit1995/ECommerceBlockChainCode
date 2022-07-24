package com.ecommerce.blockchain.pow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PowResult {

    
    private long nonce;
    
    private String hash;
	public PowResult(long nonce2, String shaHex) {
		this.nonce=nonce2;
		this.hash=shaHex;
	}
	public String getHash() {
		
		return hash;
	}
	public long getNonce() {
		
		return nonce;
	}
	public String getPrevBlockHash() {
		
		return getPrevBlockHash();
	}
	public byte[] hashTransaction() {

		return hashTransaction();
	}
	public long getTimeStamp() {
		
		return getTimeStamp();
	}

}
