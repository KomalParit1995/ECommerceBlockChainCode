package com.ecommerce.blockchain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpendableOutputResult {

    public SpendableOutputResult(int accumulated2, Map<String, int[]> unspentOuts2) {
		this.accumulated=accumulated2;
		this.unspentOuts=unspentOuts2;
	}
	
    private int accumulated;
   
    private Map<String, int[]> unspentOuts;
	public int getAccumulated() {
		
		return accumulated;
	}
	public Map<String, int[]> getUnspentOuts() {
		
		return unspentOuts;
	}

}
