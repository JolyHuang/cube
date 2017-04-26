package com.sharingif.cube.components.sequence;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.context.support.ApplicationObjectSupport;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.ClassUtils;
import com.sharingif.cube.core.util.StringUtils;

/**
 *
 * @Description:  [处理Sequence]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月12日 下午1:00:57]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月12日 下午1:00:57]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class SequenceHandlerImpl extends ApplicationObjectSupport implements ISequenceHandler {

	@SuppressWarnings("rawtypes")
	@Override
	public <T> T handleSequence(T obj) {
		List<Field> fields = ClassUtils.getDeclaredAllFields(obj.getClass());
		for(Field field : fields){
			if(field.isAnnotationPresent(Sequence.class)){
				
				try {
					field.setAccessible(true);
					Object fieldValue = field.get(obj);
					
					if((null != fieldValue&&!(fieldValue instanceof String))
						||(!StringUtils.isEmpty(fieldValue))
					){
						continue;
					}
				} catch (Exception e) {
					throw new CubeRuntimeException("get sequence value error", e);
				}
				
				Sequence sequence = field.getAnnotation(Sequence.class);
				String sequenceGeneratorId = sequence.ref();
				ISequenceGenerator sequenceGenerator = super.getApplicationContext().getBean(sequenceGeneratorId, ISequenceGenerator.class);
				
				field.setAccessible(true);
				try {
					field.set(obj, sequenceGenerator.generateSequence());
				} catch (Exception e) {
					throw new CubeRuntimeException("set sequence error", e);
				}
			}
		}
		return obj;
	}
	
}
