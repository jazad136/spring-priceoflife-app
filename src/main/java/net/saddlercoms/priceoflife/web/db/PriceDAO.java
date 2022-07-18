package net.saddlercoms.priceoflife.web.db;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import net.saddlercoms.priceoflife.model.CostLine;
import net.saddlercoms.priceoflife.model.CostTableValue;
import net.saddlercoms.priceoflife.model.Producer.DateProducer;
import net.saddlercoms.priceoflife.model.Producer.DecimalProducer;
import net.saddlercoms.priceoflife.model.StationPrices;

@Component
public class PriceDAO implements DataAccessObject{

	private final String GET_ALL_STATION = "SELECT vendor, city_state, street, FROM station;";
	
	private final String GET_ROOM_COST_BY_VENDOR = 
			"SELECT st.vendor, st.name, "
			+ "st.city_state, r.street, "
			+ "p.price, p.retrieved_date FROM station st "
			+ "join price p on st.vendor=p.vendor "
			+ "WHERE r.vendor=? ORDER BY c.retrieved_date";
	
	private final JdbcTemplate jdbcTemplate;
	
	
	private PriceDAO(JdbcTemplate jdbcTemplate) { 
		this.jdbcTemplate = jdbcTemplate; 
		
	}
	
	public StationPrices findAllData() { 
		int idxDblPrice = 5, idxDatRetrievedDate = 6;
		StationPricesExtractor spe = new StationPricesExtractor(idxDblPrice, idxDatRetrievedDate);
		return jdbcTemplate.query(GET_ALL_STATION, spe);
	}
	public List<CostLine> findAllCosts() { 
		int idxDblPrice = 5, idxDatRetrievedDate = 6;
//		StationPriceExtractor spe = new StationPriceExtractor(idxDblPrice, idxDatRetrievedDate);
		PriceMapper spe = new PriceMapper(idxDblPrice, idxDatRetrievedDate);
		return jdbcTemplate.query(GET_ALL_STATION, spe);
	}
	
	@Override
	public StationPrices findById(String stationVendor) {
		return new StationPrices();
	}
	
	private static class PriceMapper implements RowMapper<CostLine> {

		int idxDblPrice, idxDatRetrievedDate;
		private DecimalProducer dblPrice;
		private DateProducer datRetrievedDate;
		
		public PriceMapper(DecimalProducer dblPrice, DateProducer datRetrievedDate) {
//			this.idxDblPrice = idxDblPrice;
//			this.idxDatRetrievedDate = idxDatRetrievedDate;
			this.dblPrice = dblPrice;
			this.datRetrievedDate = datRetrievedDate;
		}
		
		public PriceMapper() { 
			idxDblPrice = CostTableValue.PRICE.ordinal() + 1;
			idxDatRetrievedDate = CostTableValue.RETRIEVED_DATE.ordinal() + 1;
		}
		
		// complete
		@Override
		public CostLine mapRow(ResultSet rs, int rowNum) throws SQLException {
			CostLine cl = new CostLine();
//			cl.setPrice(CostTableValue.PRICE.func.apply(idxDblPrice, rowNum));
			cl.setPrice(new BigDecimal(rs.getDouble(idxDblPrice)));
			cl.setRetrievedDate(new Date(rs.getDate(idxDatRetrievedDate).getTime()));
			return cl;
		}
		public CostLine mapRow2(ResultSet rs, int rowNum) throws SQLException {
			CostLine cl = new CostLine();
			cl.setPrice(CostTableValue.PRICE.func.apply(idxDblPrice, rs));
			cl.setPrice(new BigDecimal(rs.getDouble(idxDblPrice)));
			cl.setRetrievedDate(new Date(rs.getDate(idxDatRetrievedDate).getTime()));
			return cl;
		}
	}
	private static class StationPricesExtractor implements ResultSetExtractor<StationPrices> {

		int idxDblPrice, idxDatRetrievedDate;
		
		public StationPricesExtractor(int idxDblPrice, int idxDatRetrievedDate) {
			this.idxDblPrice = idxDblPrice;
			this.idxDatRetrievedDate = idxDatRetrievedDate;
		}
		
		
		@Override
		public StationPrices extractData(ResultSet rs) throws SQLException, DataAccessException {
			StationPrices sp = null;
			List<CostLine> stationPrices = new LinkedList<>();
			long stationId = -1;
			while(rs.next()) {
				if(stationId == -1) { 
					sp = new StationPrices();
					sp.setStationId(stationId);
				}
				CostLine cl = new CostLine();
				cl.setPrice(new BigDecimal(rs.getDouble(idxDblPrice)));
				cl.setRetrievedDate(new Date(rs.getDate(idxDatRetrievedDate).getTime()));
				stationPrices.add(cl);
			}
			return stationPrices;
		} 
		
	}
	
}
