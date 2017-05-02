package com.sharingif.cube.context.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import com.sharingif.cube.core.util.StringUtils;

/**   
 *  
 * @Description:  [替换注解生成的BeanName,删除DAOImpl、ServiceImpl的Impl后缀]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年1月16日 下午2:29:53]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年1月16日 下午2:29:53]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class ExtendedAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator{

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String name = super.buildDefaultBeanName(definition);
		name=StringUtils.replace(name, "DAOImpl", "DAO");
		name=StringUtils.replace(name, "ServiceImpl", "Service");
		return name;
	}
	
	

}
