##create bean,this is a class
#parse("/template/java_copyright.include")
package $!{basepackage}.service.impl;

import com.lambkit.common.aop.AopKit;
import com.lambkit.common.service.BaseModelServiceImpl;

import $!{basepackage}.service.${classname}Service;
import $!{basepackage}.model.${classname};

#parse("/template/java_author.include")
public class ${classname}ServiceImpl extends BaseModelServiceImpl<${classname}> implements ${classname}Service {
	
	private ${classname} DAO = null;
	
	public ${classname} dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(${classname}.class);
		}
		return DAO;
	}
}
