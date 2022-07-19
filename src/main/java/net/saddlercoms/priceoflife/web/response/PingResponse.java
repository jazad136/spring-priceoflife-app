package net.saddlercoms.priceoflife.web.response;

public class PingResponse {
	
	boolean success;
	public PingResponse() {  }
	public PingResponse(boolean successVal) { this.success = successVal; }
	
	public boolean getSuccess() { return success; }
	
	@Override public String toString() { return "PingResponse [success=" + success + "]"; } 
	
}
