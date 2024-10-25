package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.Entity;

import ConnectionMN.ConnectionSQL;
import Entity.NguoiDan;
import Entity.NhanVien;
import Entity.TaiKhoan;

public class TaiKhoan_Services {
	// Đăng ký tài khoản vào hệ thống
	public static int dangKyTK(TaiKhoan taiKhoan) {
		Connection connection = ConnectionSQL.getConnection();
		String dangKy = "Insert into UserLog values(?,?,?)";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(dangKy);
			ptsm.setString(1, taiKhoan.getaCount());
			ptsm.setString(2, taiKhoan.getPass());
			ptsm.setInt(3, taiKhoan.getsTT());
			result = ptsm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// đăng ký tài thông tin tk người dân
	public static int dangKyThongTinTK(NguoiDan nguoiDan) {
		Connection connection = ConnectionSQL.getConnection();
		{
			String dangKy = "Insert into NguoiDan values(?,?,?,?,?)";
			int result = 0;
			try {
				PreparedStatement ptsm = connection.prepareStatement(dangKy);
				ptsm.setString(1, nguoiDan.getIdNguoiDan());
				ptsm.setString(2, nguoiDan.getTenNguoiDan());
				ptsm.setString(3, nguoiDan.getDiaChi());
				ptsm.setString(4, nguoiDan.getSoDT());
				ptsm.setString(5, nguoiDan.getaCount());
				result = ptsm.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	}

	// đăng ký tài thông tin tk nhân viên
	public static int dangKyThongTinTKNV(NhanVien nhanVien) {
		Connection connection = ConnectionSQL.getConnection();
		{
			String dangKy = "Insert into NhanVienSo values(?,?,?,?,?)";
			int result = 0;
			try {
				PreparedStatement ptsm = connection.prepareStatement(dangKy);
				ptsm.setString(1, nhanVien.getIdNhanVienSo());
				ptsm.setString(2, nhanVien.getTenSo());
				ptsm.setString(3, nhanVien.getThongTinLienLac());
				ptsm.setInt(4, nhanVien.getNamCongTac());
				ptsm.setString(5, nhanVien.getaCount());
				result = ptsm.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	}

	// hàm đăng nhập
	public static boolean dangNhapTaiKhoan(Connection connection, String aCount, String pass) {
		String dangNhap = "Select * from UserLog where aCount = ? and pass =?";

		try {
			PreparedStatement ptsm = connection.prepareStatement(dangNhap);
			ptsm.setString(1, aCount);
			ptsm.setString(2, pass);
			ResultSet rs = ptsm.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// Xóa tài khoản nhân viên và các thông tin liên quan
	public static int xoaTaiKhoanNV(Connection connection, String idNhanVienSo) {
		int ketQua = 0;
		String xoaTTXL = "DELETE FROM TinhTrangXuLy WHERE idNhanVienSo = ?";
		String xoaNhanVien = "DELETE FROM NhanVienSo WHERE idNhanVienSo = ?";
		try {
			// Xóa bản ghi từ bảng Vote
			PreparedStatement ptsm = connection.prepareStatement(xoaTTXL);
			ptsm.setString(1, idNhanVienSo);
			ptsm.executeUpdate();
			// Xóa bản ghi từ bảng VanNan
			PreparedStatement ptsm1 = connection.prepareStatement(xoaNhanVien);
			ptsm1.setString(1, idNhanVienSo);
			ketQua = ptsm1.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: Xử lý ngoại lệ
		}
		return ketQua;
	}

	// Xóa tài khoản người dân và các thông tin liên quan
	public static int xoaTaiKhoan(Connection connection, String idNguoiDan) {
		int ketQua = 0;
		try {
			// Xóa các bản ghi liên quan từ bảng VanNan
			String selectVanNan = "SELECT idVanNan FROM VanNan WHERE idNguoiDan = ?";
			PreparedStatement ptsm = connection.prepareStatement(selectVanNan);
			ptsm.setString(1, idNguoiDan);
			ResultSet resultSet = ptsm.executeQuery();
			while (resultSet.next()) {
				String idVanNan = resultSet.getString("idVanNan");
				// Xóa bản ghi từ bảng TinhTrangXu
				String xoaTinhTrangXuLy = "DELETE FROM TinhTrangXuLy WHERE idVanNan = ?";
				PreparedStatement xoaVanNan = connection.prepareStatement(xoaTinhTrangXuLy);
				xoaVanNan.setString(1, idVanNan);
				xoaVanNan.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: Xử lý ngoại lệ
		}

		try {
			// Xóa các bản ghi liên quan từ bảng VanNan
			String selectIssueVote = "select v.idVanNan from Vote v\r\n"
					+ "join VanNan vn on v.idVanNan =vn.idVanNan where vn.idNguoidan=? \r\n" + "group by v.idVanNan";
			PreparedStatement ptsm = connection.prepareStatement(selectIssueVote);
			ptsm.setString(1, idNguoiDan);
			ResultSet resultSet = ptsm.executeQuery();
			while (resultSet.next()) {
				String idVanNan = resultSet.getString("idVanNan");
				// Xóa bản ghi từ bảng TinhTrangXu
				String xoaISVote = "DELETE FROM Vote WHERE idVanNan = ?";
				PreparedStatement xoaVanNan = connection.prepareStatement(xoaISVote);
				xoaVanNan.setString(1, idVanNan);
				xoaVanNan.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: Xử lý ngoại lệ
		}

		// Xóa bản ghi từ bảng Vote, VanNan, NguoiDan
		String xoaVote = "DELETE FROM Vote WHERE idNguoiDan = ?";
		String xoaVanNan = "DELETE FROM VanNan WHERE idNguoiDan = ?";
		String xoaNguoiDan = "DELETE FROM NguoiDan WHERE idNguoiDan = ?";
		try {
			// Xóa bản ghi từ bảng Vote
			PreparedStatement ptsm = connection.prepareStatement(xoaVote);
			ptsm.setString(1, idNguoiDan);
			ptsm.executeUpdate();
			// Xóa bản ghi từ bảng VanNan
			PreparedStatement ptsm1 = connection.prepareStatement(xoaVanNan);
			ptsm1.setString(1, idNguoiDan);
			ptsm1.executeUpdate();
			// Xóa bản ghi từ bảng NguoiDan
			PreparedStatement ptsm2 = connection.prepareStatement(xoaNguoiDan);
			ptsm2.setString(1, idNguoiDan);
			ketQua = ptsm2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: Xử lý ngoại lệ
		}
		return ketQua;
	}

	// cập nhật tài khoản người dân từ admin
	public static int capNhatND(Connection connection, String tenND, String diaChi, String soDT, String idNguoiDan) {
		String upDateND = "update NguoiDan set tenNguoiDan = ?, diaChi = ?, soDT = ? where idNguoiDan = ?";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(upDateND);
			ptsm.setString(1, tenND);
			ptsm.setString(2, diaChi);
			ptsm.setString(3, soDT);
			ptsm.setString(4, idNguoiDan);
			result = ptsm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// cập nhật tài khoản người dân từ tk người dân
	public static int capNhatND0(Connection connection, String tenND, String diaChi, String soDT, String taiKhoan) {
		String upDateND = "update NguoiDan set tenNguoiDan = ?, diaChi = ?, soDT = ? where aCount = ?";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(upDateND);
			ptsm.setString(1, tenND);
			ptsm.setString(2, diaChi);
			ptsm.setString(3, soDT);
			ptsm.setString(4, taiKhoan);
			result = ptsm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// cập nhật tài khoản nhân viên từ admin
	public static int capNhatNV(Connection connection, String tenNV, String diaChi, int namct, String idNhanVienSo) {
		String upDateND = "update NhanVienSo set tenSo = ?, thongTinLienLac = ?, namCongtac = ? where idNhanVienSo = ?";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(upDateND);
			ptsm.setString(1, tenNV);
			ptsm.setString(2, diaChi);
			ptsm.setInt(3, namct);
			ptsm.setString(4, idNhanVienSo);
			result = ptsm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// cập nhật tài khoản nhân viên từ nhân viên
	public static int capNhatNV0(Connection connection, String tenNV, String diaChi, int namct, String aCount) {
		String upDateND = "update NhanVienSo set tenSo = ?, thongTinLienLac = ?, namCongtac = ? where aCount = ?";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(upDateND);
			ptsm.setString(1, tenNV);
			ptsm.setString(2, diaChi);
			ptsm.setInt(3, namct);
			ptsm.setString(4, aCount);
			result = ptsm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// Đổi mật khẩu
	public static int doiMK(String taiKhoan, String pass) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "update UserLog set pass=? where aCount =?";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1, pass);
			ptsm.setString(2, taiKhoan);
			result = ptsm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// thay đổi mã phân quyền cho tài khoản
	public static int doiSTT(String taiKhoan, int sTT) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "update UserLog set sTT=? where aCount =?";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setInt(1, sTT);
			ptsm.setString(2, taiKhoan);
			result = ptsm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// chuyển dữ liệu từ người dân sang nhân viên
	public static int updateND_NV(String taiKhoan, String idNhanVien) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "insert into NhanVienSo (idNhanVienSo, tenSo, thongTinLienLac,namCongtac, aCount)"
				+ "select ?, tenNguoiDan, diaChi,0, aCount from NguoiDan\r\n" + "where aCount=? ";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1, idNhanVien);
			ptsm.setString(2, taiKhoan);
			result = ptsm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// chuyển dữ liệu từ người dân sang nhân viên
	public static int updateNV_ND(String taiKhoan, String idNguoiDan, String soDT) {
		Connection connection = ConnectionSQL.getConnection();
		String sql = "INSERT INTO NguoiDan (idNguoiDan, tenNguoiDan, diaChi, soDT, aCount) "
				+ "SELECT ?, tenSo, thongTinLienLac, ?, aCount FROM NhanVienSo WHERE aCount = ?";
		int result = 0;
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1, idNguoiDan);
			ptsm.setString(2, soDT);
			ptsm.setString(3, taiKhoan);
			result = ptsm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
