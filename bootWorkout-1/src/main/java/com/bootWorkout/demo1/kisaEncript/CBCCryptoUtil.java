/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Kim Kyung Hyun 
 * Create Date : 2023. 11. 28.
 * File Name : CBCCryptoUtil.java
 * DESC : 
*****************************************************************/
package com.bootWorkout.demo1.kisaEncript;

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

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Component
public class CBCCryptoUtil {
	
	
	/** CBC 암호화 
	 * Initial Vector를 암호문 대신 사용
	 * String to String
	 * @param origin
	 * @return
	 */
	public String encryptCBCString(String origin) {
		log.info("testtest"+CryptoKeySets.CBC_KEY);
		try {
	        Cipher cipher = setCipher(Cipher.ENCRYPT_MODE); 
	 
	        byte[] encrypted = cipher.doFinal(origin.getBytes()); //실제로 암호화 하는 부분
	        return Base64.getEncoder().encodeToString(encrypted); //암호문을 base64로 인코딩하여 출력 해줌
	        
	    } catch (Exception e) {
	    	log.info("encryptCBCString:"+e.getMessage());
	    }
		return null;
	}
	
	/** CBC 복호화 
	 * String to String
	 * @param encrypted
	 * @return
	 */
	public String decryptCBCString(String encrypted) {
		
		try {
			Cipher cipher = setCipher(Cipher.DECRYPT_MODE);
			
	        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted)); //base64 to byte 

	        return new String(original);
	    } catch (Exception e) {
	    	log.info("decryptCBCString:"+e.getMessage());
	    }
	    return null;
		
	}
	
	
	/** CBC 파일 암호화
	 * @param saveTargetPath
	 * @param infileName
	 * @param outFileName
	 * @return
	 */
	public String fileEncrypt(String saveTargetPath, String infileName, String outFileName) {
		
		File inFile = new File(saveTargetPath+infileName);
		File outFile = new File(saveTargetPath+outFileName);
		String resultMsg="";
		try {
			Cipher cipher = setCipher(Cipher.ENCRYPT_MODE);
			resultMsg = doCryptoFile(cipher,inFile,outFile);
		} catch (CryptoException e) {
			log.info("fileEncrypt:"+e.getMessage());
		}
		return resultMsg;
	}
	
	/** CBC 파일 복호화
	 * @param saveTargetPath
	 * @param infileName
	 * @param outFileName
	 * @return
	 */
	public String fileDecrypt(String saveTargetPath, String infileName, String outFileName) {
		
		File inFile = new File(saveTargetPath+infileName);
		File outFile = new File(saveTargetPath+outFileName);
		
		String resultMsg="";
		try {
			Cipher cipher = setCipher(Cipher.DECRYPT_MODE);
			resultMsg = doCryptoFile(cipher,inFile,outFile);
		} catch (CryptoException e) {
			log.info("fileDecrypt:"+e.getMessage());
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
	public String doCryptoFile(Cipher cipher, File inputFile, File outputFile)throws CryptoException {
		String resultMsg = "fail";
			try {
				
				FileInputStream fis = new FileInputStream(inputFile);
				byte[] inputBytes = new byte[(int) inputFile.length()];
				fis.read(inputBytes);
				
				byte[] outputBytes = cipher.doFinal(inputBytes);
				FileOutputStream fos = new FileOutputStream(outputFile);
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
		try {
			IvParameterSpec iv = new IvParameterSpec(CryptoKeySets.CBC_INIT_VECTOR.getBytes("UTF-8"));
			Key secretKey = new SecretKeySpec(CryptoKeySets.CBC_KEY.getBytes(), "AES");
			cipher = Cipher.getInstance(CryptoKeySets.CBC_CRYPTO_TYPE);
			cipher.init(cipherMode, secretKey,iv);
		} catch (Exception e) {
			log.info("cbcCipher_error::"+ e.getMessage());
		}
		return cipher;
	}
}
