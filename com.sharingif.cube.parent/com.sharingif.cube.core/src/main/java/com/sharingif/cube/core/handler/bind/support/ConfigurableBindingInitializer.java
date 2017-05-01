package com.sharingif.cube.core.handler.bind.support;

import org.springframework.core.convert.ConversionService;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import com.sharingif.cube.core.request.RequestInfo;


/**
 * Convenient {@link BindingInitializer} for declarative configuration
 * in a Spring application context. Allows for reusing pre-configured
 * initializers with multiple controller/handlers.
 * 2015年7月26日 下午8:13:36
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ConfigurableBindingInitializer implements BindingInitializer {
	

	private Validator validator;

	private ConversionService conversionService;


	/**
	 * Set the Validator to apply after each binding step.
	 */
	public final void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * Return the Validator to apply after each binding step, if any.
	 */
	public final Validator getValidator() {
		return this.validator;
	}

	/**
	 * Specify a ConversionService which will apply to every DataBinder.
	 * @since 3.0
	 */
	public final void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	/**
	 * Return the ConversionService which will apply to every DataBinder.
	 */
	public final ConversionService getConversionService() {
		return this.conversionService;
	}



	@Override
	public void initBinder(DataBinder binder, RequestInfo<?> requestInfo) {
		if (this.validator != null && binder.getTarget() != null &&
				this.validator.supports(binder.getTarget().getClass())) {
			binder.setValidator(this.validator);
		}
		if (this.conversionService != null) {
			binder.setConversionService(this.conversionService);
		}
	}

}
