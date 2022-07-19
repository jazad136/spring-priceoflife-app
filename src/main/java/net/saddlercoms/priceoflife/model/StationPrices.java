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

import java.util.List;

public class StationPrices {
	private Long stationId;
	private String vendor;
	private String street;
	private String cityState;
	private List<CostLine> costLines;
	
	public StationPrices() { } 
	
	public Long getStationId() { return stationId; } 
	public void setStationId(Long value) { this.stationId = value; } 
	
	public String getVendor() { return vendor; }
	public void setVendor(String value) { this.vendor = value; }
	
	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }

	public String getCityState() { return cityState; } 
	public void setCityState(String value) { this.cityState = value; }
	
	public List<CostLine> getCostLines() { return costLines; }
	public void setCostLines(List<CostLine> costLines) { this.costLines = costLines; }

	@Override
	public String toString() {
		final int maxLen = 10;
		return "StationPrices [stationId=" + stationId + ", vendor=" + vendor + ", street=" + street + ", cityState="
				+ cityState + ", costLines="
				+ (costLines != null ? costLines.subList(0, Math.min(costLines.size(), maxLen)) : null) + "]";
	}	
}
