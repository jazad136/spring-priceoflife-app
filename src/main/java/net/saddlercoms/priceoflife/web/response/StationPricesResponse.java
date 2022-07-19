package net.saddlercoms.priceoflife.web.response;

import java.util.LinkedList;
import java.util.List;

import net.saddlercoms.priceoflife.model.StationPrices;

public class StationPricesResponse {
	List<StationPrices> stationPrices;

	public StationPricesResponse() { this.stationPrices = new LinkedList<>(); }
	
	public StationPricesResponse(List<StationPrices> stationPrices) {  setStationPrices(stationPrices);  }
	
	public List<StationPrices> getStationPrices() { return stationPrices; }
	public void setStationPrices(List<StationPrices> stationPrices) { this.stationPrices = stationPrices; }

	@Override
	public String toString() {
		return "StationPriceResponse [stationPrices=" + stationPrices + "]";
	}
}
