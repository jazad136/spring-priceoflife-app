package net.saddlercoms.priceoflife.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.saddlercoms.priceoflife.model.StationPrices;
import net.saddlercoms.priceoflife.web.db.PriceDAO;

@Service
public class StationService {
	public final PriceDAO priceDAO;
	private static final Logger LOG = LoggerFactory.getLogger(StationService.class);
	@Autowired
	public StationService(PriceDAO priceDAO) {
		this.priceDAO = priceDAO;
	}
	
	
	public List<StationPrices> lookupAll() { 
		LOG.info("Lookup ALL StationPrices objects");
		return priceDAO.findAllData();
	}
	
}
