package Validate_TTXL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import ConnectionMN.ConnectionSQL;

public class Validate {
	public static int validateData() {
		// validate data
		Connection connection = ConnectionSQL.getConnection();
		int count = 0;
		String validate = "select COUNT(*) as count from TinhTrangXuLy ";
		try {
			PreparedStatement pstm = connection.prepareStatement(validate);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// check mã vấn nạn đã tồn tại trong bảng VanNan
	public static boolean checkIdVanNan(String idVanNan) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "Select * from VanNan where idVanNan = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, idVanNan);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	// check trạng thái phê duyệt
	public static boolean checkTrangThai(String idVanNan) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "SELECT trangThai FROM VanNan WHERE idVanNan = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, idVanNan);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				String trangThai = rs.getString("trangThai");
				if ("Đã phê duyệt".equals(trangThai)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}

		return false;
	}

	// check mã vấn nạn trùng với vấn nạn cũ đã thêm vào bảng TinhTrangXuLy
	public static boolean checkIdVanNan1(String idVanNan) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "SELECT * FROM TinhTrangXuLy WHERE idVanNan = ?";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setString(1, idVanNan);
			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					return true; // Trả về true nếu có bất kỳ hàng nào phù hợp
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
	//check ngày bắt đầu trước ngày kết thúc
	public static boolean validateDates(Date ngayBatDau, String ngayKetThuc) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date endDate = dateFormat.parse(ngayKetThuc);
			if(ngayBatDau.after(endDate)) {
				 
				 return false;
			}
			
		} catch (ParseException  e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean isValidDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            // Thử chuyển đổi chuỗi thành đối tượng Date
            Date date = sdf.parse(dateString);
            // Kiểm tra xem ngày đã được chuyển đổi đúng định dạng hay không
            return true;
        } catch (ParseException e) {
            // Nếu có lỗi, đồng nghĩa với việc chuỗi không đúng định dạng
            return false;
        }
    }
	
	
	 //check ngày bắt đầu và ngày kết thúc phải trước ngày hiện tại
//	public static boolean validateDate(String inputDateString) {
//        // Định dạng ngày theo định dạng dd/MM/yyyy
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//        try {
//            // Chuyển đổi chuỗi ngày thành đối tượng Date
//            Date inputDate = dateFormat.parse(inputDateString);
//
//            // Lấy ngày hiện tại
//            Date currentDate = new Date();
//
//            // Kiểm tra xem ngày nhập vào có trước ngày hiện tại không
//            if (inputDate.after(currentDate)) {
//               
//                return false;
//            }
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return false; // Nếu có lỗi chuyển đổi định dạng
//        }
//
//        // Nếu không có lỗi và ngày nhập vào trước hoặc bằng ngày hiện tại, trả về true
//        return true;
//    }
	
	
	// check mã nhân viên sở
	public static boolean checkidNhanVien(String idNhanVienSo) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "Select * from NhanVienSo where idNhanVienSo = ? ";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, idNhanVienSo);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return true;
	}

	// check ngày nhập vào có đúng không
	public static boolean formatDate(String ngay) {
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/M/yyyy");

		try {
			LocalDate.parse(ngay, formatter1);
			return true;
		} catch (DateTimeParseException e1) {
			try {
				LocalDate.parse(ngay, formatter2);
				return true;
			} catch (DateTimeParseException e2) {
				return false; // Trả về false nếu không phù hợp định dạng nào
			}
		}
	}

	// check trình trạng vấn nạn cũ và mới
	public static boolean checkTTVN(String idVanNan, String newTinhTrang) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "select tinhTrang from TinhTrangXuLy where idVanNan = ? and tinhTrang = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, idVanNan);
			pstm.setString(2, newTinhTrang);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return true;
	}

	// check tháng đăng vấn nạn
	public static boolean checkNamXuLy(int namXyLy) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "Select * from TinhTrangXuLy where Year(ngayKetThuc) = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setInt(1, namXyLy);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean kiemTraNamTrongKhoang(int nam) {
		return nam >= 1980 && nam <= 2023;
	}

	// check quý đăng vấn nạn
	public static boolean checkQuyXuLy(int quy) {
		String sql = "Select * from TinhTrangXuLy where DATEPART(QUARTER, ngayKetThuc) = ?";
		try {
			Connection connection = ConnectionSQL.getConnection();
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setInt(1, quy);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean kiemTraQuyTrongKhoang(int quy) {
		return quy >= 1 && quy <= 4;
	}
}
