package com.ecommerce.blockchain.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import com.ecommerce.blockchain.util.Base58Check;
import com.ecommerce.blockchain.util.BtcAddressUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;


@Data
@AllArgsConstructor
@Slf4j
public class Wallet implements Serializable {

    private static final long serialVersionUID = 166249065006236265L;

    
    private static final int ADDRESS_CHECKSUM_LEN = 4;
    
    private BCECPrivateKey privateKey;
    
    private byte[] publicKey;


    public Wallet() {
        initWallet();
    }

    
    private void initWallet() {
        try {
            KeyPair keyPair = newECKeyPair();
            BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
            BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();

            byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);

            this.setPrivateKey(privateKey);
            this.setPublicKey(publicKeyBytes);
        } catch (Exception e) {
           
            throw new RuntimeException("Fail to init wallet ! ", e);
        }
    }


    private void setPublicKey(byte[] publicKeyBytes) {
		this.publicKey=publicKeyBytes;
		
	}

	private void setPrivateKey(BCECPrivateKey privateKey2) {
		this.privateKey=privateKey2;
		
	}


    private KeyPair newECKeyPair() throws Exception {
      
        Security.addProvider(new BouncyCastleProvider());
       
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME);
       
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }


   
    public String getAddress() {
        try {
          
            byte[] ripemdHashedKey = BtcAddressUtils.ripeMD160Hash(this.getPublicKey());

     
            ByteArrayOutputStream addrStream = new ByteArrayOutputStream();
            addrStream.write((byte) 0);
            addrStream.write(ripemdHashedKey);
            byte[] versionedPayload = addrStream.toByteArray();

        
            byte[] checksum = BtcAddressUtils.checksum(versionedPayload);

            
            addrStream.write(checksum);
            byte[] binaryAddress = addrStream.toByteArray();

         
            return Base58Check.rawBytesToBase58(binaryAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Fail to get wallet address ! ");
    }

	public byte[] getPublicKey() {
		
		return publicKey;
	}

	public BCECPrivateKey getPrivateKey() {
		
		return privateKey;
	}


}
