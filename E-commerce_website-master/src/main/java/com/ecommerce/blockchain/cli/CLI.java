package com.ecommerce.blockchain.cli;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.ecommerce.blockchain.block.Block;
import com.ecommerce.blockchain.block.Blockchain;
import com.ecommerce.blockchain.pow.ProofOfWork;
import com.ecommerce.blockchain.store.RocksDBUtils;
import com.ecommerce.blockchain.transaction.TXOutput;
import com.ecommerce.blockchain.transaction.Transaction;
import com.ecommerce.blockchain.transaction.UTXOSet;
import com.ecommerce.blockchain.util.Base58Check;
import com.ecommerce.blockchain.wallet.Wallet;
import com.ecommerce.blockchain.wallet.WalletUtils;

import java.util.Arrays;
import java.util.Set;


@Slf4j
public class CLI {

    public void parse(String[] args) {
        this.validateArgs(args);
        try {
			
        	String cmp=args[0];
        	
                if( cmp.equals("createblockchain")) {
                    String createblockchainAddress = args[2];
                System.out.println("createblockchainAddress"+createblockchainAddress);
                    if (StringUtils.isBlank(createblockchainAddress)) {
                        help();
                    }else {
                    this.createBlockchain(createblockchainAddress);
                 
                    }
                }else {
                    
                    
                    
                    
                    
                if( cmp.equals("getbalance")) {
                    String getBalanceAddress = args[2];
                System.out.println("getBalanceAddress"+getBalanceAddress);
                    if (StringUtils.isBlank(getBalanceAddress)) {
                        help();
                    }
                    this.getBalance(getBalanceAddress);
                  
                }
                else if( cmp.equals("send")) {
                    String sendFrom = args[2];
                    String sendTo = args[4];
                    String sendAmount = args[6];
                    
                    System.out.println("sendFrom"+sendFrom);
                    System.out.println("sendTo"+sendTo);
                    System.out.println("sendAmount"+sendAmount);
                    if (StringUtils.isBlank(sendFrom) ||
                            StringUtils.isBlank(sendTo) ||
                            !NumberUtils.isDigits(sendAmount)) {
                        help();
                    }
                    this.send(sendFrom, sendTo, Integer.valueOf(sendAmount));
                   
                }else if( cmp.equals("createwallet")) {
                    this.createWallet();
                   
                }else
                    if( cmp.equals("printaddresses")) {
                    this.printAddresses();
                 
                    }else if (cmp.equals("printchain")) {
                    this.printChain();
                
                    }
                    else if (cmp.equals("h")) {
                    this.help();
                
                    }
                    
                    
				
            }
        } catch (Exception e) {
        
        } finally {
            RocksDBUtils.getInstance().closeDB();
        }
    }

   
    private void validateArgs(String[] args) {
        if (args == null || args.length < 1) {
            help();
        }
    }

   
    private void createBlockchain(String address) {
        Blockchain blockchain = Blockchain.createBlockchain(address);
        UTXOSet utxoSet = new UTXOSet(blockchain);
        utxoSet.reIndex();
       // log.info("Done ! ");
    }

   
    private void createWallet() throws Exception {
        Wallet wallet = WalletUtils.getInstance().createWallet();
   
    }

 
    private void printAddresses() {
        Set<String> addresses = WalletUtils.getInstance().getAddresses();
        if (addresses == null || addresses.isEmpty()) {
       
            return;
        }
        for (String address : addresses) {
      
        }
    }

 
    private void getBalance(String address) {
      
        try {
            Base58Check.base58ToBytes(address);
        } catch (Exception e) {
        
            throw new RuntimeException("ERROR: invalid wallet address", e);
        }

       
        byte[] versionedPayload = Base58Check.base58ToBytes(address);
        byte[] pubKeyHash = Arrays.copyOfRange(versionedPayload, 1, versionedPayload.length);

        Blockchain blockchain = Blockchain.createBlockchain(address);
        UTXOSet utxoSet = new UTXOSet(blockchain);

        TXOutput[] txOutputs = utxoSet.findUTXOs(pubKeyHash);
        int balance = 0;
        if (txOutputs != null && txOutputs.length > 0) {
            for (TXOutput txOutput : txOutputs) {
                balance += txOutput.getValue();
            }
        }
      
    }

 
    private void send(String from, String to, int amount) throws Exception {
      
        try {
            Base58Check.base58ToBytes(from);
        } catch (Exception e) {
        
            throw new RuntimeException("ERROR: sender address invalid ! address=" + from, e);
        }
     
        try {
            Base58Check.base58ToBytes(to);
        } catch (Exception e) {
          
            throw new RuntimeException("ERROR: receiver address invalid ! address=" + to, e);
        }
        if (amount < 1) {
          
            throw new RuntimeException("ERROR: amount invalid ! amount=" + amount);
        }
        Blockchain blockchain = Blockchain.createBlockchain(from);
    
        Transaction transaction = Transaction.newUTXOTransaction(from, to, amount, blockchain);
     
        Transaction rewardTx = Transaction.newCoinbaseTX(from, "");
        Block newBlock = blockchain.mineBlock(new Transaction[]{transaction, rewardTx});
        new UTXOSet(blockchain).update(newBlock);
      
    }

   
    private void help() {
        System.out.println("Usage:");
        System.out.println("  createwallet - Generates a new key-pair and saves it into the wallet file");
        System.out.println("  printaddresses - print all wallet address");
        System.out.println("  getbalance -address ADDRESS - Get balance of ADDRESS");
        System.out.println("  createblockchain -address ADDRESS - Create a blockchain and send genesis block reward to ADDRESS");
        System.out.println("  printchain - Print all the blocks of the blockchain");
        System.out.println("  send -from FROM -to TO -amount AMOUNT - Send AMOUNT of coins from FROM address to TO");
        System.exit(0);
    }

    
    private void printChain() {
        Blockchain blockchain = Blockchain.initBlockchainFromDB();
        for (Blockchain.BlockchainIterator iterator = blockchain.getBlockchainIterator(); iterator.hashNext(); ) {
            Block block = iterator.next();
            if (block != null) {
                boolean validate = ProofOfWork.newProofOfWork(block).validate();
           
            }
        }
    }

}