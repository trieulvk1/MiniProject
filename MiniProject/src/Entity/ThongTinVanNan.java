package Entity;

import java.util.Date;

public class ThongTinVanNan {
	private  String idVanNan;
	private  String tenVanNan;
	private  String diaChi;
	private  String moTa;
	private  String loaiVanNan;
	private  Date ngayDang;
	private  String hinhAnhDinhKem;
	private  String trangThai;
	private  String idNguoiDan;
	private  int soLuong;
	private  int mucDoNghiemTrong;
	private  String tinhTrang;
	
	
	

	public ThongTinVanNan(String idVanNan, String tenVanNan, String diaChi, String moTa, String loaiVanNan,
			Date ngayDang, String hinhAnhDinhKem, String trangThai, String idNguoiDan, String tinhTrang) {
		super();
		this.idVanNan = idVanNan;
		this.tenVanNan = tenVanNan;
		this.diaChi = diaChi;
		this.moTa = moTa;
		this.loaiVanNan = loaiVanNan;
		this.ngayDang = ngayDang;
		this.hinhAnhDinhKem = hinhAnhDinhKem;
		this.trangThai = trangThai;
		this.idNguoiDan = idNguoiDan;
		this.tinhTrang = tinhTrang;
	}

	public int getMucDoNghiemTrong() {
		return mucDoNghiemTrong;
	}

	public void setMucDoNghiemTrong(int mucDoNghiemTrong) {
		this.mucDoNghiemTrong = mucDoNghiemTrong;
	}

	public ThongTinVanNan(String loaiVanNan, int soLuong) {
		super();
		this.idVanNan = loaiVanNan;
		this.loaiVanNan = loaiVanNan;
		this.soLuong = soLuong;
		this.mucDoNghiemTrong = soLuong;
	}

	///lấy validate 2 thuộc tính
	public ThongTinVanNan(String idVanNan, String idNguoiDan) {
		super();
		this.idVanNan = idVanNan;
		this.idNguoiDan = idNguoiDan;
	}
	
	///lấy thuộc tính để update thông tin
	public ThongTinVanNan(String idVanNan, String moTa, String loaiVanNan) {
		super();
		this.idVanNan = idVanNan;
		this.moTa = moTa;
		this.loaiVanNan = loaiVanNan;
	}

	@Override
	public String toString() {
		return "ThongTinVanNan [idVanNan=" + idVanNan + ", tenVanNan=" + tenVanNan + ", diaChi=" + diaChi + ", moTa="
				+ moTa + ", loaiVanNan=" + loaiVanNan + ", ngayDang=" + ngayDang + ", hinhAnhDinhKem=" + hinhAnhDinhKem
				+ ", trangThai=" + trangThai  + ", tinhTrang=" + tinhTrang + ", idNguoiDan=" + idNguoiDan+ "]";
	}
	
	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public ThongTinVanNan() {
		super();
	}
	public ThongTinVanNan(String idVanNan, String tenVanNan, String diaChi, String moTa, String loaiVanNan,
			Date ngayDang, String hinhAnhDinhKem, String trangThai, String idNguoiDan) {
		super();
		this.idVanNan = idVanNan;
		this.tenVanNan = tenVanNan;
		this.diaChi = diaChi;
		this.moTa = moTa;
		this.loaiVanNan = loaiVanNan;
		this.ngayDang = ngayDang;
		this.hinhAnhDinhKem = hinhAnhDinhKem;
		this.trangThai = trangThai;
		this.idNguoiDan = idNguoiDan;
	}
	public String getIdVanNan() {
		return idVanNan;
	}
	public void setIdVanNan(String idVanNan) {
		this.idVanNan = idVanNan;
	}
	public String getTenVanNan() {
		return tenVanNan;
	}
	public void setTenVanNan(String tenVanNan) {
		this.tenVanNan = tenVanNan;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public String getLoaiVanNan() {
		return loaiVanNan;
	}
	public void setLoaiVanNan(String loaiVanNan) {
		this.loaiVanNan = loaiVanNan;
	}
	public Date getNgayDang() {
		return ngayDang;
	}
	public void setNgayDang(Date ngayDang) {
		this.ngayDang = ngayDang;
	}
	public String getHinhAnhDinhKem() {
		return hinhAnhDinhKem;
	}
	public void setHinhAnhDinhKem(String hinhAnhDinhKem) {
		this.hinhAnhDinhKem = hinhAnhDinhKem;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public String getIdNguoiDan() {
		return idNguoiDan;
	}
	public void setIdNguoiDan(String idNguoiDan) {
		this.idNguoiDan = idNguoiDan;
	}

	public String getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public String toTableString() {
		return "ThongTinVanNan [idVanNan=" + idVanNan + ", tenVanNan=" + tenVanNan + ", diaChi=" + diaChi + ", moTa="
				+ moTa + ", loaiVanNan=" + loaiVanNan + ", ngayDang=" + ngayDang + ", hinhAnhDinhKem=" + hinhAnhDinhKem
				+ ", trangThai=" + trangThai  +  ", idNguoiDan=" + idNguoiDan+ "]";
	}

}
