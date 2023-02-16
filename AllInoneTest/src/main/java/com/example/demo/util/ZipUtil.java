/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : Jang Yoon Seok
 * Create Date : 2022. 12. 15.
 * File Name : ZipUtil.java
 * DESC : 
*****************************************************************/
package com.example.demo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class ZipUtil {
	
	public static void main(String[] str) {
		ZipUtil util = new ZipUtil();
		try {
			util.add("E:\\01. 문서파일\\99.연구노트").zip("E:\\01. 문서파일\\99.연구노트\\test.zip");
			
			util.unzip("E:\\01. 문서파일\\99.연구노트\\test.zip");
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  

	private List<File> files = new ArrayList<File>();

	 /**
	 * 압축할 파일 추가
	 */
    public ZipUtil add(String file) {
        return add(new File(file));
    }

    public ZipUtil add(File file) {
        if (file == null || !file.exists())
            throw new RuntimeException("파일이 존재하지 않습니다.");

        files.add(file);

        return this;
    }

    /**
     * 압축하기
     *
     * @param to 생성할 압축파일
     * @throws ZipException
     */
    public void zip(String to) throws ZipException {
        this.zip(to, null);
    }

    /**
     * 비밀번호 걸어서 압축하기
     *
     * @param to       생성할 압축파일
     * @param password 압축파일 암호
     * @throws ZipException
     */
    @Autowired(required = false)
	public void zip(String to, String password) throws ZipException {
        ZipParameters param = new ZipParameters();
        param.setCompressionMethod(CompressionMethod.DEFLATE);
        ZipFile zipFile = null;
        if (password != null) {
            param.setEncryptFiles(true);
            param.setEncryptionMethod(EncryptionMethod.AES);
            param.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
            zipFile = new ZipFile(to, password.toCharArray());
        } else {
        	zipFile = new ZipFile(to);
        }


        if (files.size() == 0) {
            throw new RuntimeException("압축대상 파일이 없습니다.");
        }
        
        for (File file : files) {
        	try {
                if (file.isFile()) {
                    zipFile.addFile(file, param);
                } else {
                    zipFile.addFolder(file, param);
                }
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }
        
        
//        zipFile.addFiles(files, param);
    }

    /**
     * 압축풀기
     *
     * @param file
     * @param password
     * @throws ZipException
     */
    public void unzip(String file, String password) throws ZipException {
        File f = new File(file);
        ZipFile zipFile = null;
        if (password != null) {
       		zipFile = new ZipFile(f, password.toCharArray());
        } else {
        	zipFile = new ZipFile(f);
        }

        zipFile.extractAll(f.getParent());
    }

    public void unzip(String file) throws ZipException {
        this.unzip(file, null);
    }
}

