package com.collage.skincare.model.response.life;

import java.io.Serializable;

import com.collage.skincare.model.response.common.ResponseHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 7. 오후 1:08:00
 */
@XStreamAlias("Response")
public class UltraViolet implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7601668923420576557L;

	@XStreamAlias("Header")
	private ResponseHeader header;

	@XStreamAlias("Body")
	private UltraVioletBody body;

	public ResponseHeader getHeader()
	{
		return header;
	}

	public void setHeader(ResponseHeader header)
	{
		this.header = header;
	}

	public UltraVioletBody getBody()
	{
		return body;
	}

	public void setBody(UltraVioletBody body)
	{
		this.body = body;
	}
}
