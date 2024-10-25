package ChuongTrinh;

import java.sql.Connection;
import java.util.Scanner;
import Entity.*;
import Services.TrangThaiXuLy_Services;
import Validate_TTXL.*;
import Controller.*;
import ConnectionMN.ConnectionSQL;

public class ChuongTrinhTinhTrang {
	static Scanner sc = new Scanner(System.in);

	public static void ctNhanVien(String aCount) {
		Connection connection = ConnectionSQL.getConnection();
		
		do {
			System.out.println("Xin mời chọn chức năng");
			System.out.println("1. Thêm vấn nạn");
			System.out.println("2. Cập nhật tình trạng xử lý vấn nạn");
			System.out.println("3. Xóa vấn nạn");
			System.out.println("4. Liệt kê TOP 5 vấn nạn được xử lý nhanh nhất");
			System.out.println("5. Phân loại vấn nạn theo tình trạng xử lý");
			System.out.println("6. Thống kê những vấn nạn pending hơn 1 tháng " + "và thời gian pending");
			System.out.println("7. Liệt kê TOP 3 nhân viên" + " xử lý được nhiều vấn nạn nhất trong quý X năm Y");
			System.out.println("8. Thoát khỏi chương trình");

			int num;
			while (true) {
				String input = sc.nextLine();
				try {
					num = Integer.parseInt(input);
					break;
				} catch (NumberFormatException e) {
					// TODO: handle exception
					System.out.println("Vui lòng nhập vào 1 số nguyên");
				}
			}
			if ((num > 1 && num < 8) && Validate.validateData() == 0) {
				System.out.println("Hiện tại chưa có vấn nạn nào được nhập. Xin "
						+ "bạn hãy dùng chức năng này sau khi nhập vấn nạn");
			} else {
				switch (num) {
				case 1: {
					TinhTrangXuLy.inSert(aCount);
					break;
				}
				case 2: {
					TinhTrangXuLy.upDate();
					break;
				}
				case 3: {
					TinhTrangXuLy.deLeTe();
					break;
				}
				case 4: {
					TrangThaiXuLy_Services.xuLyNhanhNhat();
					break;
				}
				case 5: {
					TrangThaiXuLy_Services.phanLoaiVanNan();
					break;
				}
				case 6: {
					TrangThaiXuLy_Services.hienThiTonDong();
					break;
				}
				case 7: {
					TinhTrangXuLy.nhanVienXS();
					break;
				}

				}
				if (num == 8) {
					System.out.println("Tạm biệt!");
					break;
				} else if (num < 1 || num > 8) {
					System.out.println("Không có chức năng nào. Mời nhập lại!");
				}
			}
		} while (true);
	}
}
