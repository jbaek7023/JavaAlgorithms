package main;

public class EdgeD {
	int sink;
	int rev;
	int cap;
	int rCap;
	
	public EdgeD(int cap, int rev, int sink) {
		setCap(cap);
		setRev(rev);
		setSink(sink);
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
}
