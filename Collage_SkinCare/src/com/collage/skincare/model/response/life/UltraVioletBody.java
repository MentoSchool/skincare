package com.collage.skincare.model.response.life;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 7. 오후 1:22:57
 */
@XStreamAlias("Body")
public class UltraVioletBody implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9218776862169766053L;

	@XStreamAlias("IndexModel")
	private IndexModel indexModel;

	public IndexModel getIndexModel()
	{
		return indexModel;
	}

	public void setIndexModel(IndexModel indexModel)
	{
		this.indexModel = indexModel;
	}
}
