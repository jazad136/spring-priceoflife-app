package net.saddlercoms.priceoflife.model;

import java.time.LocalDate;
import java.util.List;

public class TimePrices {
	private Long stationId;
	private String vendor;
	private String cityState;
	private String street;
	private LocalDate firstRetrived;
	private LocalDate lastRetrieved;
	private List<CostLine> costLines;
	
	public Long getStationId() { return stationId; } 
	public void setStationId(Long value) { stationId = value; } 
	
	public String getVendor() { return vendor; }
	public void setVendor(String value) { vendor = value; }

	public String getCityState() { return cityState; }
	public void setCityState(String cityState) { this.cityState = cityState; }
	
	public String getStreet() { return street; }
	public void setStreet(String value) { street = value; }

	public LocalDate getFirstRetrived() { return firstRetrived; }
	public void setFirstRetrived(LocalDate value) { firstRetrived = value; }
	
	public LocalDate getLastRetrieved() { return lastRetrieved; }
	public void setLastRetrieved(LocalDate value) { lastRetrieved = value; }
	
	public List<CostLine> getCostLines() { return costLines; }
	public void setCostLines(List<CostLine> value) { this.costLines = value; }
	
	
	@Override
	public String toString() {
		final int maxLen = 10;
		return "TimePrices [stationId=" + stationId + ", vendor=" + vendor + ", cityState=" + cityState + ", street="
				+ street + ", firstRetrived=" + firstRetrived + ", lastRetrieved=" + lastRetrieved + ", costLines="
				+ (costLines != null ? costLines.subList(0, Math.min(costLines.size(), maxLen)) : null) + "]";
	}
	


	
	
	
	
}
