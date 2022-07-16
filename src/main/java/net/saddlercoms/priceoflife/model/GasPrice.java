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



public class GasPrice {
	private Long stationId;
	private String name;
	private Integer grade; 
	private List<CostLine> costLines;
	
	public GasPrice() { } 
	
	public Long getStationId() { return stationId; } 
	public void setStationId(Long value) { this.stationId = value; } 
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getGasGrade() { return grade; }
	public void setGasGrade(Integer value) { this.grade = value; }
	
	public List<CostLine> getCostLines() { return costLines; }
	public void setCostLines(List<CostLine> costLines) { this.costLines = costLines; }
	
	@Override
	public String toString() {
		return "GasPrice [stationId=" + stationId + ", name=" + name + ", grade=" + grade + ", costLines=" + costLines
				+ "]";
	}
	
	
	
}
