package Validate_Vote;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ConnectionMN.ConnectionSQL;

public class Validate {
	static Scanner scanner = new Scanner(System.in);

	// kiểm tra mã vấn nạn đó đã được phê duyệt chưa
	public static boolean checkTinhTrang(String idVanNan) {
		Connection connection = ConnectionSQL.getConnection();
        boolean kiemtra = false;

        try {
            String query = "SELECT COUNT(*) FROM TinhTrangXuLy WHERE idVanNan = ?";
            try (PreparedStatement pstm = connection.prepareStatement(query)) {
                pstm.setString(1, idVanNan);
                try (ResultSet resultSet = pstm.executeQuery()) {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    kiemtra = count > 0;
                }
            }
            // Đóng kết nối ngay sau khi sử dụng trong khối try
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kiemtra;
    }
	
	
	// kiểm tra định dạng chuỗi ID nhập từ bàn phím
	public static boolean ValidateIDNHAP(String id) {
		String chuoi = "^ID\\d{4}$";
		Pattern pattern = Pattern.compile(chuoi);
		Matcher matcher = pattern.matcher(id);
		return matcher.matches();
	}

	// kiểm tra xem ID có tồn tại trong cơ sở dữ liệu SQL hay không

	public static boolean VALIDATETONTAIID(String id) {
		Connection connection = ConnectionSQL.getConnection();
		String kiemtra = "SELECT COUNT(*) FROM NguoiDan WHERE idNguoiDan = ?";
		boolean isIdValid = false;
		try (PreparedStatement pstm = connection.prepareStatement(kiemtra)) {
			pstm.setString(1, id);
			try (ResultSet resultSet = pstm.executeQuery()) {
				resultSet.next();
				int count = resultSet.getInt(1);

				isIdValid = count > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isIdValid;
	}

	// KIỂM TRA ID NGƯỜI DÂN CÓ TỒN TẠI TRONG BẢNG VOTE KHÔNG THEO NGÀY

	public static boolean VALIDATEIDDANVOTETONTAIUPDATE(String id, java.sql.Date ngayVote) {
	    Connection connection = ConnectionSQL.getConnection();
	    String kiemtra = "SELECT COUNT(*) FROM Vote WHERE idNguoiDan = ? AND ngayVote = ?";
	    boolean isIdValid = false;
	    try (PreparedStatement pstm = connection.prepareStatement(kiemtra)) {
	        pstm.setString(1, id);
	        pstm.setDate(2, ngayVote);
	        try (ResultSet resultSet = pstm.executeQuery()) {
	            resultSet.next();
	            int count = resultSet.getInt(1);

	            isIdValid = count > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    try {
	        connection.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return isIdValid;
	}

	// VALIDATE ID DAN VOTE 
	public static boolean VALIDATEIDDANVOTETONTAI(String id) {
	    Connection connection = ConnectionSQL.getConnection();
	    String kiemtra = "SELECT COUNT(*) FROM Vote WHERE idNguoiDan =?";
	    boolean isIdValid = false;
	    try (PreparedStatement pstm = connection.prepareStatement(kiemtra)) {
	        pstm.setString(1, id);
	        try (ResultSet resultSet = pstm.executeQuery()) {
	            resultSet.next();
	            int count = resultSet.getInt(1);

	            isIdValid = count > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    try {
	        connection.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return isIdValid;
	}
	
	// kiểm tra định dạng chuỗi ID nhập từ bàn phím
	public static boolean ValidateIDVANNAN(String id) {
		String chuoi = "^Issue\\d{5}$";
		Pattern pattern = Pattern.compile(chuoi);
		Matcher matcher = pattern.matcher(id);
		return matcher.matches();
	}

	// kiểm tra xem ID có tồn tại trong cơ sở dữ liệu SQL hay không

//	public static boolean VALIDATETONTAIVANNAN(String idvannantimkiem) {
//		Connection connection = ConnectionSQL.getConnection();
//		String kiemtra = "SELECT COUNT(*) FROM VanNan WHERE idVanNan = ?";
//	    boolean isIdValid = false;
//
//	    try (PreparedStatement pstm = connection.prepareStatement(kiemtra)) {
//	        pstm.setString(1, idvannantimkiem);
//	        try (ResultSet resultSet = pstm.executeQuery()) {
//	            resultSet.next();
//	            int count = resultSet.getInt(1);
//
//	            isIdValid = count > 0;
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//
//	    try {
//	        connection.close();
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//
//	    return isIdValid;
//	}

	// kiểm tra xem ID có tồn tại trong cơ sở dữ liệu VOTE hay không

	public static boolean VALIDATEIDVANNANVOTETONTAIUPDATE(String idvannantimkiem) {
		Connection connection = ConnectionSQL.getConnection();
		String kiemtra = "SELECT COUNT(*) FROM Vote WHERE idVanNan = ?";
		boolean isIdValid = false;
		try (PreparedStatement pstm = connection.prepareStatement(kiemtra)) {
			pstm.setString(1, idvannantimkiem);
			try (ResultSet resultSet = pstm.executeQuery()) {
				resultSet.next();
				int count = resultSet.getInt(1);

				isIdValid = count > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isIdValid;
	}

// TỒN TẠI VOTE
	
	public static boolean VALIDATEIDVANNANVOTETONTAI(String idvannantimkiem) {
		Connection connection = ConnectionSQL.getConnection();
		String kiemtra = "SELECT COUNT(*) FROM Vote WHERE idVanNan = ?";
		boolean isIdValid = false;
		try (PreparedStatement pstm = connection.prepareStatement(kiemtra)) {
			pstm.setString(1, idvannantimkiem);
			try (ResultSet resultSet = pstm.executeQuery()) {
				resultSet.next();
				int count = resultSet.getInt(1);

				isIdValid = count > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isIdValid;
	}
	
	//
	
	public static boolean VALIDATEIDVANNANVOTETONTAIXOA(String idvannantimkiem,String id) {
		Connection connection = ConnectionSQL.getConnection();
		String kiemtra = "SELECT COUNT(*) FROM Vote WHERE idVanNan = ? AND idNguoiDan = ? ";
		boolean isIdValid = false;
		try (PreparedStatement pstm = connection.prepareStatement(kiemtra)) {
			pstm.setString(1, idvannantimkiem);
			pstm.setString(2, id);
			try (ResultSet resultSet = pstm.executeQuery()) {
				resultSet.next();
				int count = resultSet.getInt(1);

				isIdValid = count > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isIdValid;
	}
	
	// kiểm tra xem chuỗi chỉ chứa chữ số
	public static boolean Validatenumber(String str) {
		return str.matches("\\d+");
	}

	// Kiểm tra ngày
	public static boolean formatDate(String ngayVote) {
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/M/yyyy");

		try {
			LocalDate.parse(ngayVote, formatter1);
			return true;
		} catch (DateTimeParseException e1) {
			try {
				LocalDate.parse(ngayVote, formatter2);
				return true;
			} catch (DateTimeParseException e2) {
				return false; // Trả về false nếu không phù hợp định dạng nào
			}
		}
	}

	// kiểm tra dữ liệu ngày đó có nằm trong data không
	public static boolean Validatecheckday(java.sql.Date ngaynhapvao) {
		Connection connection = ConnectionSQL.getConnection();
		boolean ngayVotetontai = false;
		String check = "SELECT COUNT(*) FROM Vote WHERE ngayVote = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(check);
			pstm.setDate(1, ngaynhapvao);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				ngayVotetontai = count > 0;
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
		return ngayVotetontai;
	}

	// kiem tra nhap loaivande khong có ký tự đặt biệt
	public static boolean ValidateLoaiVanDe(String chuoi) {
		// Sử dụng biểu thức chính quy để kiểm tra xem chuỗi có chứa kí tự đặc biệt hay
		// không
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9À-Ỹà-ỹ ]");
		Matcher matcher = pattern.matcher(chuoi);

		return !matcher.find();
	}

	// kiểm tra loại vấn đề:
	public static boolean VALIDATETONTAIVANDE(String loaiVanNan) {
		Connection connection = ConnectionSQL.getConnection();
		String kiemtra = "SELECT COUNT(*) FROM VanNan WHERE loaiVanNan like ?";
		boolean isIdValid = false;
		try {
			PreparedStatement pstm = connection.prepareStatement(kiemtra);
			pstm.setString(1, loaiVanNan);
			ResultSet resultSet = pstm.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);

			isIdValid = count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isIdValid;
	}

	// chuyển đổi Date thành thứ mấy trong tuần

	public static String getDayOfWeek(String ngayVotekiemtra) {
		try {
			// Chuyển đổi chuỗi ngày thành đối tượng Date
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(ngayVotekiemtra);

			// Chuyển đổi đối tượng Date thành thứ trong tuần
			SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

//	            E đại diện cho "Day of week" (Ngày trong tuần).
//	            EEE đại diện cho ngày trong tuần ở dạng viết tắt (ví dụ: Mon, Tue, Wed).
//	            EEEE đại diện cho ngày trong tuần ở dạng đầy đủ (ví dụ: Monday, Tuesday, Wednesday).
			return dayFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "Không xác định";
		}
	}

}
