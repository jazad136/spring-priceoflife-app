package net.saddlercoms.priceoflife.model;

import net.saddlercoms.priceoflife.model.Producer.DateProducer;
import net.saddlercoms.priceoflife.model.Producer.DecimalProducer;
import net.saddlercoms.priceoflife.model.Producer.IntProducer;
import net.saddlercoms.priceoflife.model.Producer.StringProducer;

public enum CostTableValue {
/*
 SELECT st.name, st.city_state, st.street, p.grade, p.price, p.retrieved_date 
 FROM station st join cost p on st.name=p.name 
 WHERE st.name=?
 ORDER BY p.retrieved_date;
 */
	VENDOR(new StringProducer()),
	CITY_STATE(new StringProducer()),
	GRADE(new IntProducer()),
	PRICE(new DecimalProducer()),
	RETRIEVED_DATE(new DateProducer());
	public final Producer<?> func;
	
	CostTableValue(Class<?> expectedOutput) { 
		this.func = null;
	}
	CostTableValue(Producer<?> output) { 
		this.func = output;
	}
	
	
	
	
}
