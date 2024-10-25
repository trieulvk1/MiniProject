package Services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entity.NguoiDan;

public class NguoiDan_VanNan_Services {
	// hiển thị danh sách tài khoản người dân
	public static List<String> listTK(Connection connection) {
		ArrayList<String> listTaiKhoan = new ArrayList<String>();
		String list = "select nd.aCount, pass, sTT, nd.idNguoiDan, nd.tenNguoiDan,nd.diaChi, nd.soDT from UserLog us\r\n"
				+ "join NguoiDan nd on us.aCount=nd.aCount where sTT=0";
		try {
			PreparedStatement ptsm = connection.prepareStatement(list);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				String aCount = rs.getString("aCount");
				String pass = rs.getString("pass");
				String sTT = rs.getString("sTT");
				String idNguoiDan = rs.getString("idNguoiDan");
				String tenNguoiDan = rs.getString("tenNguoiDan");
				String diaChi = rs.getString("diaChi");
				String soDT = rs.getString("soDT");
				listTaiKhoan.add("Acount: " + aCount + "\t" + "ID Người dân: " + idNguoiDan + "\t" + " Tên người dân: "
						+ tenNguoiDan + "\t" + " Địa chỉ: " + diaChi + "\t" + " Số điện thoại: " + soDT);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTaiKhoan;
	}

	// hiển thị danh sách nhân viên sở
	public static List<String> listTK1(Connection connection) {
		ArrayList<String> listTaiKhoan = new ArrayList<String>();
		String list = "select nvs.aCount, pass, sTT, nvs.idNhanVienSo, nvs.tenSo,nvs.thongTinLienLac, nvs.namCongtac from UserLog us\r\n"
				+ "join NhanVienSo nvs on us.aCount=nvs.aCount where sTT=1";
		try {
			PreparedStatement ptsm = connection.prepareStatement(list);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				String aCount = rs.getString("aCount");
				String pass = rs.getString("pass");
				String sTT = rs.getString("sTT");
				String idNhanVienSo = rs.getString("idNhanVienSo");
				String tenSo = rs.getString("tenSo");
				String thongTinLienLac = rs.getString("thongTinLienLac");
				String namCongTac = rs.getString("namCongTac");
				listTaiKhoan.add("Acount: " + aCount + "\t" + "ID Nhân viên: " + idNhanVienSo + "\t"
						+ " Tên nhân viên: " + tenSo + "\t" + " Thông tin liên lạc: " + thongTinLienLac + "\t"
						+ " Năm công tác: " + namCongTac);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTaiKhoan;
	}


	/// Thống kê thông tin người dân và số
	// vấn nạn đã báo cáo mà chưa được phê duyệt
	public static List<String> thongKeTTND(Connection connection) {
		ArrayList<String> listND = new ArrayList<String>();
		String lk = "select nd.idNguoiDan, nd.tenNguoiDan, nd.diaChi, nd.soDT, count(idVanNan) as'Tong' from NguoiDan nd \r\n"
				+ "join VanNan vn on nd.idNguoiDan=vn.idNguoiDan \r\n"
				+ "where trangThai like N'Chưa phê duyệt'\r\n"
				+ "group by nd.idNguoiDan, nd.tenNguoiDan, nd.diaChi, nd.soDT";
		try {
			PreparedStatement ptsm = connection.prepareStatement(lk);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				String idNguoiDan = rs.getString("idNguoiDan");
				String tenNguoiDan = rs.getString("tenNguoiDan");
				String diaChi = rs.getString("diaChi");
				String soDT = rs.getString("soDT");
				int tong = rs.getInt("Tong");
				listND.add("ID Người dân: " + idNguoiDan + "\t" + " Tên người dân: " + tenNguoiDan + "\t" + " Địa chỉ: "
						+ diaChi + "\t" + " Số điện thoại: " + soDT+ "\t" + "Tổng số lần đăng: " + tong );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listND;
	}

	// - Liệt kê thông tin người đại diện xử lý các vấn nạn nhiều hơn 1 tháng mới
	// xong
	public static List<String> thongKeNVS(Connection connection) {
		ArrayList<String> ttNVS = new ArrayList<>();
		String sql = "select nvs.idNhanVienSo,nvs.tenSo,nvs.thongTinLienLac,namCongTac,vn.idVanNan,vn.tenVanNan, ttxl.ngayBatDau,ttxl.ngayKetThuc from NhanVienSo nvs \r\n"
				+ "join TinhTrangXuLy ttxl on nvs.idNhanVienSo=ttxl.idNhanVienSo join VanNan vn on ttxl.idVanNan=vn.idVanNan \r\n"
				+ "where datediff(DAY,ngayBatDau,ngayKetThuc) > 31 order by nvs.idNhanVienSo";
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				String idNhanVienSo = rs.getString("idNhanVienSo");
				String tenNhanVienSo = rs.getString("tenSo");
				String thongTinLienLac = rs.getString("thongTinLienLac");
				String namCongTac = rs.getString("namCongTac");
				String idVanNan = rs.getString("idVanNan");
				String tenVanNan = rs.getString("tenVanNan");
				Date ngayBatDau = rs.getDate("ngayBatDau");
				Date ngayKetThuc = rs.getDate("ngayKetThuc");
				ttNVS.add("ID nhân viên: " + idNhanVienSo + "\t" + "Tên nhân viên sở: " + tenNhanVienSo + "\t"
						+ "Thông tin liên lạc: " + thongTinLienLac + "\t" + "Năm công tác: " + namCongTac + "\t"
						+ "Mã vấn nạn: " + idVanNan + "\t" + "Tên vấn nạn: " + tenVanNan + "\t" + "Ngày xử lý: "
						+ ngayBatDau + "\t" + "Ngày hoàn thành: " + ngayKetThuc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ttNVS;
	}

	// '-Liệt kê thông tin Top 3 User người dân đăng nhiều bài nhất theo từng tháng
	// (tháng X nhập từ bàn phím)
	public static List<String> lietKetop3ND(Connection connection, int thangDang) {
		ArrayList<String> top3VN = new ArrayList<>();
		String sql = " select nd.idNguoiDan, nd.tenNguoiDan, nd.diaChi, count(nd.idNguoiDan) as Count from NguoiDan nd\r\n"
				+ "join VanNan vn on nd.idNguoiDan=vn.idNguoiDan\r\n"
				+ "where nd.idNguoiDan in (select top 3 idNguoiDan from VanNan   group by idNguoiDan order by count(idVanNan) desc) and MONTH(ngayDang)= ?\r\n"
				+ "group by nd.idNguoiDan, nd.tenNguoiDan, nd.diaChi ";
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setInt(1, thangDang);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				String idNguoiDan = rs.getString("idNguoiDan");
				String tenNguoiDan = rs.getString("tenNguoiDan");
				String diaChi = rs.getString("diaChi");
				int count = rs.getInt("Count");
				top3VN.add("ID người dân: " + idNguoiDan + "\t" + "Tên người dân: " + tenNguoiDan + "\t" + "Địa chỉ: "
						+ diaChi + "\t" + "Tổng số lần đăng: " + count);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return top3VN;
	}

	// '-Liệt kê tổng số người dân tham gia đăng bài "đã được phê duyệt"của mỗi
	// Tỉnh/Thành trong từng Quý (Quý nhập từ bàn phím)
	public static List<String> lietKeNDTGDB(Connection connection, int thangDang) {
		ArrayList<String> NDTGDB = new ArrayList<>();
		String sql = "select diaChi as diachi , count(idNguoiDan) as Total from VanNan\r\n"
				+ " where trangThai LIKE N'Đã phê duyệt' AND DATEPART(QUARTER, ngayDang) = ?\r\n"
				+ " group by diaChi;";
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setInt(1, thangDang);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				String diaChi = rs.getString("diachi");
				int tong = rs.getInt("Total");
				NDTGDB.add( "Tổng số người dân đăng bài: " + tong+ "\t" +"Địa chỉ: " + diaChi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NDTGDB;

	}
}
