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
package com.lambkit.module;

import java.util.List;
import java.util.Set;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.template.Directive;
import com.jfinal.template.Engine;
import com.lambkit.Lambkit;
import com.lambkit.common.LambkitConfig;
import com.lambkit.common.LambkitManager;
import com.lambkit.common.bean.TagBean;
import com.lambkit.common.util.ClassUtils;
import com.lambkit.core.aop.AopKit;
import com.lambkit.db.datasource.ActiveRecordPluginWrapper;
import com.lambkit.module.annotation.Module;
import com.lambkit.web.WebConfig;
import com.lambkit.web.WebConfigManager;
import com.lambkit.web.controller.annotation.RequestMapping;
import com.lambkit.web.directive.annotation.JFinalDirective;

import freemarker.template.TemplateModel;

/**
 * JFinal配置模块
 * @author 孤竹行
 *
 */
public abstract class LambkitModule {
	
	public void configConstant(Constants me) {}
	
	public void configRoute(Routes me) {}
	
	public void configEngine(Engine me) {}
	
	public void configPlugin(Plugins me) {}
	
	public void configMapping(ActiveRecordPluginWrapper arp) {}
	
	public void configMapping(String name, ActiveRecordPluginWrapper arp) {}
	
	public void configInterceptor(Interceptors me) {}
	
	public void configHandler(Handlers me) {}
	
	public void onStart(){}
	
	public void onStop(){}

	public void addDirective(Engine engine, String name, Class<? extends Directive> tm) {
		engine.addDirective(name, tm);
		LambkitManager.me().addTag(name, new TagBean("jfinal_enjoy", tm.getName(), tm.getSimpleName()));
	}
	
	public void addTag(String name, TemplateModel tm) {
		if (name.startsWith("lk.")) {
			name = "lk_" + name.substring(3);
		}
		FreeMarkerRender.getConfiguration().setSharedVariable(name, tm);
		LambkitManager.me().addTag(name, new TagBean("freemarker", tm.getClass().getName(), tm.getClass().getSimpleName()));
	}
	
	/**
	 * 加入其他配置
	 * @param config
	 */
	public void addModule(LambkitModule config) {}
	
	public List<LambkitModule> getModules() { return null;}

	/**
	 * 自动扫描加入module
	 */
	public void autoRegisterModule() {
		LambkitConfig config = Lambkit.getLambkitConfig();
    	if(StrKit.notBlank(config.getAutoRegisterModulePackages())) {
    		Set<Class<?>> ctrlClassSet = ClassUtils.scanPackageByAnnotation(config.getAutoRegisterModulePackages(), true, Module.class);
            for (Class<?> clazz : ctrlClassSet) {
            	Module module = clazz.getAnnotation(Module.class);
                if (module != null) {
                	addModule(AopKit.get((Class<? extends LambkitModule>)clazz));
                }
            }
    	}
	}
	
	/**
	 * 自动扫描加入RequestMapping
	 * @param routes
	 */
	public void autoRegisterRequestMapping(Routes routes) {
		
        LambkitConfig config = Lambkit.getLambkitConfig();
    	if(StrKit.notBlank(config.getAutoRegisterControllerPackages())) {
    		WebConfig web = WebConfigManager.me().getWebConfig("admin");
    		Set<Class<?>> ctrlClassSet = ClassUtils.scanPackageByAnnotation(config.getAutoRegisterControllerPackages(), true, RequestMapping.class);
            for (Class<?> clazz : ctrlClassSet) {
            	System.out.println("clazz:"+clazz.getName());
                RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
                if (mapping == null || mapping.value() == null) {
                    continue;
                }
                String urlstr = mapping.value();
    			if(urlstr.startsWith("/admin")) {
    				urlstr = urlstr.replace("/admin", web.getUrl());
    			}
                Class<Controller> controller = (Class<Controller>) clazz;
                if (StrKit.notBlank(mapping.viewPath())) {
                    routes.add(urlstr, controller, mapping.viewPath());
                } else {
                    routes.add(urlstr, controller);
                }
            }
    	}
	}
	
	/**
	 * 自动扫描加入JFinalDirective
	 * @param engine
	 */
	public void autoRegisterEngine(Engine engine) {
		LambkitConfig config = Lambkit.getLambkitConfig();
        if(StrKit.notBlank(config.getAutoRegisterTagPackages())) {
        	Set<Class<?>> directiveClasses = ClassUtils.scanPackageByAnnotation(config.getAutoRegisterTagPackages(), true, JFinalDirective.class);
            for (Class<?> clazz : directiveClasses) {
                JFinalDirective jFinalDirective = (JFinalDirective) clazz.getAnnotation(JFinalDirective.class);
                if (jFinalDirective != null) {
                    addDirective(engine, jFinalDirective.value(), (Class<? extends Directive>) clazz);
                }
            }
        }
	}
}
