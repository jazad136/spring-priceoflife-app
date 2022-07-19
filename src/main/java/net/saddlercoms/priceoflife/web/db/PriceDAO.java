package net.saddlercoms.priceoflife.web.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import net.saddlercoms.priceoflife.model.CostLine;
import net.saddlercoms.priceoflife.model.Producer.DateProducer;
import net.saddlercoms.priceoflife.model.Producer.DecimalProducer;
import net.saddlercoms.priceoflife.model.Producer.IntProducer;
import net.saddlercoms.priceoflife.model.Producer.LongProducer;
import net.saddlercoms.priceoflife.model.Producer.StringProducer;
import net.saddlercoms.priceoflife.model.StationPrices;
import net.saddlercoms.priceoflife.model.TimePrices;

@Component
public class PriceDAO implements DataAccessObject{

	
	private final String GET_ALL_STATION_PRICE = 
			"SELECT st.station_id, st.vendor, st.city_state, " 
			+ "st.street, p.grade, p.price, p.retrieved_date "
			+ "FROM station st join price p on st.vendor=p.vendor "
			+ "ORDER BY p.retrieved_date ASC, st.vendor ASC;";
	private final String GET_ALL_PRICE = 
			"SELECT vendor, grade, price, retrieved_date"
			+ "FROM price"
			+ "ORDER BY retrieved_date ASC, vendor ASC;";
	
	private final String GET_STATION_COST_BY_VENDOR = 
			"SELECT st.vendor, st.city_state, " 
					+ "st.street, p.grade, p.price, p.retrieved_date "
					+ "FROM station st join price p on st.vendor=p.vendor "
					+ "WHERE st.vendor=? "
					+ "ORDER BY p.retrieved_date ASC;";
	
	/*
	 * 1               2                   3              4                  5        6          7
	 station_id |       vendor       |   city_state   |      street       | grade | price |   retrieved_date
	------------+--------------------+----------------+-------------------+-------+-------+---------------------
	       1000 | Marathon Corunna   | Flint, MI      | 2800 Corunna Rd   |     1 |  4.09 | 2022-03-27 00:00:00
	       1001 | Speedway Miller    | Flint, MI      | 3155 Miller Rd    |     1 |  4.14 | 2022-03-27 00:00:00
	       1002 | Marathon Coldwater | Fort Wayne, IN | 5830 Coldwater Rd |     1 |  4.10 | 2022-03-28 00:00:00
	       1002 | Marathon Coldwater | Fort Wayne, IN | 5830 Coldwater Rd |     2 |  4.50 | 2022-03-28 00:00:00
	 */
	
	private final JdbcTemplate jdbcTemplate;
	
	
	private PriceDAO(JdbcTemplate jdbcTemplate) { 
		this.jdbcTemplate = jdbcTemplate; 
		
	}
	
	public List<StationPrices> findAllData() { 
		LongProducer lngStationId = new LongProducer(1);
		StringProducer strVendor = new StringProducer(2);
		StringProducer strCity = new StringProducer(3);
		StringProducer strStreet = new StringProducer(4);
		IntProducer intGrade = new IntProducer(5);
		DecimalProducer decPrice = new DecimalProducer(6);
		DateProducer datRetrievedDate = new DateProducer(7);
		StationPricesExtractor spe = new StationPricesExtractor(lngStationId, 
				strVendor, strCity, strStreet, intGrade, 
				decPrice, datRetrievedDate);
		return jdbcTemplate.query(GET_ALL_STATION_PRICE, spe);
	}
	
	public List<CostLine> findAllCosts() { 
		/*
		 * 1               2                   3              4                  5        6          7
		 station_id |       vendor       |   city_state   |      street       | grade | price |   retrieved_date
		------------+--------------------+----------------+-------------------+-------+-------+---------------------
		       1000 | Marathon Corunna   | Flint, MI      | 2800 Corunna Rd   |     1 |  4.09 | 2022-03-27 00:00:00
		       1001 | Speedway Miller    | Flint, MI      | 3155 Miller Rd    |     1 |  4.14 | 2022-03-27 00:00:00
		       1002 | Marathon Coldwater | Fort Wayne, IN | 5830 Coldwater Rd |     1 |  4.10 | 2022-03-28 00:00:00
		       1002 | Marathon Coldwater | Fort Wayne, IN | 5830 Coldwater Rd |     2 |  4.50 | 2022-03-28 00:00:00
		 */
		IntProducer intGrade = new IntProducer(5);
		DecimalProducer decPrice = new DecimalProducer(6);
		DateProducer datRetrievedDate = new DateProducer(7);
		PriceMapper pm = new PriceMapper(intGrade, decPrice, datRetrievedDate);
		return jdbcTemplate.query(GET_ALL_PRICE, pm);
	}
	
//	@Override
//	public StationPrices findById(String stationVendor) {
//		StationPrices stp = new StationPrices();
//		
//	}
	
	private static class PriceMapper implements RowMapper<CostLine> {
		private IntProducer intGrade;
		private DecimalProducer dblPrice;
		private DateProducer datRetrievedDate;
		
		public PriceMapper(IntProducer intGrade, DecimalProducer dblPrice, DateProducer datRetrievedDate) {
			this.intGrade = intGrade;
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
			cl.setGrade(intGrade.apply(rs));
			cl.setPrice(dblPrice.apply(rs));
			cl.setRetrievedDate(datRetrievedDate.apply(rs));
			return cl;
		}
	}
	private static class StationPricesExtractor implements ResultSetExtractor<List<StationPrices>> {

		private LongProducer lngStationId;
		private StringProducer strVendor;
		private StringProducer strCityState;
		private StringProducer strStreet;
		private IntProducer intGrade;
		private DecimalProducer decPrice;
		private DateProducer datRetrievedDate;

		public StationPricesExtractor(LongProducer lngStationId, 
				StringProducer strVendor, 
				StringProducer strCityState, 
				StringProducer strStreet, 
				IntProducer intGrade,
				DecimalProducer decPrice,
				DateProducer datRetrievedDate) {
			this.lngStationId = lngStationId;
			this.strVendor = strVendor;
			this.strCityState = strCityState;
			this.strStreet = strStreet;
			this.intGrade = intGrade;
			this.decPrice = decPrice;
			this.datRetrievedDate = datRetrievedDate;
		}
		
		@Override
		public List<StationPrices> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<StationPrices> stationPrices = new LinkedList<>();
			long lastId = -1;
			StationPrices sp = null;
			while(rs.next()) {
				long localId = lngStationId.apply(rs);
				if(localId != lastId) { 
					sp = new StationPrices();
					sp.setStationId(localId);
					sp.setVendor(strVendor.apply(rs));
					sp.setCityState(strCityState.apply(rs));
					sp.setStreet(strStreet.apply(rs));
					stationPrices.add(sp);
					sp.setCostLines(new LinkedList<>());
					lastId = localId;
				}
				CostLine cl = new CostLine();
				cl.setGrade(intGrade.apply(rs));
				cl.setPrice(decPrice.apply(rs));
				cl.setRetrievedDate(new Date(datRetrievedDate.apply(rs).getTime()));
				sp.getCostLines().add(cl);
			}
			return stationPrices;
		} 
		
	}
	@Override
	public TimePrices findByTimes(LocalDate starting, LocalDate until) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StationPrices findById(String stationID) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
