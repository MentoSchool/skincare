package com.collage.skincare.api;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;

/**
 * Abstract response model
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 7. ?�후 1:07:08
 */
public class AbsResponse implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 918601592759112156L;

	/**
	 * XML to object
	 * 
	 * @param entity
	 * @param type
	 * @return
	 */
	public static Object fromXML(String entity, Class<?> type)
	{
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		xStream.processAnnotations(type);
		return xStream.fromXML(entity);
	}
}
