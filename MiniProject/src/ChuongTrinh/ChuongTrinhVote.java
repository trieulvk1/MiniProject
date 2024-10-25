package ChuongTrinh;

import java.util.Scanner;

import Controller.ControllerVoteMucDo;

public class ChuongTrinhVote {
	public static void chuongtrinhVotee(String aCount) {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("1. Nhập thêm thông tin vào bảng Vote ");
			System.out.println("2. Update lại thông tin của bảng Vote ");
			System.out.println("3. Xóa thông tin từ bảng Vote ");
			System.out.println("4. Tính điểm vote trung bình của từng mã vấn nạn và liệt kê top 3 vấn đề có điểm cao nhất");
			System.out.println("5. Thống kê số lượng vote theo từng tháng");
			System.out.println("6. Liệt kê top 3 users có nhiều lượt vote nhất theo từng loại vấn nạn");
			System.out.println("7. Kiểm tra top 3 ngày có nhiều lượt vote nhất rơi vào thứ mấy trong tuần");
			System.out.println("XIN MỜI CHỌN CHỨC NĂNG: ");
			int num = Integer.parseInt(sc.nextLine());
			switch (num) {
			case 1:
				ControllerVoteMucDo.nhapmucdo(aCount);
				break;
			case 2:
				ControllerVoteMucDo.updatevote(aCount);
				break;
			case 3:
				ControllerVoteMucDo.xoavote(aCount);
				break;
			case 4:
				ControllerVoteMucDo.lietketop3();
				break;
			case 5:
				ControllerVoteMucDo.thongkevote();
				break;
			case 6:
				ControllerVoteMucDo.top3user();
				break;
			case 7:
				ControllerVoteMucDo.Daysofweek();
				break;
			case 8:
				System.out.println("kết thúc chương trình");
				break;
			}
			if (num == 8) {
				break;
			} else {
				if (num < 0 || num > 8) {
					System.out.println("Nhập sai cú pháp. vui lòng nhập lại");
				}
			}
		} while (true);
	}
}
