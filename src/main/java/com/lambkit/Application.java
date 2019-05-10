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
package com.lambkit;

import com.jfinal.config.*;
import com.jfinal.log.Log;
import com.jfinal.template.Engine;
import com.lambkit.common.LambkitConsts;
import com.lambkit.core.event.EventKit;
import com.lambkit.db.mgr.MgrdbManager;
import com.lambkit.module.LambkitModule;
import com.lambkit.module.lms.LmsModule;
import com.lambkit.module.upms.server.UpmsModule;

public class Application extends JFinalConfig {

    static final Log log = Log.getLog(Application.class);
    
    @Override
	public void configConstant(Constants constants) {
		// TimeUtils.startTime("start lambkit configConstant");
		if(Lambkit.getLambkitConfig().isLmsActived()) {
    		Lambkit.addModule(new UpmsModule());
        	LambkitModule module = MgrdbManager.me().getLambkitModule();
        	if(module!=null) {
        		Lambkit.addModule(module);
        	}
    		Lambkit.addModule(new LmsModule());
		}
		/**
		 * 发送初始化通知
		 */
		EventKit.sendEvent(LambkitConsts.EVENT_INIT, null);
		Lambkit.getModule().configConstant(constants);
		// TimeUtils.endTime("start lambkit configConstant");
	}

	@Override
	public void configRoute(Routes routes) {
		// TimeUtils.startTime("start lambkit configRoute");
		Lambkit.getModule().configRoute(routes);
		// TimeUtils.endTime("start lambkit configRoute");
	}

	@Override
	public void configEngine(Engine engine) {
		// TimeUtils.startTime("start lambkit configEngine");
		Lambkit.getModule().configEngine(engine);
		// TimeUtils.endTime("start lambkit configEngine");
	}

	@Override
	public void configPlugin(Plugins plugins) {
		// TimeUtils.startTime("start lambkit configPlugin");
		Lambkit.getModule().configPlugin(plugins);
		// TimeUtils.endTime("start lambkit configPlugin");
	}

	@Override
	public void configInterceptor(Interceptors interceptors) {
		// TimeUtils.startTime("start lambkit configInterceptor");
		Lambkit.getModule().configInterceptor(interceptors);
		// TimeUtils.endTime("start lambkit configInterceptor");
	}

	@Override
	public void configHandler(Handlers handlers) {
		// TimeUtils.startTime("start lambkit configHandler");
		Lambkit.getModule().configHandler(handlers);
		// TimeUtils.endTime("start lambkit configHandler");
	}

	@Override
	public void onStart() {
		// TimeUtils.startTime("start lambkit onStart");
		super.onStart();
		Lambkit.getModule().onStart();
		// TimeUtils.endTime("start lambkit onStart");
	}

	@Override
	public void onStop() {
		// TimeUtils.startTime("start lambkit onStop");
		Lambkit.getModule().onStop();
		// TimeUtils.endTime("start lambkit onStop");
	}
    
}
