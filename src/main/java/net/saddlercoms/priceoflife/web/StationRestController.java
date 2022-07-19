package net.saddlercoms.priceoflife.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.saddlercoms.priceoflife.web.response.PingResponse;
import net.saddlercoms.priceoflife.web.response.StationPricesResponse;

@RestController()
@RequestMapping(path="/api/prices/gas")
public class StationRestController {
	public final StationService service;

	public StationRestController(StationService service) {
		this.service = service;
	}
	
	@GetMapping
	public StationPricesResponse getAll() { 
		return new StationPricesResponse(service.lookupAll());
	}
	
	@GetMapping("/ping") 
	public PingResponse ping() { 
		return new PingResponse(true);
	}
	
}
