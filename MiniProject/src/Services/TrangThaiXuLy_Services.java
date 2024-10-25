package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Entity.ThongTinVanNan;
import Entity.TinhTrangXyLy;
import ConnectionMN.ConnectionSQL;

public class TrangThaiXuLy_Services {
	static Scanner sc = new Scanner(System.in);

	public static int insert(TinhTrangXyLy obo) {
		int result = 0;
		Connection connection = ConnectionSQL.getConnection();
		String insert = "insert into TinhTrangXuLy values (?,?,?,?,?)";
		try {
			PreparedStatement pstm = connection.prepareStatement(insert);
			pstm.setString(1, obo.getIdVanNan());
			pstm.setString(2, obo.getIdNhanVienSo());

			if (obo.getNgayBatDau() != null) {
				java.sql.Date sqlngayBatDau = new java.sql.Date(obo.getNgayBatDau().getTime());
				pstm.setDate(3, sqlngayBatDau);
			} else {
				pstm.setNull(3, java.sql.Types.DATE);
			}

			if (obo.getNgayKetThuc() != null) {
				java.sql.Date sqlngayKetThuc = new java.sql.Date(obo.getNgayKetThuc().getTime());
				pstm.setDate(4, sqlngayKetThuc);
			} else {
				pstm.setNull(4, java.sql.Types.DATE);
			}

			pstm.setString(5, obo.getTinhTrang());

			result = pstm.executeUpdate();
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
		return result;
	}
	public static List layDuLieuVanNan() {
		List<ThongTinVanNan> list = new ArrayList<ThongTinVanNan>();
		Connection connection = ConnectionSQL.getConnection();
		String sql = "SELECT * FROM VanNan";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			
			while (rs.next()) {
				ThongTinVanNan vn = new ThongTinVanNan();
				vn.setIdVanNan(rs.getString("idVanNan"));
				vn.setTenVanNan(rs.getString("tenVanNan"));
				vn.setDiaChi(rs.getString("diaChi"));
				vn.setMoTa(rs.getString("moTa"));
				vn.setLoaiVanNan(rs.getString("loaiVanNan"));
				vn.setNgayDang(rs.getDate("ngayDang"));
				vn.setHinhAnhDinhKem(rs.getString("hinhAnhDinhKem"));
				vn.setTrangThai(rs.getString("trangThai"));
				vn.setIdNguoiDan(rs.getString("idNguoiDan"));
 
				list.add(vn);
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
 
		return list;
	}
 
	
	public static List layDuLieuTTXL() {
        Connection connection = ConnectionSQL.getConnection();
        String sql = "SELECT * FROM TinhTrangXuLy";
        List<TinhTrangXyLy> list = new ArrayList<TinhTrangXyLy>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {

            	TinhTrangXyLy open = new TinhTrangXyLy();
                open.setIdVanNan(rs.getString("idVanNan"));
                open.setIdNhanVienSo(rs.getString("idNhanVienSo"));
                open.setNgayBatDau(rs.getDate("ngayBatDau"));
                open.setNgayKetThuc(rs.getDate("ngayKetThuc"));
                open.setTinhTrang(rs.getString("tinhTrang"));

                list.add(open);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
	
	public static int update(String idVanNan, String newTinhTrang, Date ngayBatDau, Date ngayKetThuc) {
        int result = 0;
        Connection connection = ConnectionSQL.getConnection();
        String update = "UPDATE TinhTrangXuLy SET TinhTrang = ?,ngayBatDau = ?, ngayKetThuc = ? WHERE IdVanNan = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(update);

            pstm.setString(1, newTinhTrang);
            
            if (ngayBatDau != null) {
                java.sql.Date sqlDate = new java.sql.Date(ngayBatDau.getTime());
                pstm.setDate(2, sqlDate);
            
            } else {
                pstm.setNull(2, java.sql.Types.DATE);
            }
            if (ngayKetThuc != null) {
            java.sql.Date sqlDate1 = new java.sql.Date(ngayKetThuc.getTime());
            pstm.setDate(3, sqlDate1);
            }
            else {
                pstm.setNull(3, java.sql.Types.DATE);
            }
            pstm.setString(4, idVanNan);

            result = pstm.executeUpdate();
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
        return result;
    }
	
	public static Date getDay(String idVanNan) {
        Date ngayBatDau = null;
        Connection connection = ConnectionSQL.getConnection();
        try {
            String sql = "SELECT ngayBatDau FROM TinhTrangXuLy WHERE idVanNan = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, idVanNan);
            ResultSet rs = pstm.executeQuery();
        
             if (rs.next()) {
                    // Giả sử ngayBatDau là kiểu Date trong cơ sở dữ liệu
                 ngayBatDau = rs.getDate("ngayBatDau");

                    // Định dạng ngày về dạng dd/MM/yyyy
                    if (ngayBatDau != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = sdf.format(ngayBatDau);
                        
                        // Parse ngày đã định dạng về kiểu Date (tuỳ chọn)
                        try {
                            ngayBatDau = sdf.parse(formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace(); // Xử lý ngoại lệ theo cách cần thiết
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace(); // Xử lý ngoại lệ theo cách cần thiết
            }
        
        return ngayBatDau;
    }
    public static Date getDay1(String idVanNan) {
        Date ngayKetThuc = null;
        Connection connection = ConnectionSQL.getConnection();
        try {
            String sql = "SELECT ngayKetThuc FROM TinhTrangXuLy WHERE idVanNan = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, idVanNan);
            ResultSet rs = pstm.executeQuery();
        
             if (rs.next()) {
                    // Giả sử ngayKetThuc là kiểu Date trong cơ sở dữ liệu
                    ngayKetThuc = rs.getDate("ngayKetThuc");

                    // Định dạng ngày về dạng dd/MM/yyyy
                    if (ngayKetThuc != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = sdf.format(ngayKetThuc);
                        
                        // Parse ngày đã định dạng về kiểu Date (tuỳ chọn)
                        try {
                            ngayKetThuc = sdf.parse(formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace(); // Xử lý ngoại lệ theo cách cần thiết
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace(); // Xử lý ngoại lệ theo cách cần thiết
            }
        
        return ngayKetThuc;
    }

	/**
	 * @param idVanNan
	 * @return
	 */
	public static int delete(String idVanNan) {
		int result = 0;
		Connection connection = ConnectionSQL.getConnection();
		String delete = "DELETE FROM TinhTrangXuLy WHERE IdVanNan = ? AND tinhTrang = N'Hủy vấn nạn'";
		try {
			PreparedStatement pstm = connection.prepareStatement(delete);
			pstm.setString(1, idVanNan);

			result = pstm.executeUpdate();
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

		return result;
	}

	public static void xuLyNhanhNhat() {
		Connection connection = ConnectionSQL.getConnection();
		String xuLy = "SELECT TOP 5 WITH TIES * , DATEDIFF(day, ngayBatDau, ngayKetThuc) AS ThoiGianXuLy "
				+ "FROM TinhTrangXuLy WHERE tinhTrang LIKE N'%Đã xử lý%' " + "ORDER BY ThoiGianXuLy ASC";
		try {
			PreparedStatement pstm = connection.prepareStatement(xuLy);
			ResultSet rs = pstm.executeQuery();
			// Kiểm tra xem ResultSet có dữ liệu hay không
			if (!rs.isBeforeFirst()) {
				System.out.println("Chưa có vấn nạn nào đã được giải quyết.");
			} else {
				System.out.println("Những vấn nạn được xử lý nhanh nhất là: ");
				System.out.println("-----------------------------------");
				while (rs.next()) {
					String idVanNan = rs.getString("IdVanNan");
					int thoiGianXuLy = rs.getInt("ThoiGianXuLy");
					System.out.println("IdVanNan: " + idVanNan + ", ThoiGianXuLy: " + thoiGianXuLy + " ngày");
					System.out.println("-----------------------------------");
				}
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
	}

	public static void phanLoaiVanNan() {
		Connection connection = ConnectionSQL.getConnection();
		String phanLoai = "SELECT tt.idVanNan, vn.loaiVanNan, tt.tinhTrang "
				+ "FROM TinhTrangXuLy tt JOIN VanNan vn ON tt.idVanNan = vn.idVanNan "
				+ "GROUP BY tt.idVanNan, vn.loaiVanNan, tt.tinhTrang " + "ORDER BY tt.tinhTrang";

		try {
			PreparedStatement pstm = connection.prepareStatement(phanLoai);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String idVanNan = rs.getString("idVanNan");
				String loaiVanNan = rs.getString("loaiVanNan");
				String tinhTrang = rs.getString("tinhTrang");
				System.out
						.println("IdVanNan: " + idVanNan + ", LoaiVanNan: " + loaiVanNan + ", TinhTrang: " + tinhTrang);
			}
		} catch (SQLException e) {
			// Xử lý ngoại lệ
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// Xử lý ngoại lệ khi đóng kết nối
			e.printStackTrace();
		}
	}

	public static void hienThiTonDong() {
		Connection connection = ConnectionSQL.getConnection();
		String hienThi = "SELECT \r\n" + "    idVanNan, \r\n" + "    ngayBatDau, \r\n"
				+ "    DATEDIFF(day, ngayBatDau, GETDATE()) AS ThoiGianPending \r\n" + "FROM \r\n"
				+ "    TinhTrangXuLy \r\n" + "WHERE \r\n"
				+ "    tinhTrang IN (N'Chưa theo dõi', N'Đã xem xét', N'Đang xử lý') \r\n" + "   \r\n"
				+ "    AND DATEDIFF(day, ngayBatDau, GETDATE()) > 30;";
		try {
			PreparedStatement pstm = connection.prepareStatement(hienThi);
			ResultSet rs = pstm.executeQuery();
			System.out.println("Những vẫn nạn tồn đọng dài là :");
			System.out.println("-----------------------------------");
			while (rs.next()) {
				String idVanNan = rs.getString("idVanNan");
				Date ngayBatDau = rs.getDate("ngayBatDau");
				int thoiGianPeding = rs.getInt("ThoiGianPending");

				System.out.println("Mã vấn nạn: " + idVanNan + "Ngày bắt đầu vấn nạn: " + ngayBatDau
						+ " Thời gian tồn đọng: " + thoiGianPeding + " ngày");
				System.out.println("-----------------------------------");
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
	}

	public static void nhanVienXuatSac(int nam, int quy) {

		Connection connection = ConnectionSQL.getConnection();
		String nhanvien = "SELECT TOP 3 WITH TIES idNhanVienSo, COUNT(*) AS SoLuongVatNan"
				+ " FROM TinhTrangXuLy  WHERE YEAR(ngayKetThuc) = ? AND DATEPART(QUARTER, ngayKetThuc) = ?"
				+ " GROUP BY idNhanVienSo ORDER BY SoLuongVatNan DESC";
		try {
			PreparedStatement pstm = connection.prepareStatement(nhanvien);
			pstm.setInt(1, nam);
			pstm.setInt(2, quy);

			String nhanvien1 = "SELECT TOP 3 WITH TIES idNhanVienSo, COUNT(*) AS SoLuongVatNan " + "FROM TinhTrangXuLy "
					+ "WHERE YEAR(ngayKetThuc) = ? AND DATEPART(QUARTER, ngayKetThuc) = ? " + "GROUP BY idNhanVienSo "
					+ "ORDER BY SoLuongVatNan DESC";

			try (PreparedStatement pstm1 = connection.prepareStatement(nhanvien1); ResultSet rs = pstm.executeQuery()) {
				System.out.printf("Top nhân viên xuất sắc của quý %d năm %d \n", quy, nam);
				while (rs.next()) {
					String idNhanVien = rs.getString("idNhanVienSo");
					int soVanNan = rs.getInt("SoLuongVatNan");

					System.out.println("\nMã nhân viên: " + idNhanVien + "Số lượng vấn nạn giải quyết: " + soVanNan);
					System.out.println("--------------------------------------");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static String selectIDNV(Connection connection, String aCount) {
		String sql="select idNhanVienSo from NhanVienSo where aCount =?";
		
		try {
			
			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1,aCount);
			ResultSet rs = ptsm.executeQuery();
			while(rs.next()) {
			return	rs.getString("idNhanVienSo");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aCount;
	}

}
