package com.sharingif.cube.components.payments;

import java.util.Date;

import com.sharingif.cube.core.util.DateUtils;

/**
 * [日期方式生成支付流水号]
 * [2015年5月7日 下午8:47:42]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class DateTimeSequencePaymentIdGenerator implements PaymentIdGenerator {
	
	private int defaultSequence=1000;
	private int maxSequence=9999;
	private String datetimeFormat = "yyMMddHHmmss";
	private int sequence=defaultSequence;
	
	private synchronized int getSequence(){
		sequence++;
		if(maxSequence < sequence)
			sequence=defaultSequence;
		
		return sequence;
	}

	@Override
	public String generatePaymentId() {
		return new StringBuilder()
		.append(DateUtils.getDate(new Date(), datetimeFormat))
		.append(getSequence()).toString();
	}
	
	public int getDefaultSequence() {
		return defaultSequence;
	}
	public void setDefaultSequence(int defaultSequence) {
		this.defaultSequence = defaultSequence;
	}
	public int getMaxSequence() {
		return maxSequence;
	}
	public void setMaxSequence(int maxSequence) {
		this.maxSequence = maxSequence;
	}
	public String getDatetimeFormat() {
		return datetimeFormat;
	}
	public void setDatetimeFormat(String datetimeFormat) {
		this.datetimeFormat = datetimeFormat;
	}

}
