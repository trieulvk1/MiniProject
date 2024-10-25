package Controller;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ChuongTrinh.*;
import ConnectionMN.ConnectionSQL;
import Entity.NguoiDan;
import Entity.NhanVien;
import Miniproject_Main.Validate;
import Services.TaiKhoan_Services;

public class TaiKhoan {
	static Scanner scanner = new Scanner(System.in);
	Connection connection = ConnectionSQL.getConnection();

	// chức năng đăng ký
	public static void dangKy() {
		Connection connection = ConnectionSQL.getConnection();
		ArrayList<Entity.TaiKhoan> listUser = new ArrayList<>();
		String aCount;
		do {
			System.out.print("Nhập tài khoản: ");
			aCount = scanner.nextLine();
			if (Validate.checkTKChu(connection, aCount) == false) {
				System.out.println("Tài khoản phải bắt đầu là chữ cái!");
			} else {
				if (Validate.taiKhoan(connection, aCount) == false) {
					System.out.println("Tài khoản phải lớn hơn 6 ký tự! mời nhập lại");
				} else if (Validate.checkAcount(connection, aCount) == false) {
					System.out.println("Thông tin tài khoản đã tồn tại!!");
				} else {
					break;
				}
			}
		} while (true);
		// nhập mật khẩu
		String pass;
		do {
			System.out.print("Nhập mật khẩu: ");
			pass = scanner.nextLine();
			if (Validate.matKhau(connection, pass) == false) {
				System.out.println("Nhập mật khẩu lớn hơn 6 ký tự! Mời nhập lại");
			} else {
				break;
			}
		} while (true);

		// nhập phân quyền
		int sTT;
		do {
			System.out.print("Nhập mã phân quyền (0: Người dân, 1: Nhân viên, 2: Quản lý): ");
			String input = scanner.nextLine();
			try {
				sTT = Integer.parseInt(input);
				if (sTT >= 0 && sTT <= 2) {
					break; // Thoát khỏi vòng lặp nếu giá trị là số nguyên từ 0 đến 2
				} else {
					System.out.println("Mã phân quyền không hợp lệ! Mời nhập lại.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Bạn phải nhập một số nguyên! Mời nhập lại.");
			}
		} while (true);
		if (sTT == 0) {
			// Đăng ký thông tin cá nhân của tài khoản
			Entity.TaiKhoan taiKhoan = new Entity.TaiKhoan(aCount, pass, sTT);
			int result = TaiKhoan_Services.dangKyTK(taiKhoan);
			System.out.println("Mời nhập thông tin cái nhân");
			String Idnd;
			do {
				System.out.print("Nhập ID Người dân (NDxxxx): ");
				Idnd = scanner.nextLine();
				if (Validate.iDNguoiDan(connection, Idnd) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					if (Validate.checkIDNor(connection, Idnd) == false) {
						System.out.println("ID Người dân đã tồn tại!");
					} else {
						break;
					}
				}
			} while (true);

			ArrayList<NguoiDan> listND = new ArrayList<>();
			String tennd;
			do {
				System.out.print("Nhập tên người dân: ");
				tennd = scanner.nextLine();
				if (Validate.checkName(connection, tennd) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					break;
				}
			} while (true);
			System.out.print("Nhập địa chỉ người dân: ");
			String dcnd = scanner.nextLine();
			String soDT;
			do {
				System.out.print("Nhập số điện thoại người dân (09xxxxxxxx): ");
				soDT = scanner.nextLine();
				if (Validate.soDTNguoiDan(soDT) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					break;
				}
			} while (true);
			String taiKhoan1 = aCount;
			NguoiDan nguoiDan = new NguoiDan(Idnd, tennd, dcnd, soDT, taiKhoan1);
			int result1 = TaiKhoan_Services.dangKyThongTinTK(nguoiDan);
			if (result1 != 0) {
				System.out.println("Đăng ký thông tin thành công!!!");
			} else {
				System.out.println("Đăng ký không thành công!!");
			}
		} else if (sTT == 1) {
			Entity.TaiKhoan taiKhoan = new Entity.TaiKhoan(aCount, pass, sTT);
			int result = TaiKhoan_Services.dangKyTK(taiKhoan);
			System.out.println("Mời nhập thông tin cái nhân");
			String Idnd;
			do {
				System.out.print("Nhập ID nhân viên (NVxxxx): ");
				Idnd = scanner.nextLine();
				if (Validate.iDNhanVien(connection, Idnd) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					if (Validate.checkIDNV(connection, Idnd) == false) {
						System.out.println("ID Người dân đã tồn tại!");
					} else {
						break;
					}
				}
			} while (true);
			ArrayList<NhanVien> listNV = new ArrayList<>();
			String tennv;
			do {
				System.out.print("Nhập tên nhân viên: ");
				tennv = scanner.nextLine();
				if (Validate.checkName(connection, tennv) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					break;
				}
			} while (true);
			System.out.print("Nhập thông tin liên lạc: ");
			String ttll = scanner.nextLine();
			int namCT;
			do {
				System.out.print("Nhập số năm công tác: ");
				String input = scanner.nextLine();
				try {
					namCT = Integer.parseInt(input);
					if (namCT >= 0 && namCT <= 100) {
						break; // Thoát khỏi vòng lặp nếu giá trị là số nguyên từ 0 đến 2
					} else {
						System.out.println("Năm công tác không hợp lệ.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Bạn phải nhập một số nguyên! Mời nhập lại.");
				}
			} while (true);
			String taiKhoan1 = aCount;
			NhanVien nhanVien = new NhanVien(Idnd, tennv, ttll, namCT, taiKhoan1);
			int result1 = TaiKhoan_Services.dangKyThongTinTKNV(nhanVien);
			if (result1 != 0) {
				System.out.println("Đăng ký thông tin thành công!!!");
			} else {
				System.out.println("Đăng ký không thành công!!");
			}
		} else if (sTT == 2) {
			Entity.TaiKhoan taiKhoan = new Entity.TaiKhoan(aCount, pass, sTT);
			int result = TaiKhoan_Services.dangKyTK(taiKhoan);
			if (result != 0) {
				System.out.println("Đăng ký thành công!!!");
			}
		}

	}

	// đăng nhập User
	public static void dangNhap() {
		Connection connection = ConnectionSQL.getConnection();
		String taiKhoan;
		do {
			System.out.print("Nhập tài khoản: ");
			taiKhoan = scanner.nextLine();
			if (Validate.checkTKChu(connection, taiKhoan) == false) {
				System.out.println("Tài khoản phải bắt đầu là chữ cái!");
			} else {
				if (Validate.taiKhoan(connection, taiKhoan) == false) {
					System.out.println("Tài khoản phải lớn hơn 6 ký tự! mời nhập lại");
				} else {
					break;
				}
			}
		} while (true);
		String pass;
		do {
			System.out.print("Nhập mật khẩu: ");
			pass = scanner.nextLine();
			if (Validate.matKhau(connection, pass) == false) {
				System.out.println("Nhập mật khẩu lớn hơn 6 ký tự! Mời nhập lại");
			} else {
				break;
			}
		} while (true);
		if (TaiKhoan_Services.dangNhapTaiKhoan(connection, taiKhoan, pass) == false) {
			System.out.println("Thông tin tài khoản hoặc mật khẩu không chính xác!!");

		} else {
			System.out.println("Đăng nhập thành công!!");
			// check tài khoản đã đăng ký trong bảng người dân chưa. nếu chưa thì sẽ thêm
			// thông tin người dân.
			if (Validate.checkQTC(connection, taiKhoan) == 0
					&& TaiKhoan_Services.dangNhapTaiKhoan(connection, taiKhoan, pass) == true
					&& Validate.checkNguoiDan(connection, taiKhoan) == true) {
				String Idnd;
				System.out.println("Tài khoản của bạn hiện không có thông tin. Vui lòng nhập thông tin");
				do {
					System.out.print("Nhập ID Người dân (NDxxxx): ");
					Idnd = scanner.nextLine();
					if (Validate.iDNguoiDan(connection, Idnd) == false) {
						System.out.println("Nhập không đúng định dạng! Mời nhập lại");
					} else {
						if (Validate.checkIDNor(connection, Idnd) == false) {
							System.out.println("ID người dân đã tồn tại!");
						} else {
							break;
						}
					}
				} while (true);
				ArrayList<NguoiDan> listND = new ArrayList<>();
				String tennd;
				do {
					System.out.print("Nhập tên người dân: ");
					tennd = scanner.nextLine();
					if (Validate.checkName(connection, tennd) == false) {
						System.out.println("Nhập không đúng định dạng! Mời nhập lại");
					} else {
						break;
					}
				} while (true);
				System.out.print("Nhập địa chỉ người dân: ");
				String dcnd = scanner.nextLine();

				String soDT;
				do {
					System.out.print("Nhập số điện thoại người dân (09xxxxxxxx): ");
					soDT = scanner.nextLine();
					if (Validate.soDTNguoiDan(soDT) == false) {
						System.out.print("Nhập không đúng định dạng! Mời nhập lại");
					} else {
						break;
					}
				} while (true);
				String aCount = taiKhoan;
				NguoiDan nguoiDan = new NguoiDan(Idnd, tennd, dcnd, soDT, aCount);
				int result1 = TaiKhoan_Services.dangKyThongTinTK(nguoiDan);
				if (result1 != 0) {
					System.out.println("Đăng ký thông tin thành công!!!");
				} else {
					System.out.println("Đăng ký không thành công!!");
				}
				;
				ChuongTrinhDangNhap.logND(connection, taiKhoan);
			} else if (Validate.checkQTC(connection, taiKhoan) == 1
					&& TaiKhoan_Services.dangNhapTaiKhoan(connection, taiKhoan, pass) == true
					&& Validate.checkNhanVien(connection, taiKhoan) == true) {
				System.out.println("Tài khoản của bạn hiện không có thông tin. Vui lòng nhập thông tin");
				String Idnd;
				do {
					System.out.print("Nhập ID nhân viên (NVxxxx): ");
					Idnd = scanner.nextLine();
					if (Validate.iDNhanVien(connection, Idnd) == false) {
						System.out.println("Nhập không đúng định dạng! Mời nhập lại");
					} else {
						if (Validate.checkIDNV(connection, Idnd) == false) {
							System.out.println("ID Người dân đã tồn tại!");
						} else {
							break;
						}
					}
				} while (true);
				ArrayList<NhanVien> listNV = new ArrayList<>();
				String tennv;
				do {
					System.out.print("Nhập tên nhân viên: ");
					tennv = scanner.nextLine();
					if (Validate.checkName(connection, tennv) == false) {
						System.out.println("Nhập không đúng định dạng! Mời nhập lại");
					} else {
						break;
					}
				} while (true);
				System.out.print("Nhập thông tin liên lạc: ");
				String ttll = scanner.nextLine();
				int namCT;
				do {
					System.out.print("Nhập số năm công tác: ");
					String input = scanner.nextLine();
					try {
						namCT = Integer.parseInt(input);
						if (namCT >= 0 && namCT <= 100) {
							break; // Thoát khỏi vòng lặp nếu giá trị là số nguyên từ 0 đến 2
						} else {
							System.out.println("Năm công tác không hợp lệ.");
						}
					} catch (NumberFormatException e) {
						System.out.println("Bạn phải nhập một số nguyên! Mời nhập lại.");
					}
				} while (true);
				String taiKhoan1 = taiKhoan;
				NhanVien nhanVien = new NhanVien(Idnd, tennv, ttll, namCT, taiKhoan1);
				int result1 = TaiKhoan_Services.dangKyThongTinTKNV(nhanVien);
				if (result1 != 0) {
					System.out.println("Đăng ký thông tin thành công!!!");
				} else {
					System.out.println("Đăng ký không thành công!!");
				}
				ChuongTrinhDangNhap.logNV(connection, taiKhoan);
			}

			// Đăng nhập với vai trò ADMIN
			else if (Validate.checkQTC(connection, taiKhoan) == 2) {
				ChuongTrinhDangNhap.logAdmin(connection);
				// Đăng nhập với vai trò người dân
			} else if (Validate.checkQTC(connection, taiKhoan) == 1) {
				ChuongTrinhDangNhap.logNV(connection, taiKhoan);

				// Đăng nhập với vai trò nhân viên
			} else if (Validate.checkQTC(connection, taiKhoan) == 0) {
				ChuongTrinhDangNhap.logND(connection, taiKhoan);
			}
		}
	}

	// hiển xóa tài khoản
	public static void xoaTaiKhoan() {
		Connection connection = ConnectionSQL.getConnection();
		System.out.print("Nhập ID người dân muốn xóa (NDxxxx): ");
		String idNguoiDan = scanner.nextLine();
		if (Validate.checkIDNor(connection, idNguoiDan) == true) {
			System.out.println("ID người dân không tồn tại!");
		} else {
			if (TaiKhoan_Services.xoaTaiKhoan(connection, idNguoiDan) != 0) {
				System.out.println("Xóa tài khoản thành công!!");
			}
		}
	}
	
	// hiển xóa tài khoản nhân viên
		public static void xoaTaiKhoanNV() {
			Connection connection = ConnectionSQL.getConnection();
			System.out.print("Nhập ID nhân viên muốn xóa (NVxxxx): ");
			String idNguoiDan = scanner.nextLine();
			if (Validate.checkIDNV(connection, idNguoiDan) == true) {
				System.out.println("ID nhân viên không tồn tại!");
			} else {
				if (TaiKhoan_Services.xoaTaiKhoanNV(connection, idNguoiDan) != 0) {
					System.out.println("Xóa tài khoản thành công!!");
				}
			}
		}

	// Cập nhật thông tin tài khoản người dân
	// cập nhật dữ liệu
	public static void capNhapNguoiDan() {
		Connection connection = ConnectionSQL.getConnection();
		String idND;
		do {
			System.out.print("Nhập ID người dân cần cập nhật (NDxxxx): ");
			idND = scanner.nextLine();
			if (Validate.checkIDND(connection, idND) == true) {
				System.out.println("ID người dân không tồn tại!!");
			} else {
				break;
			}
		} while (true);
		String tennd;
		do {
			System.out.print("Nhập tên người dân: ");
			tennd = scanner.nextLine();
			if (Validate.checkName(connection, tennd) == false) {
				System.out.println("Nhập không đúng định dạng! Mời nhập lại");
			} else {
				break;
			}
		} while (true);
		System.out.print("Nhập địa chỉ người dân cần thay đổi: ");
		String diaChi = scanner.nextLine();
		String soDT;
		do {
			System.out.print("Nhập số điện thoại cần thay đổi: ");
			soDT = scanner.nextLine();
			if (Validate.soDTNguoiDan(soDT) == false) {
				System.out.print("Nhập không đúng định dạng! Mời nhập lại");
			} else {
				break;
			}
		} while (true);
		if (TaiKhoan_Services.capNhatND(connection, tennd, diaChi, soDT, idND) != 0) {
			System.out.println("Cập nhật thành công!!");
		} else {
			System.out.println("Cập nhật không thành công!!");
		}
	}

	// cập nhật thông tin nhân viên
	public static void capNhapNhanVien() {
		Connection connection = ConnectionSQL.getConnection();
		String idNV;
		do {
			System.out.print("Nhập ID nhân viên cần cập nhật (NVxxxx): ");
			idNV = scanner.nextLine();
			if (Validate.checkIDNV(connection, idNV) == true) {
				System.out.println("ID nhân viên không tồn tại!!");
			} else {
				break;
			}
		} while (true);
		String tennv;
		do {
			System.out.print("Nhập tên nhân viên: ");
			tennv = scanner.nextLine();
			if (Validate.checkName(connection, tennv) == false) {
				System.out.println("Nhập không đúng định dạng! Mời nhập lại");
			} else {
				break;
			}
		} while (true);
		System.out.print("Nhập thông tin liên lạc: ");
		String ttll = scanner.nextLine();
		int namCT;
		do {
			System.out.print("Nhập số năm công tác: ");
			String input = scanner.nextLine();
			try {
				namCT = Integer.parseInt(input);
				if (namCT >= 0 && namCT <= 100) {
					break; // Thoát khỏi vòng lặp nếu giá trị là số nguyên từ 0 đến 2
				} else {
					System.out.println("Năm công tác không hợp lệ.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Bạn phải nhập một số nguyên! Mời nhập lại.");
			}
		} while (true);
		if (TaiKhoan_Services.capNhatNV(connection, tennv, ttll, namCT, idNV) != 0) {
			System.out.println("Cập nhật thành công!!");
		} else {
			System.out.println("Cập nhật không thành công!!");
		}
	}

	// kiểu tra chương trình
	public static int checkTT() {
		Connection connection = ConnectionSQL.getConnection();
		int count = Validate.checkChuongTrinh(connection);
		return count;
	}

	// đổi mật khẩu từ tài khoản
	public static void doiMK(String taiKhoan) {
		Connection connection = ConnectionSQL.getConnection();
		System.out.print("Nhập lại mật khẩu: ");
		String pass = scanner.nextLine();
		if (Validate.checkMatKhau(taiKhoan, pass) == false) {
			System.out.println("Mật khẩu nhập lại không đúng!!");
		} else {
			String pass1;
			do {
				System.out.print("Nhập mật khẩu mới: ");
				pass1 = scanner.nextLine();
				if (Validate.matKhau(connection, pass1) == false) {
					System.out.println("Nhập mật khẩu lớn hơn 6 ký tự! Mời nhập lại");
				} else {
					break;
				}
			} while (true);
			if (TaiKhoan_Services.doiMK(taiKhoan, pass1) != 0) {
				System.out.println("Cập nhật mật khẩu thành công!!");
			} else {
				System.out.println("Cập nhật mật khẩu không thành công!!");
			}

		}
	}

	// đổi thông tin từ người dân
	public static void updateTTND0(String taiKhoan) {
		Connection connection = ConnectionSQL.getConnection();
		String tennd;
		do {
			System.out.print("Nhập tên người dân muốn thay đổi: ");
			tennd = scanner.nextLine();
			if (Validate.checkName(connection, tennd) == false) {
				System.out.println("Tên người dân sai định dạng!!");
			} else {
				break;
			}
		} while (true);
		System.out.print("Nhập địa chỉ muốn thay đổi: ");
		String diaChi = scanner.nextLine();
		String soDT;
		do {
			System.out.print("Nhập số điện thoại muốn thay đổi (09xxxxxxxx): ");
			soDT = scanner.nextLine();
			if (Validate.soDTNguoiDan(soDT) == false) {
				System.out.println("Nhập không đúng định dạng! Mời lnhập lại");
			} else {
				break;
			}
		} while (true);
		if (TaiKhoan_Services.capNhatND0(connection, tennd, diaChi, soDT, taiKhoan) != 0) {
			System.out.println("Cập nhật thành công!!");
		} else {
			System.out.println("Cập nhật không thành công!!");
		}
	}
	
	// đổi thông tin từ nhân viên
		public static void updateTTNV0(String taiKhoan) {
			Connection connection = ConnectionSQL.getConnection();
			String tennv;
			do {
				System.out.print("Nhập tên nhân viên: ");
				tennv = scanner.nextLine();
				if (Validate.checkName(connection, tennv) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					break;
				}
			} while (true);
			System.out.print("Nhập thông tin liên lạc: ");
			String ttll = scanner.nextLine();
			int namCT;
			do {
				System.out.print("Nhập số năm công tác: ");
				String input = scanner.nextLine();
				try {
					namCT = Integer.parseInt(input);
					if (namCT >= 0 && namCT <= 100) {
						break; // Thoát khỏi vòng lặp nếu giá trị là số nguyên từ 0 đến 2
					} else {
						System.out.println("Năm công tác không hợp lệ.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Bạn phải nhập một số nguyên! Mời nhập lại.");
				}
			} while (true);
			if (TaiKhoan_Services.capNhatNV0(connection, tennv, ttll, namCT, taiKhoan) != 0) {
				System.out.println("Cập nhật thành công!!");
			} else {
				System.out.println("Cập nhật không thành công!!");
			}
		}



	// thay đổi quyền truy cập tài khoản
	public static void updateSTT() {
		Connection connection = ConnectionSQL.getConnection();
		String aCount;
		do {
			System.out.print("Nhập tài khoản cần thay đổi quyền: ");
			aCount = scanner.nextLine();
			if (Validate.checkAcount(connection, aCount) == true) {
				System.out.println("Tài khoản không tồn tại!!");
			} else {
				break;
			}
		} while (true);
		int sTT;
		do {
			System.out.print("Nhập mã phân quyền (0:Người dân, 1:Nhân viên sở): ");
			String input = scanner.nextLine();
			try {
				sTT = Integer.parseInt(input);
				if (sTT >= 0 && sTT <= 1) {
					break; // Thoát khỏi vòng lặp nếu giá trị là số nguyên từ 0 đến 2
				} else {
					System.out.println("Mã phân quyền không hợp lệ! Mời nhập lại.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Bạn phải nhập một số nguyên! Mời nhập lại.");
			}
		} while (true);
		int result = TaiKhoan_Services.doiSTT(aCount, sTT);
		if (result != 0) {
			System.out.println("Cập nhật mã phân quyền thành công!!");
		}
		if (sTT == 1 && Validate.checkaCountNV(aCount) == true) {
			String Idnd;
			do {
				System.out.print("Nhập ID nhân viên (NVxxxx): ");
				Idnd = scanner.nextLine();
				if (Validate.iDNhanVien(connection, Idnd) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					if (Validate.checkIDNV(connection, Idnd) == false) {
						System.out.println("ID nhân viên đã tồn tại!");
					} else {
						break;
					}
				}
			} while (true);
			if (TaiKhoan_Services.updateND_NV(aCount, Idnd) != 0) {
				System.out.println("Cập nhật thành công!!");
			}

			;

		} else if (sTT == 0 && Validate.checkaCountND(aCount) == true) {
			String Idnd;
			do {
				System.out.print("Nhập ID Người dân (NDxxxx): ");
				Idnd = scanner.nextLine();
				if (Validate.iDNguoiDan(connection, Idnd) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					if (Validate.checkIDNor(connection, Idnd) == false) {
						System.out.println("ID người dân đã tồn tại!");
					} else {
						break;
					}
				}
			} while (true);
			String soDT;
			do {
				System.out.print("Nhập số điện thoại người dân (09xxxxxxxx): ");
				soDT = scanner.nextLine();
				if (Validate.soDTNguoiDan(soDT) == false) {
					System.out.println("Nhập không đúng định dạng! Mời nhập lại");
				} else {
					break;
				}
			} while (true);
			if (TaiKhoan_Services.updateNV_ND(aCount, Idnd, soDT) != 0) {
				System.out.println("Cập nhật thành công!!");
			}
		} else {
			System.out.println("Không thể update thông tin vì tài khoản đã tồn tại!!");
		}
	}
}
