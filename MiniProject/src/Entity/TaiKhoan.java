package Entity;

public class TaiKhoan {
private String aCount;
private String pass;
private int sTT;


public TaiKhoan() {
	super();
	// TODO Auto-generated constructor stub
}


public TaiKhoan(String aCount, String pass, int sTT) {
	super();
	this.aCount = aCount;
	this.pass = pass;
	this.sTT = sTT;
}


public String getaCount() {
	return aCount;
}


public void setaCount(String aCount) {
	this.aCount = aCount;
}


public String getPass() {
	return pass;
}


public void setPass(String pass) {
	this.pass = pass;
}


public int getsTT() {
	return sTT;
}


public void setsTT(int sTT) {
	this.sTT = sTT;
}


}
