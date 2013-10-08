package com.collage.goddessofskin.api;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Response header
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 7. ?¤í›„ 1:08:04
 */
@XStreamAlias("Header")
public class ResponseHeader implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1958533695825793656L;
	
	@XStreamAlias("SuccessYN")
	private String successYN;
	
	@XStreamAlias("ReturnCode")
	private String returnCode;
	
	@XStreamAlias("ErrMsg")
	private String errMsg;
	
	public String getSuccessYN()
	{
		return successYN;
	}
	public void setSuccessYN(String successYN)
	{
		this.successYN = successYN;
	}
	public String getReturnCode()
	{
		return returnCode;
	}
	public void setReturnCode(String returnCode)
	{
		this.returnCode = returnCode;
	}
	public String getErrMsg()
	{
		return errMsg;
	}
	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}
}
