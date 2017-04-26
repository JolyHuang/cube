package com.sharingif.cube.communication;

import java.util.Map;

/**
 * JsonModel
 * 2017年1月7日 下午9:27:37
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class JsonModel<T> {
	
	public static final String TRAN_STATUS = "_tranStatus";
	public static final String EXCEPTION_MESSAGE = "_exceptionMessage";
	public static final String EXCEPTION_LOCALIZED_MESSAGE = "_exceptionLocalizedMessage";
	public static final String DATA = "_data";
	
	private Boolean _tranStatus;
	private String _exceptionMessage;
	private String _exceptionLocalizedMessage;
	private T _data;
	
	public JsonModel(Boolean _tranStatus, String _exceptionMessage, String _exceptionLocalizedMessage, T _data) {
		super();
		this._tranStatus = _tranStatus;
		this._exceptionMessage = _exceptionMessage;
		this._exceptionLocalizedMessage = _exceptionLocalizedMessage;
		this._data = _data;
	}
	
	/**
	 * 使用包含JsonModel所有数据的map初始化JsonModel，方便数据的查找
	 * @param jsonModel
	 */
	@SuppressWarnings("unchecked")
	public JsonModel(Map<String, Object> jsonMap) {
		this._tranStatus = (boolean) jsonMap.get(JsonModel.TRAN_STATUS);
		this._exceptionMessage = (String) jsonMap.get(JsonModel.EXCEPTION_MESSAGE);
		this._exceptionLocalizedMessage = (String) jsonMap.get(JsonModel.EXCEPTION_LOCALIZED_MESSAGE);
		this._data = (T) jsonMap.get(JsonModel.DATA);
	}
	
	public Boolean get_tranStatus() {
		return _tranStatus;
	}

	public String get_exceptionMessage() {
		return _exceptionMessage;
	}

	public String get_exceptionLocalizedMessage() {
		return _exceptionLocalizedMessage;
	}

	public T get_data() {
		return _data;
	}
	
}
