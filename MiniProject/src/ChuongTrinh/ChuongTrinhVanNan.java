package ChuongTrinh;


import java.sql.Connection;
import java.util.Scanner;
import Controller.NhapThonngTinVanNan;

public class ChuongTrinhVanNan {
	static Scanner scanner = new Scanner(System.in);

	/// MenuVanNan
	public static void MenuVanNan1(Connection connection, String aCount) {
		boolean check=true;
		do {
			try {
				System.out.println("------------------------------------------------");
				System.out.println("1. Đăng vấn nạn");
				System.out.println("2. Cập nhật thông tin vấn nạn");
				System.out.println("3. Xóa vấn nạn");
				System.out.println("4. Liệt kê số lượng từng loại vấn nạn theo tháng X của năm Y (X và Y nhập từ bàn phím)");
				System.out.println("5. Liệt kê tổng các vấn nạn của mỗi tình trạng xử lý của  thành phố (Thành phố nhập từ bàn phím)");
				System.out.println("6. Hiển thị tổng các vấn nạn theo từng mức độ biểu quyết (sắp xếp giảm dần theo mức độ biểu quyết)");
				System.out.println("7. Thống kê các vấn nạn đã phê duyệt nhưng chưa xử lý theo tháng X (X nhập từ bàn phím)");
				System.out.println("8. Thoát khỏi chương trình");
				System.out.println("Mời bạn chọn chức năng cho vấn nạn:");
				System.out.println("------------------------------------------------");
				int num = Integer.parseInt(scanner.nextLine());		
				switch (num) {
				case 1:
					NhapThonngTinVanNan.dangVanNan(connection, aCount);
					break;
				case 2:
					if (NhapThonngTinVanNan.checkVanNan(connection) == false) {
						System.out.println("chưa có vấn nạn để thực hiện chức năng");
						break;
					} else {
						NhapThonngTinVanNan.capNhatThongTinVanNan(connection, aCount);
						break;
					}

				case 3:
					if (NhapThonngTinVanNan.checkVanNan(connection) == false) {
						System.out.println("chưa có vấn nạn để thực hiện chức năng");
						break;
					} else {
						NhapThonngTinVanNan.xoaVanNan(connection, aCount);
						break;
					}
				case 4:
					if (NhapThonngTinVanNan.checkVanNan(connection) == false) {
						System.out.println("chưa có vấn nạn để thực hiện chức năng");
						break;
					} else {
						NhapThonngTinVanNan.lietKeLoaiVanNan(connection);
						break;
					}
					
				case 5:
					if (NhapThonngTinVanNan.checkVanNan(connection) == false) {
						System.out.println("chưa có vấn nạn để thực hiện chức năng");
						break;
					} else {
						NhapThonngTinVanNan.lietKeVanNanTheoDiaChi(connection);
						break;
					}
					
				case 6:
					if (NhapThonngTinVanNan.checkVanNan(connection) == false) {
						System.out.println("chưa có vấn nạn để thực hiện chức năng");
						break;
					} else {
						NhapThonngTinVanNan.mucDoNghiemTrong(connection);
						break;
					}
					
				case 7:
					if (NhapThonngTinVanNan.checkVanNan(connection) == false) {
						System.out.println("chưa có vấn nạn để thực hiện chức năng");
						break;
					} else {
						NhapThonngTinVanNan.vanNanChuaXuLy(connection);
						break;
					}
					
				case 8:
					check = false;
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + num);
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				System.out.println("Nhập đúng các chức năng bằng số. Mời nhập lại!!!");
			}
		} while (check==true);

	}

}
