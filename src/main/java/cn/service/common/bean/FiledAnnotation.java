/**
 * Copyright (c) 2011 duowan.com. 
 * All Rights Reserved.
 * This program is the confidential and proprietary information of 
 * duowan. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with duowan.com.
 */
package cn.service.common.bean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FiledAnnotation {

	/**
	 * ���������Է��顣��õ��ֶ�levelԽ�͡�
	 * һ��ͷ�2-4����͹��ˡ�ת��jsonʱ������ȥ��һЩ�����õ��ֶ�
	 * @desc
	 * @author lynn
	 * @date ����2:04:00
	 * @return
	 * @return 
	 * @throws
	*
	 */
	int filedGrpLevel();
	
}
