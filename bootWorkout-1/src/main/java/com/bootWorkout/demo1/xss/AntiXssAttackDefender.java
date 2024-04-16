/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : Kim Kyung Hyun
 * Create Date : 2024. 3. 27.
 * File Name : AntiXssAttackDefender.java
 * DESC :
*****************************************************************/
package com.bootWorkout.demo1.xss;

import org.owasp.encoder.Encode;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class AntiXssAttackDefender {

	public static void main(String[] args) {

		// 사용자 입력값
        String userInput = "asdfasfd";
//        userInput = "<script>alert('XSS 공격 예제');</script>";
//        userInput = "<img src='#' onerror='alert('xss공격!')'>";
        userInput = "<iframe src='&#106;&#097;&#118;&#097;&#115;&#099;&#114;&#105;&#112;&#116;&#058;&#097;&#108;&#101;&#114;&#116;&#040;&#034;&#097;&#115;&#100;&#102;&#097;&#115;&#102;&#100;&#097;&#034;&#041;&#059;'></iframe>";

        // AntiXSS 라이브러리를 사용하여 XSS 공격 방어 : 안전한 값으로 변환
        String sanitizeddText = AntiXSShtmlSanitizer(userInput);
        String encodedText = AntiXSSEncoder(userInput);

        // 안전한 값 출력
        System.out.println("AntiXSShtmlSanitizer : " + sanitizeddText);
        System.out.println("AntiXSSEncoder : " + encodedText);

	}

	/** convert
	 * @param userInput
	 * @return
	 */
	private static String AntiXSSEncoder(String inputText) {
		String safeTxt = Encode.forHtml(inputText);
		return safeTxt;
	}

	/** remove
	 * @param inputText
	 * @return
	 */
	private static String AntiXSShtmlSanitizer(String inputText) {
		PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
		String txtResult = policy.sanitize(inputText);
		return txtResult;
	}



}
