package VanNan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entity.ThongTinVanNan;

public class ChucNangVanNan {
	/// Đăng tải vấn nạn lên database
	public static int dangTaiVanNan(Connection connection, ThongTinVanNan thongTinVanNan) {
		String dangVanNan = "INSERT INTO VanNan VALUES(?,?,?,?,?,?,?,?,?)";
		int check = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(dangVanNan);

			preparedStatement.setString(1, thongTinVanNan.getIdVanNan());
			preparedStatement.setString(2, thongTinVanNan.getIdNguoiDan());
			preparedStatement.setString(3, thongTinVanNan.getTenVanNan());
			preparedStatement.setString(4, thongTinVanNan.getDiaChi());
			preparedStatement.setString(5, thongTinVanNan.getMoTa());
			preparedStatement.setString(6, thongTinVanNan.getLoaiVanNan());

			java.sql.Date ngaydang = new Date(thongTinVanNan.getNgayDang().getTime());
			preparedStatement.setDate(7, ngaydang);
			preparedStatement.setString(8, thongTinVanNan.getTrangThai());
			preparedStatement.setString(9, thongTinVanNan.getHinhAnhDinhKem());



			/// câu lệnh insert lên database
			check = preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return check;
	}
	
	///lấy idNguoiDan
	public static String layIdNguoiDan(Connection connection, String aCount) {
		String selectIdNguoiDan = " select IdNguoiDan from NguoiDan where aCount = ? ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectIdNguoiDan);
			preparedStatement.setString(1, aCount);
			ResultSet resultSet =  preparedStatement.executeQuery();
			if(resultSet.next()) {
				return resultSet.getString("IdNguoiDan");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/// Lấy dữ liệu ra để validate idVanNan và idThongTin
	public static ThongTinVanNan selectDataValidate(Connection connection, String idVanNan) {
		ThongTinVanNan validateIssue = null;
		String selectData = "select idVanNan, idNguoiDan from VanNan where idVanNan =?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectData);
			preparedStatement.setString(1, idVanNan);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String idVanNan1 = resultSet.getString("idVanNan");
				String idNguoiDan = resultSet.getString("idNguoiDan");
				validateIssue = new ThongTinVanNan(idVanNan1, idNguoiDan);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return validateIssue;
	}

	/// update Thông tin vận nạn
	public static int updateIssue(Connection connection, ThongTinVanNan updateIssue) {
		int check = 0;
		String updateData = "update VanNan SET moTa =?, loaiVanNan =? WHERE idVanNan =?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateData);
			preparedStatement.setString(1, updateIssue.getMoTa());
			preparedStatement.setString(2, updateIssue.getLoaiVanNan());
			preparedStatement.setString(3, updateIssue.getIdVanNan());

			check = preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return check;
	}

	/// Xóa 1 vấn nạn
	public static int deleteIssue(Connection connection, String idVanNan) {
	    int check = 0;
	    String deleteTinhTrangXuLy = "DELETE FROM TinhTrangXuLy WHERE idVanNan = ?";
	    String deleteVote = "DELETE FROM Vote WHERE idVanNan = ?";
	    String deleteVanNan = "DELETE FROM VanNan WHERE idVanNan = ?";
	    try {
	        // Xóa bản ghi từ TinhTrangXuLi
	        PreparedStatement preparedStatement = connection.prepareStatement(deleteTinhTrangXuLy);
	        preparedStatement.setString(1, idVanNan);
	        preparedStatement.executeUpdate();

	        // Xóa bản ghi từ Vote
	        PreparedStatement preparedStatement2 = connection.prepareStatement(deleteVote);
	        preparedStatement2.setString(1, idVanNan);
	        preparedStatement2.executeUpdate();

	        // Xóa bản ghi từ VanNan
	        PreparedStatement preparedStatement3 = connection.prepareStatement(deleteVanNan);
	        preparedStatement3.setString(1, idVanNan);
	        check = preparedStatement3.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace(); // In ra lỗi để debug
	        // TODO: Xử lý ngoại lệ
	    }
	    return check;
	}


	/// Liệt kê số lượng từng loại vấn nạn theo tháng X của năm Y (X và Y nhập từ
	/// bàn phím)
	public static ArrayList<ThongTinVanNan> lietKeLoaiVanNan(Connection connection, int month, int year) {
		ArrayList<ThongTinVanNan> arr = new ArrayList<ThongTinVanNan>();
		String selectData = "select loaiVanNan, count(loaiVanNan) as soLuong from VanNan where month(ngayDang) = ? and year(ngaydang)=? group by loaiVanNan";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectData);
			preparedStatement.setInt(1, month);
			preparedStatement.setInt(2, year);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String loaiVanNan = resultSet.getString("loaiVanNan");
				int soLuong = resultSet.getInt("soLuong");
				ThongTinVanNan thongTinVanNan = new ThongTinVanNan(loaiVanNan, soLuong);
				arr.add(thongTinVanNan);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arr;
	}

	/// Lấy dữ liệu ra để validate diaChi
	public static String checkDiaChi(Connection connection, String diaChi) {

		String diaChi1 = null;
		String selectData = "select diaChi from VanNan where diaChi =?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectData);
			preparedStatement.setString(1, diaChi);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {

				diaChi1 = resultSet.getString("diaChi");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return diaChi1;
	}

	/// Liệt kê tổng các vấn nạn của mỗi tình trạng xử lý của thành phố (Thành phố
	/// nhập từ bàn phím)
	public static ArrayList<ThongTinVanNan> tongVanNanTheoTinhTrang(Connection connection,String diaChi) {
		ArrayList<ThongTinVanNan> arr = new ArrayList<ThongTinVanNan>();
		String selectString ="SELECT tt.tinhTrang, COUNT(tt.tinhTrang) AS soluong "
				+ "FROM VanNan vn "
				+ "JOIN TinhtrangXuLy tt ON vn.idVanNan = tt.idVanNan "
				+ "WHERE vn.diaChi = ? "
				+ "GROUP BY tt.tinhTrang; ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectString);
			preparedStatement.setString(1,diaChi);
		
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				
				String tinhTrang = resultSet.getString("tinhTrang");
				int soLuong = resultSet.getInt("soLuong");
				ThongTinVanNan tinhTrangVanNan = new ThongTinVanNan(tinhTrang, soLuong);
				arr.add(tinhTrangVanNan);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return arr;
	}

    ///liệt kê tổng vấn nạn theo mức độ
	public static ArrayList<ThongTinVanNan> tongVanNanTheoMucDo(Connection connection) {
	    ArrayList<ThongTinVanNan> arr = new ArrayList<ThongTinVanNan>();
	    String selectString = "SELECT idVanNan, AVG(diem) AS mucDoNghiemTrong FROM Vote GROUP BY idVanNan ORDER BY mucDoNghiemTrong DESC";
	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(selectString);

	        ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            String idVanNan = resultSet.getString("idVanNan");
	            int soLuong = resultSet.getInt("mucDoNghiemTrong");
	            ThongTinVanNan thongTinVanNan = new ThongTinVanNan(idVanNan, soLuong);
	            arr.add(thongTinVanNan);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return arr;
	}

	///Thống kê các vấn nạn đã phê duyệt nhưng chưa xử lý theo tháng X (X nhập từ bàn phím)
	public static ArrayList<ThongTinVanNan>  vanNanChuaXuLy(Connection connection, int month) {
		ArrayList<ThongTinVanNan> arr = new ArrayList<ThongTinVanNan>();
		String vanNanChuaXuLy = "SELECT vn.idVanNan, tenVanNan, diaChi, moTa, loaiVanNan, ngayDang, hinhAnhDinhKem, trangThai, tt.tinhTrang, idNguoiDan "
                + "FROM VanNan vn "
                + "LEFT JOIN TinhTrangXuLy tt ON vn.idVanNan = tt.idVanNan "
                + "WHERE (tt.tinhTrang = N'Chưa theo dõi' AND vn.trangThai = N'Đã phê duyệt' AND MONTH(vn.ngayDang) = ?) "
                + "OR NOT EXISTS (SELECT idVanNan FROM TinhTrangXuLy WHERE idVanNan = vn.idVanNan) AND vn.trangThai = N'Đã phê duyệt' AND MONTH(ngayDang) = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(vanNanChuaXuLy);
			preparedStatement.setInt(1, month);
			preparedStatement.setInt(2, month);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String idVanNan = resultSet.getString("idVanNan");
				String tenVanNan = resultSet.getString("tenVanNan");
				String diaChi = resultSet.getString("diaChi");
				String moTa = resultSet.getString("moTa");
				String loaiVanNan = resultSet.getString("loaiVanNan");
				Date ngayDang = resultSet.getDate("ngayDang");
				String hinhAnhDinhKem = resultSet.getString("hinhAnhDinhKem");
				String trangThai = resultSet.getString("trangThai");
				String tinhTrang = resultSet.getString("tinhTrang");
				String idNguoiDan = resultSet.getString("idNguoiDan");
				
				ThongTinVanNan thongTinVanNan = new ThongTinVanNan(idVanNan, tenVanNan, diaChi, moTa, loaiVanNan, ngayDang, hinhAnhDinhKem, trangThai, idNguoiDan, tinhTrang);
				arr.add(thongTinVanNan);
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arr;
	}
	
	///kiểm tra xem có vấn nạn hay không
	public static boolean checkVanNan(Connection connection) {
		String checkVanNan = "SELECT * FROM VanNan";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(checkVanNan);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	//hiển thị các vấn nạn chưa được phê duyệt
		public static ArrayList<ThongTinVanNan>  hienThiVanNanChuaPD(Connection connection) {
			ArrayList<ThongTinVanNan> arr = new ArrayList<ThongTinVanNan>();
			String vanNanChuaXuLy = "SELECT idVanNan, tenVanNan, diaChi, moTa, loaiVanNan, ngayDang, hinhAnhDinhKem, trangThai, idNguoiDan "
					+ " FROM VanNan  "
					+ " WHERE trangThai = N'Chưa phê duyệt' ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(vanNanChuaXuLy);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					String idVanNan = resultSet.getString("idVanNan");
					String tenVanNan = resultSet.getString("tenVanNan");
					String diaChi = resultSet.getString("diaChi");
					String moTa = resultSet.getString("moTa");
					String loaiVanNan = resultSet.getString("loaiVanNan");
					Date ngayDang = resultSet.getDate("ngayDang");
					String hinhAnhDinhKem = resultSet.getString("hinhAnhDinhKem");
					String trangThai = resultSet.getString("trangThai");
			
					String idNguoiDan = resultSet.getString("idNguoiDan");
					
					ThongTinVanNan thongTinVanNan = new ThongTinVanNan(idVanNan, tenVanNan, diaChi, moTa, loaiVanNan, ngayDang, hinhAnhDinhKem, trangThai, idNguoiDan);
					arr.add(thongTinVanNan);
					
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return arr;
		}
		
		///kiểm tra xem nhập idVanNan phê duyệt đúng hay chưa
		public static boolean  kiemTraIdVanNan(Connection connection, String idVanNan) {
			String vanNanChuaXuLy = "SELECT idVanNan, tenVanNan, diaChi, moTa, loaiVanNan, ngayDang, hinhAnhDinhKem, trangThai, idNguoiDan "
					+ " FROM VanNan  "
					+ " WHERE trangThai = N'Chưa phê duyệt' and idVanNan = ? ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(vanNanChuaXuLy);
				preparedStatement.setNString(1, idVanNan);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				return resultSet.next();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
		}
		
		///update trạng thái phê duyệt cho vấn nạn
		
			public static int updateTrangThaiVanNan(Connection connection, String idVanNan) {
				int check = 0;
				String updateData = "update VanNan SET trangThai =? WHERE idVanNan =?";
	 
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(updateData);
					preparedStatement.setNString(1, "Đã phê duyệt");
					preparedStatement.setString(2, idVanNan);
	 
					check = preparedStatement.executeUpdate();
				} catch (Exception e) {
					// TODO: handle exception
				}
				return check;
			}
}
