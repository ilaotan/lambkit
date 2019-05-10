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
package com.lambkit.module.meta.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.lambkit.common.model.BaseModel;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2019-01-07
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseMetaFileCatalogMapping<M extends BaseMetaFileCatalogMapping<M>> extends BaseModel<M> implements IBean {

	public String getTableName() {
		return "meta_file_catalog_mapping";
	}
    
	public java.lang.Long getCatalogId() {
		return this.get("catalog_id");
	}

	public void setCatalogId(java.lang.Long catalogId) {
		this.set("catalog_id", catalogId);
	}
	public java.lang.Long getFileId() {
		return this.get("file_id");
	}

	public void setFileId(java.lang.Long fileId) {
		this.set("file_id", fileId);
	}
}
