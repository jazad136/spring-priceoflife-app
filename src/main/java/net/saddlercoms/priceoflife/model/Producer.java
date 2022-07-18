package net.saddlercoms.priceoflife.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Producer<T> implements BiFunction<Integer, ResultSet, T>, Function<ResultSet, T> {
	public abstract T getValue(Integer idx, ResultSet rs);

	public abstract T getValue(ResultSet rs);
	public T apply(Integer idx, ResultSet rs) {
		return getValue(idx, rs);
	}
	
	public T apply(ResultSet rs) { 
		return getValue(rs);
	}
	
	public static class StringProducer extends Producer<String> {
		public String getValue(Integer idx, ResultSet rs) { 
			try { 
				return rs.getString(idx);
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
	}
	
	public static class IntProducer extends Producer<Integer> {
		public Integer getValue(Integer idx, ResultSet rs) { 
			try { 
				return rs.getInt(idx);
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
	}
	
	public static class DecimalProducer extends Producer<BigDecimal> {
		public DecimalProducer(Integer idx) { 
			
		}
		public BigDecimal getValue(Integer idx, ResultSet rs) { 
			try { 
				return new BigDecimal(rs.getDouble(idx));
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
	}
	
	public static class DateProducer extends Producer<Date> {
		public Date getValue(Integer idx, ResultSet rs) { 
			try { 
				return rs.getDate(idx);
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
	}
	
}


