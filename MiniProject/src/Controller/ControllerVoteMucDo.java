package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ConnectionMN.ConnectionSQL;
import Validate_Vote.*;
import Services.*;
import Entity.TinhTrangXyLy;
import Entity.VoteMucDo;

public class ControllerVoteMucDo {
	static Scanner scanner = new Scanner(System.in);

	public static void nhapmucdo(String aCount) {
		Connection connection = ConnectionSQL.getConnection();
		String Idnd = functionVote.selectIDND(connection, aCount);
//		do {
//			System.out.print("Nhập ID người dân (IDxxxx, x là các số 0-9): ");
//			Idnd = scanner.nextLine();
//			if (Validate.ValidateIDNHAP(Idnd) && Validate.VALIDATETONTAIID(Idnd)) {
//				System.out.println("ID người dân hợp lệ!");
//				break;
//			} else {
//				System.out
//						.println("ID người dân không hợp lệ hoặc không tồn tại trong cơ sở dữ liệu. Vui lòng thử lại.");
//			}
//		} while (true);

		String idvannan = "";
		System.out.println("Dang sách vấn nạn có thể thêm vào trong bảng Vote");
		List<TinhTrangXyLy> danhsach = functionVote.layThongTinToanBoTinhTrangXuLy();
		for (int i = 0; i < danhsach.size(); i++) {
			System.out.println("Record " + i + danhsach.get(i));
		}
		System.out.println("--------------------------");
		
		do {
			System.out.print("Nhập ID vấn nạn (Issuexxxxx, x là các số 0-9): ");
			idvannan = scanner.nextLine();
			if (Validate.ValidateIDVANNAN(idvannan)) {
				// Nếu ID vấn nạn hợp lệ, kiểm tra tình trạng
				if (Validate.checkTinhTrang(idvannan)) {
					System.out.println("ID vấn nạn hợp lệ và đã xử lý!");
					break;
				} else {
					System.out.println("ID vấn nạn hợp lệ, nhưng chưa xử lý. Vui lòng thử lại.");
				}
			} else {
				System.out.println("ID vấn nạn không hợp lệ. Vui lòng thử lại.");
			}
		} while (true);

		int diem;
		do {
			System.out.print("Nhập điểm từ 1 đến 5: ");
			String diemInput = scanner.nextLine();

			if (Validate.Validatenumber(diemInput)) {
				diem = Integer.parseInt(diemInput);

				if (diem >= 1 && diem <= 5) {
					System.out.println("Điểm nhập hợp lệ: " + diem);
					break;
				} else {
					System.out.println("Lỗi: Điểm phải nằm trong khoảng từ 1 đến 5.");
				}
			} else {
				System.out.println("Lỗi: Vui lòng chỉ nhập số nguyên từ 1 đến 5.");
			}
		} while (true);

		LocalDate ngayHienTai = LocalDate.now();
		java.sql.Date ngayHienTaiSQL = java.sql.Date.valueOf(ngayHienTai);

		int result = functionVote.insertmucdo(diem, ngayHienTaiSQL, Idnd, idvannan);

		if (result != 0) {
			System.out.println("Đã thêm thành công!");
		} else {
			System.out.println("thêm thất bại");
		}
	}

	// update
	public static void updatevote(String aCount) {
		Connection connection = ConnectionSQL.getConnection();

		System.out.println("Dang sách vấn nạn trong bảng Vote");
		List<VoteMucDo> danhsach = functionVote.layThongTinToanBoVanNan();
		for (int i = 0; i < danhsach.size(); i++) {
			System.out.println("Record " + i + danhsach.get(i));
		}
		System.out.println("--------------------------");

		String idvannantimkiem = "";
		do {
			System.out.print("Nhập ID vấn nạn cần update (Issue, x là các số 0-9 gồm 5 số): ");
			idvannantimkiem = scanner.nextLine();

			if (Validate.ValidateIDVANNAN(idvannantimkiem)) {
				// Kiểm tra xem ID vấn nạn có tồn tại trong bảng Vote hay không
				if (Validate.VALIDATEIDVANNANVOTETONTAIUPDATE(idvannantimkiem)) {
					System.out.println("ID vấn nạn muốn update hợp lệ!");
					break;
				} else {
					System.out.println("ID vấn nạn không tồn tại. Vui lòng thử lại.");
				}
			} else {
				System.out.println("ID vấn nạn không hợp lệ. Vui lòng thử lại.");
			}
		} while (true);

		int diemtimkiem;
		do {
			System.out.print("\"Nhập vào điểm bạn muốn update từ 1--> 5 ");
			String diemInput = scanner.nextLine();

			if (Validate.Validatenumber(diemInput)) {
				diemtimkiem = Integer.parseInt(diemInput);

				if (diemtimkiem >= 1 && diemtimkiem <= 5) {
					System.out.println("Điểm nhập hợp lệ: " + diemtimkiem);
					break;
				} else {
					System.out.println("Lỗi: Điểm phải nằm trong khoảng từ 1 đến 5.");
				}
			} else {
				System.out.println("Lỗi: Vui lòng chỉ nhập số nguyên từ 1 đến 5.");
			}
		} while (true);

		String Idndtimkiem = functionVote.selectIDND(connection, aCount);

		int result = functionVote.updatemoi(Idndtimkiem, idvannantimkiem, diemtimkiem);
		if (result != 0) {
			System.out.println("Đã update thành công!");
		} else {
			System.out.println("Update thất bại");
		}
	}

