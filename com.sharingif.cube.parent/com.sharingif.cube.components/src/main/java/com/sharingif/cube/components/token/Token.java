package com.sharingif.cube.components.token;

/**   
 *  
 * @Description:  [Token]   
 * @Author:       [Joly]   
 * @CreateDate:   [2014年12月21日 下午4:08:43]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2014年12月21日 下午4:08:43]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class Token implements IToken {
	
	private static final long serialVersionUID = -2101517252789755432L;
	
	private long createDate;										// 生成时间
	private String uniqueId;										// token唯一id
	private boolean unavailable;									// 已失效
	
	public Token(String uniqueId){
		this.createDate = System.currentTimeMillis();
		this.unavailable = false;
		this.uniqueId = uniqueId;
	}
	
	@Override
	public long getCreateDate() {
		return createDate;
	}
	@Override
	public String getUniqueId() {
		return uniqueId;
	}
	@Override
	public boolean isUnavailable() {
		return unavailable;
	}
	@Override
	public void setUnavailable(boolean unavailable) {
		this.unavailable = unavailable;
	}
	

}
