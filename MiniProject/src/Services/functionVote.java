package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import ConnectionMN.*;
import Entity.TinhTrangXyLy;
import Entity.VoteMucDo;
import Validate_Vote.*;



public class functionVote {
	static Scanner scanner = new Scanner(System.in);

	// thêm thông tin vào bảng vote mức độ
	public static int insertmucdo(int diem, java.sql.Date ngayHienTaiSQL, String Idnd, String idvannan) {
		int result = 0;
		Connection connection = ConnectionSQL.getConnection();
		String themmucdo = "INSERT INTO Vote (idNguoidan, idVanNan, ngayVote, diem) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstm = connection.prepareStatement(themmucdo)) {
			pstm.setString(1, Idnd);
			pstm.setString(2, idvannan);
			pstm.setDate(3, (java.sql.Date) ngayHienTaiSQL);
			pstm.setInt(4, diem);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int updatemoi(String idnguoidan, String idvannan, int diemtimkiem) {
		Connection connection = ConnectionSQL.getConnection();
		int result = 0;
		String update = "UPDATE Vote SET diem = ? WHERE idNguoidan = ? AND idVanNan = ?";
		try (PreparedStatement pstm = connection.prepareStatement(update)) {
			pstm.setInt(1, diemtimkiem);
			pstm.setString(2, idnguoidan);
			pstm.setString(3, idvannan);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int xoadulieu(String idnguoidan, String idvannan) {
		Connection connection = ConnectionSQL.getConnection();
		int result = 0;
		String delete = "DELETE FROM Vote WHERE idNguoidan = ? AND idVanNan = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(delete);
			pstm.setString(1, idnguoidan);
			pstm.setString(2, idvannan);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ArrayList<String> tinhdiemtop3() {
		Connection connection = ConnectionSQL.getConnection();
		ArrayList<String> diemtop3 = new ArrayList<>();
		try {
			String Tinhtop3 =  "SELECT TOP 3 vn.idVanNan, vn.tenVanNan, ROUND(AVG(v.diem), 0) AS diemTrungBinh " +
	                "FROM VanNan vn " +
	                "JOIN Vote v ON vn.idVanNan = v.idVanNan " +
	                "GROUP BY vn.idVanNan, vn.tenVanNan " +
	                "ORDER BY diemTrungBinh DESC";

			PreparedStatement pstm = connection.prepareStatement(Tinhtop3);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String idVanNan = rs.getString("idVanNan");
				String tenVanNan = rs.getString("tenVanNan");
				double diemTrungBinh = rs.getDouble("diemTrungBinh");
				diemtop3.add("ID vấn nạn:" + idVanNan + "," + "Tên vấn nạn:" + tenVanNan + "," + "Điểm trung bình:"
						+ diemTrungBinh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return diemtop3;
	}

	public static int demVoteTheoThang(int month) {
		Connection connection = ConnectionSQL.getConnection();
		int voteCount = 0;
		try {
			String voteThang = "SELECT COUNT(*) FROM Vote WHERE MONTH(ngayVote) = ?";
			PreparedStatement pstm = connection.prepareStatement(voteThang);
			pstm.setInt(1, month);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				voteCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return voteCount;
	}

	public static void displayUserVotes() {
	    Connection connection = ConnectionSQL.getConnection();

	    try {
	        String ketqua = "SELECT nd.idNguoiDan, nd.tenNguoiDan, COUNT(v.idNguoiDan) AS SOLUONGVOTE " +
	                                "FROM NguoiDan nd " +
	                                "JOIN Vote v ON nd.idNguoiDan = v.idNguoiDan " +
	                                "GROUP BY nd.idNguoiDan, nd.tenNguoiDan " +
	                                "ORDER BY SOLUONGVOTE DESC";

	        PreparedStatement pstm = connection.prepareStatement(ketqua);
	        ResultSet rs = pstm.executeQuery();

	        while (rs.next()) {
	            String idNguoiDan = rs.getString("idNguoiDan");
	            String tenNguoiDan = rs.getString("tenNguoiDan");
	            int SOLUONGVOTEUSER = rs.getInt("SOLUONGVOTE");

	            System.out.println("ID người dân: " + idNguoiDan + ", Tên người dân: " + tenNguoiDan +
	                    ", Số lượt vote: " + SOLUONGVOTEUSER);
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
	}



	public static ArrayList<String> demngay() {
		Connection connection = ConnectionSQL.getConnection();
		ArrayList<String> top3Days = new ArrayList<String>();
		try {
			String top3 = "SELECT TOP 3 ngayVote, COUNT(*) AS soLuotVote " + "FROM Vote " + "GROUP BY ngayVote "
					+ "ORDER BY soLuotVote DESC";
			PreparedStatement pstm = connection.prepareStatement(top3);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				String ngayVotekiemtra = rs.getString("ngayVote");
				int soLuongVote = rs.getInt("soLuotVote");

				// chuyển đổi chuỗi ngày thành thứ trong tuần
				String thuTrongTuan = Validate.getDayOfWeek(ngayVotekiemtra);

				top3Days.add("Ngày: " + ngayVotekiemtra + ", Số lượt vote: " + soLuongVote + ", Thứ: " + thuTrongTuan);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return top3Days;
	}
	
	public static String selectIDND(Connection connection, String aCount) {
		String sql="select idNguoiDan from NguoiDan where aCount=?";
		try {
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1,aCount);
			ResultSet rs = ptsm.executeQuery();
			if(rs.next()) {
				return rs.getString("idNguoiDan");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql ;
		
	}

	public static List<VoteMucDo> layThongTinToanBoVanNan() {
		List<VoteMucDo> danhsach = new ArrayList<>();
		Connection connection = ConnectionSQL.getConnection();
        String SQL = "SELECT * FROM Vote";
        try {
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                VoteMucDo vt = new VoteMucDo();
                vt.setIdNguoidan(rs.getString("idNguoiDan"));
                vt.setIdVanNan(rs.getString("idVanNan"));
                vt.setDiem(rs.getInt("diem"));
                vt.setNgayVote(rs.getDate("ngayVote"));

                danhsach.add(vt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
	}

	public static List<TinhTrangXyLy> layThongTinToanBoTinhTrangXuLy() {
		List<TinhTrangXyLy> danhsach = new ArrayList<>();
		Connection connection = ConnectionSQL.getConnection();
        String SQL = "SELECT * FROM TinhTrangXuLy";
        try {
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
            	TinhTrangXyLy ttxl = new TinhTrangXyLy();
            	ttxl.setIdVanNan(rs.getString("idVanNan"));
                ttxl.setIdNhanVienSo(rs.getString("idNhanVienSo"));
                ttxl.setNgayBatDau(rs.getDate("ngayBatDau" ));
            	ttxl.setNgayKetThuc(rs.getDate("ngayKetThuc" ));
                ttxl.setTinhTrang(rs.getString("tinhTrang" ));
                danhsach.add(ttxl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhsach;
	}

}
