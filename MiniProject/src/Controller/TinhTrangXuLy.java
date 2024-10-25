package Controller;

import java.security.Provider.Service;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ConnectionMN.ConnectionSQL;
import Validate_TTXL.*;
import Validate_TTXL.Validate;
import Miniproject_Main.*;
import Services.TrangThaiXuLy_Services;
import Entity.*;

public class TinhTrangXuLy {
	static Scanner sc = new Scanner(System.in);

	public static void inSert(String aCount) {
		Connection connection = ConnectionSQL.getConnection();
		String idVanNan = "";
		String idNhanVien = TrangThaiXuLy_Services.selectIDNV(connection, aCount);
		String tinhTrang = "";
		Date ngayBatDau = null;
		Date ngayKetThuc = null;
		List<ThongTinVanNan> list = TrangThaiXuLy_Services.layDuLieuVanNan();
		System.out.println("Những vấn nạn trong sở là: ");
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Record " + (i + 1) + list.get(i));
		}
		do {
			System.out.println("Nhập vào mã vấn nạn (Issuexxxxx, x là số)");
			idVanNan = sc.nextLine();
			String ddMa = "Issue\\d{5}";
			Pattern r = Pattern.compile(ddMa);
			Matcher m = r.matcher(idVanNan);
			if (!m.matches()) {
				System.out.println("Nhập không đúng định dạng, mời nhập lại!");
				continue;
			}
			// ... (kiểm tra mã vấn nạn)
			if (!Validate.checkIdVanNan(idVanNan)) {
				System.out.println("Mã vấn nạn không tồn tại, mời nhập lại");
				continue;
			}
			if (Validate.checkIdVanNan1(idVanNan)) {
				System.out.println("Mã vấn nạn đã tồn tại trong bảng, mời nhập lại");
				continue;
			}
			if (!Validate.checkTrangThai(idVanNan)) {
				System.out.println("Vấn nạn chưa được phê duyệt, mời nhập lại");
			} else {
				break;
			}
		} while (true);

		do {
			System.out.println("Nhập vào tình trạng xử lý mới (Nhập số từ 1-5)");
			System.out.println("1. Chưa theo dõi");
			System.out.println("2. Đã xem xét");
			System.out.println("3. Đang xử lý");
			System.out.println("4. Đã xử lý");
			System.out.println("5. Hủy vấn nạn");

			int choice = 0;
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Vui lòng nhập một số từ 1 đến 5");
				continue;
			}

			switch (choice) {
			case 1:
				tinhTrang = "Chưa theo dõi";
				break;
			case 2:
				tinhTrang = "Đã xem xét";
				break;
			case 3:
				tinhTrang = "Đang xử lý";
				break;
			case 4:
				tinhTrang = "Đã xử lý";
				break;
			case 5:
				tinhTrang = "Hủy vấn nạn";
				break;
			default:
				System.out.println("Vui lòng chọn từ 1 đến 5");
				continue;
			}

		} while (tinhTrang == null);
		if (!("Chưa theo dõi".equals(tinhTrang) || "Đã xem xét".equals(tinhTrang))) {
			do {
				System.out.println("Nhập ngày bắt đầu tiếp nhận vấn nạn (dd/MM/yyyy)");
				String ngayBatDauStr = sc.nextLine();
				if (Validate.formatDate(ngayBatDauStr)) {
					try {
						ngayBatDau = new SimpleDateFormat("dd/MM/yyyy").parse(ngayBatDauStr);
						break;
					} catch (ParseException e) {
						e.printStackTrace();
						System.out.println("Lỗi khi ép kiểu!");
					}

				} else {
					System.out.println("Nhập ngày không đúng định dạng");
				}
			} while (true);
		}

		if (!("Đang xử lý".equals(tinhTrang) || "Chưa theo dõi".equals(tinhTrang) || "Đã xem xét".equals(tinhTrang))) {
			// Nếu tình trạng không phải là "Đang xử lý", yêu cầu nhập ngày kết thúc
			do {
				System.out.println("Nhập ngày kết thúc xử lý (dd/MM/yyyy)");
				String ngayKetThucStr = sc.nextLine();

				if (!Validate.formatDate(ngayKetThucStr)) {
					System.out.println("Nhập ngày không đúng định đạng");
					continue;
				} else if (!Validate.validateDates(ngayBatDau, ngayKetThucStr)) {
					System.out.println("Ngày bắt đầu phải trước ngày kết thúc.");
				} else {
					try {
						ngayKetThuc = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKetThucStr);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					break;
				}
			
			} while (true);
		}

		TinhTrangXyLy obo1 = new TinhTrangXyLy(idVanNan, idNhanVien, ngayBatDau, ngayKetThuc, tinhTrang);

		int result = TrangThaiXuLy_Services.insert(obo1);
		if (result != 0) {
			System.out.println("Insert dữ liệu thành công");
		} else {
			System.out.println("Insert dữ liệu thất bại");
		}
	}

	public static void upDate() {
		int result = 0;
		List<TinhTrangXuLy> list = TrangThaiXuLy_Services.layDuLieuTTXL();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Record " + (i + 1) + list.get(i));
		}
		String idVanNan = "";
		do {
			System.out.println("Nhập vấn nạn muốn thay đổi tình trạng (Issuexxxxx, x là số)");
			idVanNan = sc.nextLine();
			String ddMa = "Issue\\d{5}";
			Pattern r = Pattern.compile(ddMa);
			Matcher m = r.matcher(idVanNan);
			if (!m.matches()) {
				System.out.println("Nhập không đúng định dạng, mời nhập lại!");
				continue;
			}

			if (Validate.checkIdVanNan1(idVanNan) == false) {
				System.out.println("Mã vấn nạn không tồn tại. Mời nhập lại!");
			} else {
				break;
			}
		} while (true);

		String newTinhTrang = "";
		do {
			System.out.println("Nhập vào tình trạng xử lý mới (Nhập số từ 1-5)");
			System.out.println("1. Chưa theo dõi");
			System.out.println("2. Đã xem xét");
			System.out.println("3. Đang xử lý");
			System.out.println("4. Đã xử lý");
			System.out.println("5. Hủy vấn nạn");

			int choice = 0;
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Vui lòng nhập một số từ 1 đến 5");
				continue;
			}

			switch (choice) {
			case 1:
				newTinhTrang = "Chưa theo dõi";
				break;
			case 2:
				newTinhTrang = "Đã xem xét";
				break;
			case 3:
				newTinhTrang = "Đang xử lý";
				break;
			case 4:
				newTinhTrang = "Đã xử lý";
				break;
			case 5:
				newTinhTrang = "Hủy vấn nạn";
				break;
			default:
				System.out.println("Vui lòng chọn từ 1 đến 5");
				continue;
			}

			if (Validate.checkTTVN(idVanNan, newTinhTrang)) {
				break;
			} else {
				System.out.println("Tình trạng mới bị trùng tình trạng cũ. Mời nhập lại!");
			}
		} while (true);

		Date ngayBatDau = TrangThaiXuLy_Services.getDay(idVanNan);
