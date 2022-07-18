package net.saddlercoms.priceoflife.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Producer<T> implements Function<ResultSet, T> {
	
	public abstract T getValue(Integer idx, ResultSet rs);

	public abstract T getValue(ResultSet rs);
	
	public T apply(Integer idx, ResultSet rs) {
		return getValue(idx, rs);
	}
	
	public T apply(ResultSet rs) { 
		return getValue(rs);
	}
	
	public static class StringProducer extends Producer<String> {
		
		private int resultSetIndex;
		
		public StringProducer(int resultSetIndex) { 
			this.resultSetIndex = resultSetIndex;
		}
		public String getValue(Integer idx, ResultSet rs) { 
			try { 
				return rs.getString(idx);
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
		
		public String getValue(ResultSet rs) { 
			try { return rs.getString(resultSetIndex); } 
			catch(SQLException e) {  throw new RuntimeException(e); } 
		}
	}
	
	public static class IntProducer extends Producer<Integer> {
		
		private int resultSetIndex;
		
		public Integer getValue(Integer idx, ResultSet rs) { 
			try { 
				return rs.getInt(idx);
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
		public Integer getValue(ResultSet rs) { 
			try { return rs.getInt(resultSetIndex); } 
			catch(SQLException e) {  throw new RuntimeException(e); } 
		}
	}
	
	public static class LongProducer extends Producer<Integer> {
		
		private int resultSetIndex;
		
		public Long getValue(Integer idx, ResultSet rs) { 
			try { 
				return rs.getLong(idx);
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
		public Long getValue(ResultSet rs) { 
			try { return rs.getLong(resultSetIndex); } 
			catch(SQLException e) {  throw new RuntimeException(e); } 
		}
	}
	
	public static class DecimalProducer extends Producer<BigDecimal> {
		
		private int resultSetIndex;
		
		public DecimalProducer(Integer idx) { 
			this.resultSetIndex = idx;
		}
		public BigDecimal getValue(Integer idx, ResultSet rs) { 
			try { 
				return new BigDecimal(rs.getDouble(idx));
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
		public BigDecimal getValue(ResultSet rs) { 
			try { return new BigDecimal(rs.getDouble(resultSetIndex)); } 
			catch(SQLException e) {  throw new RuntimeException(e); } 
		}
	}
	
	public static class DateProducer extends Producer<Date> {
		private int resultSetIndex;
		
		public DateProducer(Integer idx) { 
			this.resultSetIndex = idx;
		}
		
		public Date getValue(Integer idx, ResultSet rs) { 
			try { 
				return rs.getDate(idx);
			} 
			catch(SQLException e) { 
				throw new RuntimeException(e); 
			} 
		}
		public Date getValue(ResultSet rs) { 
			try { return rs.getDate(resultSetIndex); } 
			catch(SQLException e) {  throw new RuntimeException(e); } 
		}
	}
	
}


