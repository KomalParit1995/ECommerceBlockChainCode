package com.ecommerce.blockchain.cli;

public class MainBlockchain {

    public static void mainMethodOfBlockchain(String servletAmt) {
    	if(servletAmt!=null)
    	{
    	//String[] argss = {"createblockchain", "-address", "0xa7a82dd06901f29ab14af63faf3358ad101724a8"};
    	
    // String[] argss = {"getbalance", "-address", "0xa7a82dd06901f29ab14af63faf3358ad101724a8"};
      
      //String[] argss = {"printaddresses"};
    
      String[] argss = {"send", "-from", "0xa7a82dd06901f29ab14af63faf3358ad101724a8", "-to", "0xf0849e4048d3415fa5f7dc949ab389b5e7df302a", "-amount", servletAmt};
  
    //  String[] argss = {"createwallet"};
    	
    	 //String[] argss = {"printaddresses"};
    	 //String[] argss = {"printchain"};
    	// String[] argss = {"h"};
        
        CLI cli = new CLI();
        cli.parse(argss);
    	}else{
    		
    		System.out.println("Somethimg is wrong");
    	}
    }
}
