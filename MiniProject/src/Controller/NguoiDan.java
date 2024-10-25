package Controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import ConnectionMN.ConnectionSQL;
import Miniproject_Main.Validate;
import Services.NguoiDan_VanNan_Services;

public class NguoiDan {
	static Scanner sc = new Scanner(System.in);

	// hiển thị tất cả tài khoản lên hệ thống
	public static void hienThiTK() {
		Connection connection = ConnectionSQL.getConnection();
		List<String> listNguoiDan = NguoiDan_VanNan_Services.listTK(connection);
		System.out.println("Tài khoản người dân:");
		for (String taikhoan : listNguoiDan) {
			System.out.println(taikhoan);
		}
		if (listNguoiDan.size() == 0) {
			System.out.println("Không có tài khoản nào được đăng ký!!");
		}
	}

	public static void hienThiTK1() {
		Connection connection = ConnectionSQL.getConnection();
		List<String> listNhanVien = NguoiDan_VanNan_Services.listTK1(connection);
		System.out.println("Tài khoản nhân viên:");
		for (String taikhoan : listNhanVien) {
			System.out.println(taikhoan);
		}
		if (listNhanVien.size() == 0) {
			System.out.println("Không có tài khoản nhân viên nào được đăng ký!!");
		}
	}

	// '- Thống kê thông tin người dân và số vấn nạn đã báo cáo mà chưa được phê
	// duyệt
	public static void hienThiVNND() {
		Connection connection = ConnectionSQL.getConnection();
		List<String> listVanNan = NguoiDan_VanNan_Services.thongKeTTND(connection);
		if(listVanNan.size()==0) {
			System.out.println("Không có vấn nạn nào người dân đăng mà chưa được phê duyệt");
		}else {
		System.out.println("Thông tin người dân báo cáo vấn nạn mà chưa được phê duyệt: ");
		
		for (String vanNan : listVanNan) {
			System.out.println(vanNan);
		}
		}
	}

	public static void hienThiNVS() {
		Connection connection = ConnectionSQL.getConnection();
		List<String> listVanNan = NguoiDan_VanNan_Services.thongKeNVS(connection);
		if(listVanNan.size()==0) {
			System.out.println("Không có vấn nạn nào được xử lý hơn 1 tháng mới xong");
		}else {
			
		System.out.println("Những vấn nạn được xử lý hơn 1 tháng mới xong" );
		for (String vanNan : listVanNan) {
			System.out.println(vanNan);
		}
		}
	}

	// '-Liệt kê thông tin Top 3 User người dân đăng nhiều bài nhất theo từng tháng
	// (tháng X nhập từ bàn phím)
	public static void hienThiTop3VN() {
		Connection connection = ConnectionSQL.getConnection();
		int thangDang;
		do {
			try {
				System.out.println("Nhập tháng đăng vấn nạn: ");
				thangDang = Integer.parseInt(sc.nextLine());
				if (Validate.checkThang(connection, thangDang) == true) {
					if (Validate.checkThangDangVN(connection, thangDang) == false) {
						break;
					} else {
						break;
					}
				} else {
					System.out.println("Nhập tháng không hợp lệ!!");
				}
			} catch (NumberFormatException e) {
				System.out.println("Nhập tháng không hợp lệ!!.");
			}

		} while (true);
		
		List<String> listVanNan = NguoiDan_VanNan_Services.lietKetop3ND(connection, thangDang);
		if(listVanNan.size()==0) {
			System.out.println("Không có người dân nào đăng vấn nạn trong tháng "+thangDang);
		}else {
			System.out.println("Top 3 người dân đăng vấn nạn nhiều nhất theo tháng "+thangDang);
		for (String vanNan : listVanNan) {
			System.out.println(vanNan);
		}
		}

	}

	// '-Liệt kê tổng số người dân tham gia đăng bài "đã được phê duyệt"của mỗi
	// Tỉnh/Thành trong từng Quý (Quý nhập từ bàn phím)
	public static void hienthiNDTGDB() {
		Connection connection = ConnectionSQL.getConnection();
		int quy;
		do {
			try {
				System.out.println("Nhập quý muốn tìm kiếm: ");
				quy = Integer.parseInt(sc.nextLine());
				if (Validate.checkQuy(connection, quy) == true) {
					if (Validate.checkQuyDangVN(connection, quy) == true) {
						break;
					} else {
						break;
					}
				} else {
						System.out.println("Nhập quý không hợp lệ!!");
				}
			} catch (NumberFormatException e) {
				System.out.println("Nhập quý không hợp lệ!!.");
			}
		} while (true);
		List<String> listVanNan = NguoiDan_VanNan_Services.lietKeNDTGDB(connection, quy);
		if(listVanNan.size()==0) {
			System.out.println("Không có người dân đăng vấn nạn trong quý "+quy);
		}else {
			System.out.println("Tổng số người dân đăng bài đã được phê duyệt của mỗi thành phố theo quý "+quy);
		for (String vanNan : listVanNan) {
			System.out.println(vanNan);
		}
		}
	}

}
