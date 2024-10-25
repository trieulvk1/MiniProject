package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Entity.ThongTinVanNan;
import VanNan.ChucNangVanNan;

public class NhapThonngTinVanNan {
	static Scanner scanner = new Scanner(System.in);

	/// đăng tải thông tin vấn nạn
	public static void dangVanNan(Connection connection, String aCount) {
		/// Nhập id vấn nạn
		String idVanNan;
		do {
			System.out.println("Nhập mã ID vấn nạn (IssueXXXXX X là số)");
			idVanNan = scanner.nextLine();
			String check = "Issue[0-9]{5}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(idVanNan);
			ThongTinVanNan checkValidate = ChucNangVanNan.selectDataValidate(connection, idVanNan);
			if (!matcher.matches()) {
				System.out.println("Nhập sai định dạng. Mời nhập lại!!!");
			} else if (checkValidate != null) {
				System.out.println("ID vấn nạn của bạn đã bị trùng với 1 ID trong hệ thống. Mời nhập lại");
			} else {
				break;
			}
		} while (true);

		/// Nhập tên vấn nạn
		String tenVanNan;
		do {
			System.out.println("Nhập tên vấn nạn ");
			tenVanNan = scanner.nextLine();
			String check = ".{1,}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(tenVanNan);
			if (!matcher.matches()) {
				System.out.println("Không được để trống thông tin. Mời nhập lại!!!");
			} else {
				break;
			}
		} while (true);

		/// Nhập địa chỉ
		String diaChi;
		do {
			System.out.println("Nhập địa chỉ vấn nạn xảy ra ");
			diaChi = scanner.nextLine();
			String check = ".{1,}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(diaChi);
			if (!matcher.matches()) {
				System.out.println("Không được để trống thông tin. Mời nhập lại!!!");
			} else {
				break;
			}
		} while (true);
		/// Nhập mô tả
		String moTa;
		do {
			System.out.println("Nhập mô tả ");
			moTa = scanner.nextLine();
			String check = ".{1,}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(moTa);
			if (!matcher.matches()) {
				System.out.println("Không được để trống thông tin. Mời nhập lại!!!");
			} else {
				break;
			}
		} while (true);

		/// Nhập loại vấn nạn
		String loaiVanNan;
		do {
			System.out.println("Nhập loại vấn nạn (0: Môi trường, 1: Tài nguyên ) ");
			loaiVanNan = scanner.nextLine();
			String check = "[0-1]{1}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(loaiVanNan);
			if (!matcher.matches()) {
				System.out.println("Nhập sai định dạng. Mời nhập lại!!!");
			} else {
				switch (loaiVanNan) {
				case "0":
					loaiVanNan = "Môi trường";
					break;
				case "1":
					loaiVanNan = "Tài nguyên";
					break;
				}
				break;
			}
		} while (true);

		/// lấy ngày đăng

		Date ngayDang;

		// Lấy ngày hiện tại dưới dạng kiểu LocalDate
		LocalDate ngayHienTai = LocalDate.now();

		// Chuyển đổi LocalDate sang Date
		ngayDang = java.sql.Date.valueOf(ngayHienTai.atStartOfDay().toLocalDate());

		/// Tải file hình ảnh đính kèm
		String hinhAnhDinhKem;
		do {
			System.out.println("Nhập file ảnh đính kèm(.jpd,.jdpg)");
			hinhAnhDinhKem = scanner.nextLine();
			String check = ".*\\.(jpd|jdpg)";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(hinhAnhDinhKem);
			if (!matcher.matches()) {
				System.out.println("Nhập sai định dạng. Mời nhập lại!!!");
			} else {
				break;
			}
		} while (true);

		/// Trạng thái vấn nạn
		String trangThai = "Chưa phê duyệt";

		/// idNguoiDan
		String idNguoiDan = ChucNangVanNan.layIdNguoiDan(connection, aCount);

		ThongTinVanNan thongTinVanNan = new ThongTinVanNan(idVanNan, tenVanNan, diaChi, moTa, loaiVanNan, ngayDang,
				hinhAnhDinhKem, trangThai, idNguoiDan);

//			System.out.println(thongTinVanNan.toString());

		// Gọi lại chức năng đăng kí
		int check = ChucNangVanNan.dangTaiVanNan(connection, thongTinVanNan);
		if (check != 0) {
			System.out.println("Đăng tải thành công");
		} else {
			System.out.println("Thất bại");
		}

	}

