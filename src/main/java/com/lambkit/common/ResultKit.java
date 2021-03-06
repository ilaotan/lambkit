/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lambkit.common;

public class ResultKit {

	public static AjaxResult json(int code, String message, Object data) {
		return new AjaxResult(code, message, data);
	}
	
	public static AjaxResult json(String type, int code, String message, Object data) {
		if("layui".equalsIgnoreCase(type)) {
			return layui(code, message, data);
		}
		return new AjaxResult(code, message, data);
	}
	
	public static PageResult page(int code, String message, int count, Object data) {
		return new PageResult(code, message, count, data);
	}
	
	public static PageResult page(String type, int code, String message, int count, Object data) {
		if("layui".equalsIgnoreCase(type)) {
			return layui(code, message, count, data);
		}
		return new PageResult(code, message, count, data);
	}
	
	public static AjaxResult layui(int code, String message, Object data) {
		if(code==1) code=0;
		else if(code==0) code=1;
		return new AjaxResult(code, message, data);
	}
	
	public static PageResult layui(int code, String message, int count, Object data) {
		if(code==1) code=0;
		else if(code==0) code=1;
		return new PageResult(code, message, count, data);
	}
	
	
}
