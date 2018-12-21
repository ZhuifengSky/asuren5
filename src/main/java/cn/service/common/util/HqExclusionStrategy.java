/**
 * Copyright (c) 2011 duowan.com. 
 * All Rights Reserved.
 * This program is the confidential and proprietary information of 
 * duowan. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with duowan.com.
 */
package cn.service.common.util;

import cn.service.common.bean.FiledAnnotation;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class HqExclusionStrategy implements ExclusionStrategy {

	private int filedSkipGrp;
	
	public HqExclusionStrategy(){
		
	}
	
	public HqExclusionStrategy(int fs){
		this.filedSkipGrp = fs;
	}
	
	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		FiledAnnotation  hqfa = f.getAnnotation(FiledAnnotation.class);
		if(null == hqfa){
			return false;
		}else{
			return filedSkipGrp <= hqfa.filedGrpLevel();
		}
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		
		return false;
	}

}
