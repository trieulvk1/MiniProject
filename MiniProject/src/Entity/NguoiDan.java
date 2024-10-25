package Entity;

public class NguoiDan {

	private String idNguoiDan;
	private String tenNguoiDan;
	private String diaChi;
	private String soDT;
	private String aCount;
	
	
	
	public NguoiDan() {
		super();
		// TODO Auto-generated constructor stub
	}



	public NguoiDan(String idNguoiDan, String tenNguoiDan, String diaChi, String soDT, String aCount) {
		super();
		this.idNguoiDan = idNguoiDan;
		this.tenNguoiDan = tenNguoiDan;
		this.diaChi = diaChi;
		this.soDT = soDT;
		this.aCount = aCount;
	}



	public String getIdNguoiDan() {
		return idNguoiDan;
	}



	public void setIdNguoiDan(String idNguoiDan) {
		this.idNguoiDan = idNguoiDan;
	}



	public String getTenNguoiDan() {
		return tenNguoiDan;
	}



	public void setTenNguoiDan(String tenNguoiDan) {
		this.tenNguoiDan = tenNguoiDan;
	}



	public String getDiaChi() {
		return diaChi;
	}



	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}



	public String getSoDT() {
		return soDT;
	}



	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}



	public String getaCount() {
		return aCount;
	}



	public void setaCount(String aCount) {
		this.aCount = aCount;
	}
	
	
}
