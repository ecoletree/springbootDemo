/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 12. 14.
 * File Name : AesCryptoUtil.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.cryptoFinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AesCryptoUtil extends CryptoCommon{
	
	private final String AES_TYPE;
	/**
	 * @param aes_type
	 */
	public AesCryptoUtil(String aes_type) {
		this.AES_TYPE = aes_type;
	}
	
	/** String 암호화
	 * @param origin
	 * @return
	 */
	public String encryptString(String origin) {
		String result = null;
		try {
	        Cipher cipher = setCipher(Cipher.ENCRYPT_MODE); 
	 
	        byte[] encrypted = cipher.doFinal(origin.getBytes()); 
	        result = Base64.getEncoder().encodeToString(encrypted);
	        
	    } catch (Exception e) {
	    	log.info(e.getMessage());
	    }
		return result;
	}
	
	/** String 복호화
	 * @param encrypted
	 * @return
	 */
	public String decryptString(String encrypted) {
		String result = null;
		try {
			Cipher cipher = setCipher(Cipher.DECRYPT_MODE);
			
	        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted)); //base64 to byte 

	        result = new String(original);
	    } catch (Exception e) {
	    	log.info(e.getMessage());
	    }
		return result;
	}
	
	/** File 암호화
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public String encryptFile(String inputFile, String outputFile) {
		
		String resultMsg="";
		try {
			Cipher cipher = setCipher(Cipher.ENCRYPT_MODE);
			resultMsg = doCryptoFile(cipher,inputFile,outputFile);
		} catch (CryptoException e) {
			log.info("fileEncrypt:"+e.getMessage());
		}
		return resultMsg;
	}
	
	/** File 복호화
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public String decryptFile(String inputFile, String outputFile) {
		String resultMsg="";
		try {
			Cipher cipher = setCipher(Cipher.DECRYPT_MODE);
			resultMsg = doCryptoFile(cipher,inputFile,outputFile);
		} catch (CryptoException e) {
			log.info("fileEncrypt:"+e.getMessage());
		}
		return resultMsg;
	}
	
	/** CBC 파일 암/복호화
	 * @param cipherMode
	 * @param key
	 * @param inputFile
	 * @param outputFile
	 * @return
	 * @throws CryptoException
	 */
	public String doCryptoFile(Cipher cipher, String inputFile, String outputFile)throws CryptoException {
		String resultMsg = "fail";
			try {
				File inFile = new File(inputFile);
				File outFile = new File(outputFile);
				
				FileInputStream fis = new FileInputStream(inFile);
				byte[] inputBytes = new byte[(int) inFile.length()];
				fis.read(inputBytes);
				
				byte[] outputBytes = cipher.doFinal(inputBytes);
				FileOutputStream fos = new FileOutputStream(outFile);
				fos.write(outputBytes);
				
				fis.close();
				fos.close();
			} catch (Exception e) {
				log.info("doCryptoFile:"+e.getMessage());
			}
			resultMsg = "success";
		return resultMsg;
	}
	
	/** cipher 생성
	 * @param cipherMode
	 * @return
	 */
	public Cipher setCipher(int cipherMode) {
		Cipher cipher = null;
		String mode = "AES/"+AES_TYPE+"/PKCS5Padding";
		try {
			Key secretKey = new SecretKeySpec(pbszUserKey, "AES");
			cipher = Cipher.getInstance(mode);
			if(AES_TYPE.equals(CBC)) {
				log.info("cipher 생성 CBC");
				IvParameterSpec iv = new IvParameterSpec(bsxIV);
				cipher.init(cipherMode, secretKey,iv);
			}else {
				log.info("cipher 생성 ECB");
				cipher.init(cipherMode, secretKey);
			}
		} catch (Exception e) {
			log.info("cbcCipher_error::"+ e.getMessage());
		}
		return cipher;
	}

}
