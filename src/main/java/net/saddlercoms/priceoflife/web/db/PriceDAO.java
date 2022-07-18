package net.saddlercoms.priceoflife.web.db;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
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
import net.saddlercoms.priceoflife.model.Producer.LongProducer;
import net.saddlercoms.priceoflife.model.Producer.StringProducer;
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

//		int idxDblPrice, idxDatRetrievedDate;
		private DecimalProducer dblPrice;
		private DateProducer datRetrievedDate;
		
		public PriceMapper(DecimalProducer dblPrice, DateProducer datRetrievedDate) {
//			this.idxDblPrice = idxDblPrice;
//			this.idxDatRetrievedDate = idxDatRetrievedDate;
			this.dblPrice = dblPrice;
			this.datRetrievedDate = datRetrievedDate;
		}
		
		// complete
//		public CostLine mapRowOld(ResultSet rs, int rowNum) throws SQLException {
//			CostLine cl = new CostLine();
////			cl.setPrice(CostTableValue.PRICE.func.apply(idxDblPrice, rowNum));
//			cl.setPrice(new BigDecimal(rs.getDouble(idxDblPrice)));
//			cl.setRetrievedDate(new Date(rs.getDate(idxDatRetrievedDate).getTime()));
//			return cl;
//		}
		@Override
		public CostLine mapRow(ResultSet rs, int rowNum) throws SQLException {
			CostLine cl = new CostLine();
			cl.setPrice(dblPrice.apply(rs));
			cl.setRetrievedDate(datRetrievedDate.apply(rs));
			return cl;
		}
	}
	private static class StationPricesExtractor implements ResultSetExtractor<List<StationPrices>> {

		private LongProducer intStationId;
		private StringProducer strVendor;
		private StringProducer strStreet;
		private DecimalProducer dblPrice;
		private DateProducer datRetrievedDate;
		
		public StationPricesExtractor(int idxStrVendor, int idxStrStreet, int idxDblPrice, int idxDatRetrievedDate) {
			this.strVendor = new StringProducer(idxStrVendor);
			this.strStreet = new StringProducer(idxStrStreet);
			this.dblPrice = new DecimalProducer(idxDblPrice);
			this.datRetrievedDate = new DateProducer(idxDatRetrievedDate);
			this.strVendor = new StringProducer(idxStrVendor);
		}
		
		
		@Override
		public List<StationPrices> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<StationPrices> stationPrices = new LinkedList<>();
			long stationId = -1;
			StationPrices sp = null;
			while(rs.next()) {
				long localId = rs.getLong(intStationId.apply(rs));
				if(stationId != localId) { 
					sp = new StationPrices();
					sp.setStationId(localId);
					sp.setStreet(strStreet.apply(rs));
					sp.setVendor(strVendor.apply(rs));
					stationPrices.add(sp);
					localId = stationId;
				}
				CostLine cl = new CostLine();
				cl.setPrice(dblPrice.apply(rs));
				cl.setRetrievedDate(new Date(datRetrievedDate.apply(rs).getTime()));
				sp.getCostLines().add(cl);
			}
			return stationPrices;
		} 
		
	}
	
}