	// xóa
	public static void xoavote(String aCount) {
		Connection connection = ConnectionSQL.getConnection();

		System.out.println("Dang sách vấn nạn trong bảng Vote");
		List<VoteMucDo> danhsach = functionVote.layThongTinToanBoVanNan();
		for (int i = 0; i < danhsach.size(); i++) {
			System.out.println("Record " + i + danhsach.get(i));
		}
		System.out.println("--------------------------");

		String Idndxoa = functionVote.selectIDND(connection, aCount);

		String idvannantimkiem = "";
		do {
			System.out.print("Nhập ID vấn nạn cần xóa (Issue, x là các số 0-9 gồm 5 số): ");
			idvannantimkiem = scanner.nextLine();
			if (Validate.ValidateIDVANNAN(idvannantimkiem)
					&& Validate.VALIDATEIDVANNANVOTETONTAIXOA(idvannantimkiem, Idndxoa)) {
				System.out.println("ID vấn nạn muốn xóa hợp lệ!");
				break;
			} else {
				System.out.println("ID vấn nạn không hợp lệ hoặc không tồn tại. Vui lòng thử lại.");
			}
		} while (true);

		int result = functionVote.xoadulieu(Idndxoa, idvannantimkiem);

		if (result != 0) {
			System.out.println("Đã xóa thành công!");
		} else {
			System.out.println("xóa thất bại");
		}
	}

	// liet ke top 3 van de có điểm trung bình cao nhất
	public static void lietketop3() {
		System.out.println("Tóp 3 vấn nạn có điểm vote cao nhất");
		ArrayList<String> top3vande = functionVote.tinhdiemtop3();
		for (String at : top3vande) {
			System.out.println(at);
		}
	}

	// thống kê số lượng vote theo tháng
	public static void thongkevote() {

		System.out.println("Dang sách vấn nạn trong bảng Vote");
		List<VoteMucDo> danhsach = functionVote.layThongTinToanBoVanNan();
		for (int i = 0; i < danhsach.size(); i++) {
			System.out.println("Record " + i + danhsach.get(i));
		}
		System.out.println("--------------------------");

		boolean isValidInput = false;
		int month = 0;

		while (!isValidInput) {
			System.out.println("Nhập tháng mà bạn muốn thống kê vote:");
			String input = scanner.nextLine();

			if (input.matches("\\d+")) {
				month = Integer.parseInt(input);

				if (month >= 1 && month <= 12) {
					isValidInput = true;
				} else {
					System.out.println("Tháng không hợp lệ. Vui lòng nhập lại.");
				}
			} else {
				System.out.println("Không phải là số nguyên. Vui lòng nhập lại.");
			}
		}

		int demVote = functionVote.demVoteTheoThang(month);
		System.out.println("Số lượng vote trong tháng" + " " + month + " là " + demVote);
	}

	// liệt kê top 3 user có nhiều lượt vote nhất theo từng loại vấn nạn. Loại vấn
	// nạn nhập từ bàn phím
	public static void top3user() {
//	    int phimNhap;
//	    do {
//	        System.out.print("Nhập phím (0: Tài nguyên, 1: Môi trường): ");
//	        try {
//	            phimNhap = Integer.parseInt(scanner.nextLine());
//	            if (phimNhap == 0 || phimNhap == 1) {
//	                break;
//	            } else {
//	                System.out.println("Phím không hợp lệ. Vui lòng nhập lại.");
//	            }
//	        } catch (NumberFormatException e) {
//	            System.out.println("Phím không hợp lệ. Vui lòng nhập lại.");
//	        }
//	    } while (true);
//
//	    String loaiVanNan;
//	    if (phimNhap == 0) {
//	        loaiVanNan = "Tài nguyên";
//	    } else {
//	        loaiVanNan = "Môi trường";
//	    }

	    functionVote.displayUserVotes();
	    
	}
	      


	// Kiểm tra top 3 ngày có nhiều lượt vote nhất rơi vào thứ mấy trong tuần?
	public static void Daysofweek() {

		System.out.println("Dang sách vấn nạn trong bảng Vote");
		List<VoteMucDo> danhsach = functionVote.layThongTinToanBoVanNan();
		for (int i = 0; i < danhsach.size(); i++) {
			System.out.println("Record " + i + danhsach.get(i));
		}
		System.out.println("--------------------------");

		ArrayList<String> top3days = functionVote.demngay();
		for (String thongtin : top3days) {
			System.out.println(thongtin);
		}
	}
	
	
}