	/// Update thông tin vấn nạn
	public static void capNhatThongTinVanNan(Connection connection, String aCount) {

		/// Nhập id vấn nạn cần update
		String idVanNan;
		do {
			System.out.println("Nhập mã ID vấn nạn (IssueXXXXX X là số)");
			idVanNan = scanner.nextLine();
			String check = "Issue[0-9]{5}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(idVanNan);
			/// lấy validate ra kiểm tra
			ThongTinVanNan checkValidate = ChucNangVanNan.selectDataValidate(connection, idVanNan);
			if (!matcher.matches()) {
				System.out.println("Nhập sai định dạng. Mời nhập lại!!!");
			} else if (checkValidate == null) {
				System.out.println("ID vấn nạn bạn nhập không có trên hệ thống. Mời nhập lại!!! ");
			} else if (!checkValidate.getIdNguoiDan().trim()
					.equals(ChucNangVanNan.layIdNguoiDan(connection, aCount).trim())) {
				System.out.println("Bạn vừa nhập ID vấn nạn của người khác. Mời nhập lại ID vấn nạn của bạn!!! ");
			} else {
				break;
			}
		} while (true);

		/// nhập mô tả cần update
		String moTa;
		do {
			System.out.println("Nhập mô tả cần update: ");
			moTa = scanner.nextLine();
			String check = ".{1,}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(moTa);
			if (!matcher.matches()) {
				System.out.println("Không được để trống thông tin. Mời nhập lại!!!");
			} else {
				break;
			}
		} while (true);
		/// Nhập loại vấn nạn cần update
		String loaiVanNan;
		do {
			System.out.println("Nhập loại vấn nạn (0: Môi trường, 1: Tài nguyên ) cần update:");
			loaiVanNan = scanner.nextLine();
			String check = "[0-1]{1}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(loaiVanNan);
			if (!matcher.matches()) {
				System.out.println("Nhập sai định dạng. Mời nhập lại!!!");
			} else {
				switch (loaiVanNan) {
				case "0":
					loaiVanNan = "Môi trường";
					break;
				case "1":
					loaiVanNan = "Tài nguyên";
					break;
				}
				break;
			}
		} while (true);
		ThongTinVanNan updateIssue = new ThongTinVanNan(idVanNan, moTa, loaiVanNan);

		// Gọi lại hàm Update từ chức năng
		int check = ChucNangVanNan.updateIssue(connection, updateIssue);
		if (check != 0) {
			System.out.println("Update thông tin thành công");
		} else {
			System.out.println("Update thông tin thất bại");
		}

	}

	/// xóa thông tin vấn nạn
	public static void xoaVanNan(Connection connection, String aCount) {

		/// Nhập id vấn nạn cần Xóa
		String idVanNan;
		do {
			System.out.println("Nhập mã ID vấn nạn (IssueXXXXX X là số)");
			idVanNan = scanner.nextLine();
			String check = "Issue[0-9]{5}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(idVanNan);
			/// lấy validate ra kiểm tra
			ThongTinVanNan checkValidate = ChucNangVanNan.selectDataValidate(connection, idVanNan);
			if (!matcher.matches()) {
				System.out.println("Nhập sai định dạng. Mời nhập lại!!!");
			} else if (checkValidate == null) {
				System.out.println("ID vấn nạn bạn nhập không có trên hệ thống. Mời nhập lại!!! ");
			} else if (!checkValidate.getIdNguoiDan().trim()
					.equals(ChucNangVanNan.layIdNguoiDan(connection, aCount).trim())) {
				System.out
						.println("Bạn vừa nhập ID vấn nạn của người khác. Mời nhập lại ID vấn nạn cần xóa của bạn!!! ");
			} else {
				break;
			}
		} while (true);

		/// truyền tham số cho chức năng xóa
		int check = ChucNangVanNan.deleteIssue(connection, idVanNan);
		if (check != 0) {
			System.out.println("Xóa thông tin thành công");
		} else {
			System.out.println("Xóa thông tin thất bại");
		}
	}

