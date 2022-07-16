/**
   Copyright 2022 Jonathan A. Saddler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.saddlercoms.priceoflife.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

/** 
 * Source code for Cost. 
 */
public class CostLine {
	
	private BigDecimal price;
	private Date retrievedDate;
	
	private final DecimalFormat priceFormat;
	public CostLine() { 
		priceFormat = (DecimalFormat)DecimalFormat.getInstance();
		priceFormat.setMinimumFractionDigits(2);
		priceFormat.setMaximumFractionDigits(2);
		priceFormat.setRoundingMode(RoundingMode.HALF_EVEN);
	}
	
	public DecimalFormat getCostLinePriceFormat() { return priceFormat; } 
	
	public String getPrice() { return priceFormat.format(price); }
	public BigDecimal getPriceDecimal() { return price; } 
	public void setPrice(BigDecimal price) { this.price = price; }
	
	public Date getRetrievedDate() { return retrievedDate; }
	public void setRetrievedDate(Date retrievedDate) { this.retrievedDate = retrievedDate; }

	@Override
	public String toString() {
		return "CostLine [price=" + price + ", retrievedDate=" + retrievedDate + "]";
	}


}
