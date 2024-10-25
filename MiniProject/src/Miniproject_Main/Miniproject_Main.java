package Miniproject_Main;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

import ChuongTrinh.ChuongTrinhVanNan;
import ConnectionMN.ConnectionSQL;
import Controller.NguoiDan;
import Controller.TaiKhoan;

public class Miniproject_Main {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionSQL.getConnection();
//		ChuongTrinhVanNan.MenuVanNan1(connection);
		do {
			System.out.println("1. Đăng nhập tài khoản");
			System.out.println("2. Đăng ký tài khoản");
			System.out.print("Mời nhập lựa chọn: ");
			try {
				int key = Integer.parseInt(sc.nextLine());

				if ((key > 1 && key < 2) && TaiKhoan.checkTT() == 0) {
					System.out.println("Vui lòng đăng nhập.");
				} else {
					switch (key) {
					case 1: {
						TaiKhoan.dangNhap();
						break;
					}
					case 2: {
						TaiKhoan.dangKy();
						break;
					}
					case 3:
						System.out.println("Kêt thúc chương trình!");
						break;
					}
				}
				if (key == 3) {
					break;
				} else {
					if (key < 0 || key >3) {
						System.out.println("mời nhập lại!");
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Lựa chọn không hợp lệ. Mời nhập lại bằng số nguyên.");
			}
		} while (true);
		sc.close();
	}

}
