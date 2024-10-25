package Entity;

import java.util.Date;

public class VoteMucDo {
	private int diem;
	private Date ngayVote;
	private String idNguoiDan;
	private String idVanNan;

	public VoteMucDo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VoteMucDo(int diem, Date ngayVote, String idNguoidan, String idVanNan) {
		super();
		this.diem = diem;
		this.ngayVote = ngayVote;
		this.idNguoiDan = idNguoidan;
		this.idVanNan = idVanNan;
	}

	@Override
	public String toString() {
		return " [diem=" + diem + ", ngayVote=" + ngayVote + ", idNguoidan=" + idNguoiDan + ", idVanNan="
				+ idVanNan + "]";
	}

	/**
	 * @return the diem
	 */
	public int getDiem() {
		return diem;
	}

	/**
	 * @param diem the diem to set
	 */
	public void setDiem(int diem) {
		this.diem = diem;
	}

	/**
	 * @return the ngayVote
	 */
	public Date getNgayVote() {
		return ngayVote;
	}

	/**
	 * @param ngayVote the ngayVote to set
	 */
	public void setNgayVote(Date ngayVote) {
		this.ngayVote = ngayVote;
	}

	/**
	 * @return the idNguoidan
	 */
	public String getIdNguoidan() {
		return idNguoiDan;
	}

	/**
	 * @param idNguoidan the idNguoidan to set
	 */
	public void setIdNguoidan(String idNguoidan) {
		this.idNguoiDan = idNguoidan;
	}

	/**
	 * @return the idVanNan
	 */
	public String getIdVanNan() {
		return idVanNan;
	}

	/**
	 * @param idVanNan the idVanNan to set
	 */
	public void setIdVanNan(String idVanNan) {
		this.idVanNan = idVanNan;
	}

}