//      System.out.println(""+ ngayBatDau);
		Date ngayKetThuc = TrangThaiXuLy_Services.getDay1(idVanNan);
//      System.out.println(""+ ngayKetThuc);

		if ("Đang xử lý".equals(newTinhTrang)) {
			System.out.println("Nhập ngày bắt đầu xử lý");
			String ngayBatDauStr = sc.nextLine();
			try {
				ngayBatDau = new SimpleDateFormat("dd/MM/yyyy").parse(ngayBatDauStr);
			} catch (ParseException e) {
				// TODO: handle exception
			}
		}

		if (("Đã xử lý".equals(newTinhTrang) || "Hủy vấn nạn".equals(newTinhTrang)) && ngayKetThuc == null) {
			do {
				System.out.println("Nhập ngày kết thúc xử lý (dd/MM/yyyy)");
				String ngayKetThucStr = sc.nextLine();

				if (!Validate.formatDate(ngayKetThucStr)) {
					System.out.println("Nhập ngày không đúng định đạng");
					continue;
				} else if (!Validate.validateDates(ngayBatDau, ngayKetThucStr)) {
					System.out.println("Ngày bắt đầu phải trước ngày kết thúc.");
				} else {
					try {
						ngayKetThuc = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKetThucStr);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					break;
				}
			
			} while (true);

		}

		result = TrangThaiXuLy_Services.update(idVanNan, newTinhTrang, ngayBatDau, ngayKetThuc);
		if (result != 0) {
			System.out.println("Đã cập nhật thành công!");
		} else {
			System.out.println("Cập nhật thất bại!");
		}
	}

	public static void deLeTe() {
		List<TinhTrangXuLy> list = TrangThaiXuLy_Services.layDuLieuTTXL();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Record " + (i + 1) + list.get(i));
		}
		String idVanNan = "";
		do {
			System.out.println("Nhập vấn nạn đã được giải quyết (Issuexxxxx, x là số)");
			idVanNan = sc.nextLine();
			String ddMa = "Issue\\d{5}";
			Pattern r = Pattern.compile(ddMa);
			Matcher m = r.matcher(idVanNan);
			if (!m.matches()) {
				System.out.println("Nhập không đúng định dạng, mời nhập lại!");
			}
			if (Validate.checkIdVanNan1(idVanNan) == false) {
				System.out.println("Mã vấn nạn không tồn tại. Mời nhập lại!");
			} else {
				break;
			}
		} while (true);

		int result = TrangThaiXuLy_Services.delete(idVanNan);
		if (result != 0) {
			System.out.println("Đã xóa thành công vấn nạn!");
		} else {
			System.out.println("Không có vấn nạn nào được xóa!");
		}

	}

	public static void nhanVienXS() {
		int nam, quy;

		do {
			System.out.println("Nhập năm muốn kiểm tra (xxxx khoảng 1980-2023)");
			nam = Integer.parseInt(sc.nextLine());

			if (!Validate.kiemTraNamTrongKhoang(nam)) {
				System.out.println("Vui lòng nhập trong khoảng 1980-2023");
				continue;
			}
			if (Validate.checkNamXuLy(nam)) {
				do {
					System.out.println("Nhập vào quý muốn kiểm tra");
					quy = Integer.parseInt(sc.nextLine());

					if (!Validate.kiemTraQuyTrongKhoang(quy)) {

						System.out.println("Vui lòng nhập khoảng từ 1-4");
						continue;
					}

					if (Validate.checkQuyXuLy(quy)) {
						TrangThaiXuLy_Services.nhanVienXuatSac(nam, quy);
						break;
					} else {
						System.out.println("Quý này không có vấn nạn được xử lý");
						break;
					}

				} while (true);
				break;
			} else {
				System.out.println("Năm vừa nhập không có vấn nạn được xử lý!");
				break;
			}
		} while (true);
	}
//	public static int validate() {
//		int count = Validate.validateData();
//		return count;
//	}
}
