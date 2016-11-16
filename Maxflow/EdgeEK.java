package main;

public class EdgeEK {
	int source;
	int sink;
	int rev;
	int cap;
	int rCap;
	
	public EdgeEK(int source, int sink, int rev, int cap) {
		setCap(cap);
		setRev(rev);
		setSink(sink);
		setSource(source);
	}
	
	public void setCap(int cap) {
		this.cap = cap;
	}
	
	public void setRev(int rev) {
		this.rev = rev;
	}
	
	public void setSink(int sink) {
		this.sink = sink;
	}
	
	public void setSource(int source) {
		this.source = source;
	}
	
}
