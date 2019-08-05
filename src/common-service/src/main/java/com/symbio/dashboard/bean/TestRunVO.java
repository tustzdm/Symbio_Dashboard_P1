package com.symbio.dashboard.bean;

import com.symbio.dashboard.enums.Locales;
import lombok.Data;

/**
 * Page Object
 * 
 * @author Shawn
 * 
 */
@Data
public class TestRunVO implements java.io.Serializable {
	// Fix parameter
	private String token;
	private String locale = Locales.EN_US.toString();

	// business info
	private Integer userId;
	private Integer productId;
	private Integer releaseId;
	private Integer testSetId;

	// page info
	private Integer pageIndex;
	private Integer pageSize;

	@Override
	public String toString(){
		return new StringBuffer().append("token = ").append(token)
				.append(", locale = ").append(locale)
				.append(", userId = ").append(userId)
				.append(", productId = ").append(productId)
				.append(", releaseId = ").append(releaseId)
				.append(", testSetId = ").append(testSetId)
				.append(", pageIndex = ").append(pageIndex)
				.append(", pageSize = ").append(pageSize)
				.toString();
	}
}
