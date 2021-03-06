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
package com.lambkit.core.aop;

import com.jfinal.log.Log;

/**
 * 类实例创建者
 */
public class AopKit {

    private static Log log = Log.getLog(AopKit.class);

    private static LambkitAopFactory aopFactory = new LambkitAopFactory();
    /**
     * 获取单例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T singleton(Class<T> clazz) {
    	return aopFactory.getSingleton(clazz);
    }
    
    public static <T> T instance(Class<T> clazz) {
    	return aopFactory.getObject(clazz);
    }
    
    public static <T> T instance(String clazzName) {
        try {
            @SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) Class.forName(clazzName, false, Thread.currentThread().getContextClassLoader());
            return instance(clazz);
        } catch (Exception e) {
            log.error("can not newInstance class:" + clazzName + "\n" + e.toString(), e);
        }
        return null;
    }

    /**
     * 创建新的实例
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public static <T> T get(Class<T> clazz) {
        return get(clazz, false);
    }


    public static <T> T get(Class<T> clazz, boolean createdByGuice) {
        if (createdByGuice) {
            return null;
        } else {
            return aopFactory.get(clazz);
        }
    }

    /**
     * 创建新的实例
     *
     * @param <T>
     * @param clazzName
     * @return
     */
    public static <T> T get(String clazzName) {
        try {
            @SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) Class.forName(clazzName, false, Thread.currentThread().getContextClassLoader());
            return get(clazz);
        } catch (Exception e) {
            log.error("can not newInstance class:" + clazzName + "\n" + e.toString(), e);
        }
        return null;
    }


}