	/// Liệt kê số lượng từng loại vấn nạn theo tháng X của năm Y (X và Y nhập từ
	/// bàn phím)
	public static void lietKeLoaiVanNan(Connection connection) {
		/// Nhập tháng cần kiểm tra
		int month;
		do {
			try {
				System.out.println("Nhập tháng cần tìm kiếm");
				month = Integer.parseInt(scanner.nextLine());
				if (month < 1 || month > 12) {
					System.out.println("Vui lòng nhập đúng định dạng tháng");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				System.out.println("Vui lòng nhập đúng định dạng. Mời nhập lại!!!");
			}
		} while (true);
		/// Nhập năm cần kiểm tra
		int year;
		do {

			try {
				System.out.println("Nhập năm cần tìm kiếm");
				year = Integer.parseInt(scanner.nextLine());

				// lấy năm hiện tại
				int namHienTai = LocalDate.now().getYear();
				if (year > namHienTai) {
					System.out.println("Vui lòng không nhập năm lớn hơn năm hiện tại. Mời nhập lại!!!");
				} else if (year < 2000) {
					System.out.println("Vui lòng nhập số năm lớn hơn 2000. Mời nhập lại!!!");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				System.out.println("Vui lòng nhập đúng định dạng. Mời nhập lại!!!");
			}
		} while (true);

		/// Truyền tham số cho chức năng
		ArrayList<ThongTinVanNan> arr = ChucNangVanNan.lietKeLoaiVanNan(connection, month, year);

		/// Hiển thị ra màn hình
		if (arr.isEmpty()) {
			System.out.println("Không có vấn đề nào cho tháng năm bạn muốn tìm kiếm");
		} else {
			System.out.println("Số lượng từng loại vấn nạn theo tháng " + month + ", năm " + year);
			for (ThongTinVanNan value : arr) {
				System.out.println("Loại vấn nạn " + value.getLoaiVanNan() + ", Số lượng: " + value.getSoLuong());
			}
		}

	}

	/// Liệt kê tổng các vấn nạn của mỗi tình trạng xử lý của thành phố (Thành phố
	/// nhập từ bàn phím)
	public static void lietKeVanNanTheoDiaChi(Connection connection) {
		/// Nhập địa chỉ muốn liệt kê
		String diaChi;
		do {
			System.out.println("Nhập địa chỉ vấn nạn xảy ra ");
			diaChi = scanner.nextLine();
			String check = ".{1,}";
			Pattern pattern = Pattern.compile(check);
			Matcher matcher = pattern.matcher(diaChi);
			String checkDiaChi1 = ChucNangVanNan.checkDiaChi(connection, diaChi);
			if (!matcher.matches()) {
				System.out.println("Không được để trống thông tin. Mời nhập lại!!!");
			} else if (checkDiaChi1 == null) {
				System.out.println("Không có địa chỉ bạn muốn liệt kê. Nhập lại địa chỉ khác!!!");
			} else {
				break;
			}
		} while (true);

		/// gọi lại hàm chức năng
		ArrayList<ThongTinVanNan> arr = ChucNangVanNan.tongVanNanTheoTinhTrang(connection, diaChi);

		if (arr.isEmpty()) {
			System.out.println("Không có vấn đề nào cho địa chỉ bạn muốn tìm kiếm");
		} else {
			System.out.println("Tổng các vấn nạn của mỗi tình trạng xử lý của thành phố " + diaChi);
			for (ThongTinVanNan value : arr) {
				System.out.println(
						"Tình trạng xử lí: " + value.getLoaiVanNan() + ", " + " Số lượng: " + value.getSoLuong());
			}
		}

	}

	/// Hiển thị tổng các vấn nạn theo từng mức độ biểu quyết (sắp xếp giảm dần theo
	/// mức độ biểu quyết)
	public static void mucDoNghiemTrong(Connection connection) {
		ArrayList<ThongTinVanNan> arr = ChucNangVanNan.tongVanNanTheoMucDo(connection);
		if (arr.isEmpty()) {
			System.out.println("Chưa có vấn nạn nào");
		} else {
			for (ThongTinVanNan value : arr) {
				System.out.println("IDVanNan:  " + value.getIdVanNan() + ", " + " mức độ nghiêm trọng: "
						+ value.getMucDoNghiemTrong());
			}
		}
	}

	/// Thống kê các vấn nạn đã phê duyệt nhưng chưa xử lý theo tháng X (X nhập từ
	/// bàn phím)
	public static void vanNanChuaXuLy(Connection connection) {
		int month;
		do {
			try {
				System.out.println("Nhập tháng cần tìm kiếm các vấn nạn đã phê duyệt nhưng chưa xử lý ");
				month = Integer.parseInt(scanner.nextLine());
				if (month < 1 || month > 12) {
					System.out.println("Vui lòng nhập đúng định dạng tháng");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				System.out.println("Vui lòng nhập đúng định dạng. Mời nhập lại!!!");
			}
		} while (true);
		ArrayList<ThongTinVanNan> arr = ChucNangVanNan.vanNanChuaXuLy(connection, month);

		if (arr.isEmpty()) {
			System.out.println("Chưa có vấn nạn nào đã phê duyệt nhưng chưa xử lý");
		} else {
			for (ThongTinVanNan value : arr) {
				System.out.println(value.toString());
			}
		}
	}

	// kiểm tra validate Vấn nan có hay chưa
	public static boolean checkVanNan(Connection connection) {

		boolean check = ChucNangVanNan.checkVanNan(connection);
		if (check != true) {
			return false;
		}
		return true;

	}
	///kiểm tra xem nhập idVanNan phê duyệt đúng hay chưa
		public static boolean  kiemTraIdVanNan(Connection connection, String idVanNan) {
			String vanNanChuaXuLy = "SELECT idVanNan, tenVanNan, diaChi, moTa, loaiVanNan, ngayDang, hinhAnhDinhKem, trangThai, idNguoiDan "
					+ " FROM VanNan  "
					+ " WHERE trangThai = N'Chưa phê duyệt' and idVanNan = ? ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(vanNanChuaXuLy);
				preparedStatement.setNString(1, idVanNan);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next()) {
					return true;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
		}
		
		///update trạng thái phê duyệt cho vấn nạn
		
			public static int updateTrangThaiVanNan(Connection connection, String idVanNan) {
				int check = 0;
				String updateData = "update VanNan SET trangThai =? WHERE idVanNan =?";
	 
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(updateData);
					preparedStatement.setNString(1, "Đã phê duyệt");
					preparedStatement.setString(2, idVanNan);
	 
					check = preparedStatement.executeUpdate();
				} catch (Exception e) {
					// TODO: handle exception
				}
				return check;
			}
			/// Update trạng thái phê duyệt
			public static void updateTrangThaiPheDuyet(Connection connection, String aCount) {
				/// hiển thị các vấn nạn chưa được phê duyệt
				ArrayList<ThongTinVanNan> arrVanNan = ChucNangVanNan.hienThiVanNanChuaPD(connection);
				if (arrVanNan.isEmpty()) {
					System.out.println("Tất cả vấn nạn đã được phê duyệt");
				} else {
					for (ThongTinVanNan value : arrVanNan) {
						System.out.println(value.toTableString());
						
//						/// Nhập id vấn nạn cần phê duyệt
						String idVanNan;
						do {
							System.out.println("Nhập mã ID vấn nạn (IssueXXXXX X là số) cần phê duyệt");
							idVanNan = scanner.nextLine();
							String check = "Issue[0-9]{5}";
							Pattern pattern = Pattern.compile(check);
							Matcher matcher = pattern.matcher(idVanNan);
							boolean checkIdVanNan = ChucNangVanNan.kiemTraIdVanNan(connection, idVanNan);
							if (!matcher.matches()) {
								System.out.println("Nhập sai định dạng. Mời nhập lại!!!");
							} else if (checkIdVanNan == false) {
								System.out.println("Id vấn nạn đã được phê duyệt hoặc không có trên hệ thống");
							} else {
								break;
							}
						} while (true);
						
						///update trạng thái phê duyệt cho vấn nạn
						int check = ChucNangVanNan.updateTrangThaiVanNan(connection, idVanNan);
						if (check != 0) {
							System.out.println("Update thông tin thành công");
						} else {
							System.out.println("thất bại");
						}
					}
		 
				}
		 
			}
}
