package Entity;

public class NhanVien {
	private String idNhanVienSo;
	private String tenSo;
	private String thongTinLienLac;
	private int namCongTac;
	private String aCount;
	
	
	public String getIdNhanVienSo() {
		return idNhanVienSo;
	}


	public void setIdNhanVienSo(String idNhanVienSo) {
		this.idNhanVienSo = idNhanVienSo;
	}


	public String getTenSo() {
		return tenSo;
	}


	public void setTenSo(String tenSo) {
		this.tenSo = tenSo;
	}


	public String getThongTinLienLac() {
		return thongTinLienLac;
	}


	public void setThongTinLienLac(String thongTinLienLac) {
		this.thongTinLienLac = thongTinLienLac;
	}


	public int getNamCongTac() {
		return namCongTac;
	}


	public void setNamCongTac(int namCongTac) {
		this.namCongTac = namCongTac;
	}


	public String getaCount() {
		return aCount;
	}


	public void setaCount(String aCount) {
		this.aCount = aCount;
	}


	public NhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}


	public NhanVien(String idNhanVienSo, String tenSo, String thongTinLienLac, int namCongTac, String aCount) {
		super();
		this.idNhanVienSo = idNhanVienSo;
		this.tenSo = tenSo;
		this.thongTinLienLac = thongTinLienLac;
		this.namCongTac = namCongTac;
		this.aCount = aCount;
	}


	
	
	
}
