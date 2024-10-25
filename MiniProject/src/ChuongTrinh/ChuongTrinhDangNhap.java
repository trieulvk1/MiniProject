package ChuongTrinh;

import java.sql.Connection;
import java.util.Scanner;

import ChuongTrinh.ChuongTrinhVanNan;
import Controller.NguoiDan;
import Controller.NhapThonngTinVanNan;
import Controller.TaiKhoan;
import Miniproject_Main.Validate;

public class ChuongTrinhDangNhap {
	static Scanner scanner = new Scanner(System.in);

	// Đăng nhâp admin (stt=2);
	public static void logAdmin(Connection connection) {
		do {
			System.out.println("<--CHƯƠNG TRÌNH QUẢN LÝ-->");
			System.out.println("1. Hiển thị danh sách tài khoản");
			System.out.println("2. Xóa thông tin người dân");
			System.out.println("3. Xóa thông tin nhân viên");
			System.out.println("4. Cập nhật thông tin người dân");
			System.out.println("5. Cập nhật thông tin nhân viên");
			System.out.println("6. Thay đổi quyền truy cập tài khoản");
			System.out.println("7. Thống kê thông tin người dân và số vấn nạn đã báo cáo mà chưa được phê duyệt");
			System.out.println("8. Hiển thị thông tin người đại diện xử lý các vấn nạn nhiều hơn 1 tháng mới xong");
			System.out.println("9. Hiển thị thông tin Top 3 User người dân đăng nhiều bài nhất theo từng tháng");
			System.out.println(
					"10. Hiển thị tổng số người dân tham gia đăng bài \"đã được phê duyệt\" của mỗi Tỉnh/Thành trong từng Quý");
			System.out.println("11. Đăng xuất");
			System.out.print("Mời nhập lựa chọn: ");
			try {
				int key = Integer.parseInt(scanner.nextLine());

				if ((key > 1 && key < 10) && Validate.checkChuongTrinhND(connection) == 0) {
					System.out.println("Hiện chưa có người dân nào. Xin vui lòng truy cập lại sau.");
				} else {
					switch (key) {
					case 1: {
						Controller.NguoiDan.hienThiTK();
						Controller.NguoiDan.hienThiTK1();
						break;
					}
					case 2: {
						TaiKhoan.xoaTaiKhoan();
						break;
					}
					case 3: {
						TaiKhoan.xoaTaiKhoanNV();
						break;
					}
					case 4: {
						TaiKhoan.capNhapNguoiDan();
						break;
					}
					case 5: {
						TaiKhoan.capNhapNhanVien();
						break;
					}
					case 6: {
						TaiKhoan.updateSTT();
						break;
					}
					case 7: {
						NguoiDan.hienThiVNND();
						break;
					}
					case 8: {
						NguoiDan.hienThiNVS();
						break;
					}
					case 9: {
						NguoiDan.hienThiTop3VN();
						break;
					}
					case 10: {
						NguoiDan.hienthiNDTGDB();
						break;
					}
					case 11:
						System.out.println("Kêt thúc chương trình!");
						break;
					}
				}
				if (key == 11) {
					break;
				} else {
					if (key < 0 || key > 11) {
						System.out.println("mời nhập lại!");
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Lựa chọn không hợp lệ. Mời nhập lại bằng số nguyên.");
			}
		} while (true);
	}

	// Đăng nhâp admin (stt=1);
	public static void logNV(Connection connection, String aCount) {
		do {
			System.out.println("<--CHƯƠNG TRÌNH NHÂN VIÊN-->");
			System.out.println("1. Cập nhật vấn nạn");
			System.out.println("2. Cập nhật trạng thái phê duyệt vấn nạn");
			System.out.println("3. Cập nhật thông tin cá nhân");
			System.out.println("4. Đổi mật khẩu");
			System.out.println("5. Đăng xuất");
			System.out.print("Mời nhập lựa chọn: ");
			try {
				int key = Integer.parseInt(scanner.nextLine());

				if ((key > 1 && key < 4) && Validate.checkChuongTrinhND(connection) == 0) {
					System.out.println("Hiện chưa có người dân nào. Xin vui lòng truy cập lại sau.");
				} else {
					switch (key) {
					case 1: {
						ChuongTrinhTinhTrang.ctNhanVien(aCount);
						break;
					}
					case 2: {
						NhapThonngTinVanNan.updateTrangThaiPheDuyet(connection, aCount);
						break;
					}
					case 3: {
						TaiKhoan.updateTTNV0(aCount);
						break;
					}
					case 4: {
						TaiKhoan.doiMK(aCount);
						break;
					}
					case 5:
						System.out.println("Kêt thúc chương trình!");
						break;
					}
				}
				if (key == 5) {
					break;
				} else {
					if (key < 0 || key > 5) {
						System.out.println("mời nhập lại!");
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Lựa chọn không hợp lệ. Mời nhập lại bằng số nguyên.");
			}
		} while (true);
	}

	// Đăng nhâp người dân (stt=0);
	public static void logND(Connection connection, String aCount) {
		do {
			System.out.println("<--CHƯƠNG TRÌNH NGƯỜI DÂN-->");
			System.out.println("1. Chức năng vấn nạn");
			System.out.println("2. Chức năng vote");
			System.out.println("3. Sửa đổi thông tin cá nhân");
			System.out.println("4. Đổi mật khẩu");
			System.out.println("5. Đăng xuất");
			System.out.print("Mời nhập lựa chọn: ");
			try {
				int key = Integer.parseInt(scanner.nextLine());

				if ((key > 1 && key < 5) && Validate.checkChuongTrinhND(connection) == 0) {
					System.out.println("Hiện chưa có người dân nào. Xin vui lòng truy cập lại sau.");
				} else {
					switch (key) {
					case 1: {
						ChuongTrinhVanNan.MenuVanNan1(connection, aCount);
						break;
					}
					case 2: {
						ChuongTrinhVote.chuongtrinhVotee(aCount);
						break;

					}
					case 3: {
						TaiKhoan.updateTTND0(aCount);
						break;
					}
					case 4: {
						TaiKhoan.doiMK(aCount);
						break;
					}
					case 5:
						System.out.println("Kêt thúc chương trình!");
						break;
					}

				}
				if (key == 5) {
					break;
				} else {
					if (key < 0 || key > 5) {
						System.out.println("mời nhập lại!");
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Lựa chọn không hợp lệ. Mời nhập lại bằng số nguyên.");
			}
		} while (true);
	}
}
