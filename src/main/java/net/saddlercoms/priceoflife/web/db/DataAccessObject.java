package net.saddlercoms.priceoflife.web.db;

import java.time.LocalDate;

import net.saddlercoms.priceoflife.model.StationPrices;
import net.saddlercoms.priceoflife.model.TimePrices;

public interface DataAccessObject {
	StationPrices findById(String stationID);
	TimePrices findByTimes(LocalDate starting, LocalDate until);
	
}
