package com.example.demo.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
	

public class SFTPConnection {
	
	
	public static void main(String[] str) {
		SFTPConnection conn = new SFTPConnection();
		String url = "106.10.34.69";
		int port = 22;
		String user = "root";
		String pws = "cD5xzgbrp";
		try {
			ChannelSftp ss =  conn.ftpConnect(url, port, user, pws);
			
			System.out.println(ss.isConnected());
			
			File file = new File("C:\\Users\\boadl\\Documents\\Overwatch\\videos\\overwatch\\솜_22-10-11_10-11-15.mp4");
			if (file.exists()) {
				conn.upload(ss, file, "/opt");
			}
			
			
			System.out.println("끝? : "+conn.isComplate());
			conn.disconnect();
			
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	private FileInputStream in = null;
	
	private boolean isComplate = false;
	
	/**
	 * 파일 전송 완료 여부
	 * @return
	 */
	public boolean isComplate () {
		return isComplate;
	}
	
	/**
	 * sftp 객체
	 * @return
	 */
	public ChannelSftp getChannelSftp () {
		return channelSftp;
	}
	
	/**
	 * ftp connect 메소드
	 * @param url 경로
	 * @param port 포트
	 * @param user 유저정보
	 * @param password 비밀번호
	 * @return sftp 객체
	 * @throws JSchException
	 */
	public ChannelSftp ftpConnect(String url, int port, String user, String password) throws JSchException {
        	
		JSch jsch = new JSch();
    	//세션객체 생성 ( user , host, port ) 	
        session = jsch.getSession(user, url, port);
        
        //password 설정
        session.setPassword(password);
        
        //세션관련 설정정보 설정
        java.util.Properties config = new java.util.Properties();
        
        //호스트 정보 검사하지 않는다.
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        
        //접속
        session.connect();

        //sftp 채널 접속
        channel = session.openChannel("sftp");
        channel.connect();
        
        channelSftp = (ChannelSftp) channel;
		return channelSftp;
	}
	
	/**
	 * 모두 사용후 disconnect
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if (in != null) {
			in.close();
			in = null;
		}
		if (channel != null && channel.isConnected()) {
			channel.disconnect();
			channel = null;
		}
		if (channelSftp != null) {
			channelSftp.quit();
			channelSftp = null;
		}
		if (session != null && session.isConnected()) {
			session.disconnect();
			session = null;
		}
		isComplate = false;
	}
	
	// 단일 파일 업로드 
	
	public void upload(ChannelSftp channelSftp, File file) throws FileNotFoundException, SftpException{
		upload(channelSftp, file);
	}
	
	/**
	 * 파일 업로드
	 * @param channelSftp : sftp 객체
	 * @param file : 파일
	 * @param dir : 업로드할 경로
	 * @throws FileNotFoundException
	 * @throws SftpException
	 */
	public void upload(ChannelSftp channelSftp, File file, String dir) throws FileNotFoundException, SftpException{
		in = null;
		in = new FileInputStream(file);
		if (dir != null) {
			// 업로드 경로가 있는 경우 경로를 생성
			channelSftp.cd(dir);
		}
		channelSftp.put(in,file.getName(), new SftpProgressMonitor() {
			long FileSize = 0;
			long sendFileSize = 0;
			int per = 0;
			public void init(int op, String src, String dest, long max) {
				this.FileSize = max;
				isComplate = false;
				logger.info(file.getName()+" : upload start ");
			}
		
			public boolean count(long count) {
				this.sendFileSize += count;
				long p = this.sendFileSize * 100 / this.FileSize;
				if(p > per) {
					logger.info("=");
					per++;
				}
				
				return true;
			}
		
			public void end() {
				logger.info(file.getName()+" : upload end");
				isComplate = true;
			}
		});
	}
	
	/**
	 * StackTrace를 String으로 생성
	 * @param e
	 */
	@SuppressWarnings("unused")
	private void errorStackTrace(Exception e) {
		StringWriter errors = new StringWriter();
	    e.printStackTrace(new PrintWriter(errors));
	   	logger.error(errors.toString(), e);
	}
}
