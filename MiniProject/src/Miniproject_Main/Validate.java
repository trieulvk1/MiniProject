package Miniproject_Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ConnectionMN.ConnectionSQL;

public class Validate {

	// check thông tin tài khoản
	public static boolean taiKhoan(Connection connection, String tk) {
		String ddMa = "[a-zA-Z_0-9]{5,}";
		Pattern r = Pattern.compile(ddMa);
		Matcher m = r.matcher(tk);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	// check mật khẩu
	public static boolean matKhau(Connection connection, String pass) {
		String ddMa = ".{6,}";
		Pattern r = Pattern.compile(ddMa);
		Matcher m = r.matcher(pass);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	// check thông tin ID người dân
	public static boolean iDNguoiDan(Connection connection, String Idnd) {
		String ddMa = "ND\\d{4}";
		Pattern r = Pattern.compile(ddMa);
		Matcher m = r.matcher(Idnd);
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	
	// check thông tin ID người dân
	public static boolean iDNhanVien(Connection connection, String Idnd) {
		String ddMa = "NV\\d{4}";
		Pattern r = Pattern.compile(ddMa);
		Matcher m = r.matcher(Idnd);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	// check số điện thoại người dân
	public static boolean soDTNguoiDan(String soDT) {
		String ddMa = "09[0-9]{8}";
		Pattern r = Pattern.compile(ddMa);
		Matcher m = r.matcher(String.valueOf(soDT));
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	// check tồn tại Acount người dân trong User
	public static boolean nguoiDanVaAcount(Connection connection, String aCount, String taiKhoan) {
		Pattern r = Pattern.compile(taiKhoan);
		Matcher m = r.matcher(aCount);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	// check tài khoản tồn tại
	public static boolean checkAcount(Connection connection, String aCount) {
		String sql = "Select * from UserLog where aCount = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, aCount);
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

	// check thông tin người dân tồn tại
	public static boolean checkNguoiDan(Connection connection, String aCount) {
		String sql = "Select * from NguoiDan where aCount = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, aCount);
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
	
	// check thông tin nhân viên tồn tại
		public static boolean checkNhanVien(Connection connection, String aCount) {
			String sql = "Select * from NhanVienSo where aCount = ?";
			try {
				PreparedStatement pstm = connection.prepareStatement(sql);
				pstm.setString(1, aCount);
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

	// check ID tồn tại
	public static boolean checkIDNor(Connection connection, String aCount) {
		String sql = "Select * from NguoiDan where idNguoiDan = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, aCount);
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

	// check ID người dân
	public static boolean checkIDND(Connection connection, String idND) {
		String sql = "Select * from NguoiDan where idNguoiDan = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, idND);
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
	public static boolean checkIDNV(Connection connection, String idNV) {
		String sql = "Select * from NhanVienSo where idNhanVienSo = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, idNV);
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

	// check tháng đăng vấn nạn
	public static boolean checkThangDangVN(Connection connection, int thangDang) {
		String sql = "Select * from VanNan where Month(ngayDang) = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setInt(1, thangDang);
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

	// check quý đăng vấn nạn
	public static boolean checkQuyDangVN(Connection connection, int thangDang) {
		String sql = "Select * from VanNan where DATEPART(QUARTER, ngayDang) = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setInt(1, thangDang);
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

	// check quyền truy cập của user
	public static int checkQTC(Connection connection, String aCount) {
		int result = 0;
		String sql = "select sTT from UserLog where aCount =?";
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1, aCount);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				result = rs.getInt("sTT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	// check thông tin chương trình
	public static int checkChuongTrinh(Connection connectionn) {
		int count = 0;
		String list = "Select count(*) as count from VanNan";
		try {
			PreparedStatement ptsm = connectionn.prepareStatement(list);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// check thông tin chương trình
	public static int checkChuongTrinhND(Connection connectionn) {
		int count = 0;
		String list = "Select count(*) as count from NguoiDan";
		try {
			PreparedStatement ptsm = connectionn.prepareStatement(list);
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// check thông tin tài khoản
	public static boolean checkChuongTrinh1(Connection connection, int key) {
		String ddMa = "[0-9]{1}";
		Pattern r = Pattern.compile(ddMa);
		Matcher m = r.matcher(String.valueOf(key));
		;
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	
	// check năm công tác
		public static boolean checkNamCT(Connection connection, int namCT) {
			try {
	            Scanner sc = new Scanner (System.in);
	            namCT=sc.nextInt();
	            return true;
	        } catch (NumberFormatException e) {
	            return false;
	        }
		}
	
	//check mật khẩu
	public static boolean checkMatKhau(String aCount,String pass) {
		Connection connection = ConnectionSQL.getConnection();
		String sql="select pass from UserLog where aCount =? and pass =?";
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1,aCount);
			ptsm.setString(2,pass);
			ResultSet rs=ptsm.executeQuery();
			while(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//check acount trong bảng người dân đã tồn tại hay chưa
	public static boolean checkaCountND(String aCount) {
		Connection connection = ConnectionSQL.getConnection();
		String sql="select aCount from NguoiDan where aCount =? ";
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1,aCount);
			ResultSet rs=ptsm.executeQuery();
			while(rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	//check acount trong bảng nhân viên đã tồn tại hay chưa
		public static boolean checkaCountNV(String aCount) {
			Connection connection = ConnectionSQL.getConnection();
			String sql="select aCount from NhanVienSo where aCount =? ";
			try {
				PreparedStatement ptsm = connection.prepareStatement(sql);
				ptsm.setString(1,aCount);
				ResultSet rs=ptsm.executeQuery();
				while(rs.next()) {
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	//check nhập tháng
		public static boolean checkThang(Connection connection, int thang) {
			if(thang > 0 && thang <= 12) {
				return true;
			}
			return false;
		}
		
		public static boolean checkQuy(Connection connection, int quy) {
			if(quy > 0 && quy <= 4) {
				return true;
			}
			return false;
		}
		
	//check tên phải là chữ
		public static boolean checkName(Connection connection, String ten) {
			String ddMa ="^[\\p{L}\\p{M}\\s]+$";
			Pattern r = Pattern.compile(ddMa);
			Matcher m = r.matcher(ten);
			if (!m.matches()) {
				return false;
			}
			return true;
		}
		
	//check tai khoan bắt đầu là chữ
		public static boolean checkTKChu(Connection connection, String ten) {
			String ddMa = "[a-zA-Z].*";
			Pattern r = Pattern.compile(ddMa);
			Matcher m = r.matcher(ten);
			if (!m.matches()) {
				return false;
			}
			return true;
		}
}
