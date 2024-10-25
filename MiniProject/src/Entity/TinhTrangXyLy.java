package Entity;

import java.util.Date;

public class TinhTrangXyLy {

    private String idVanNan;
    private String idNhanVienSo;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String tinhTrang;
    
    
    @Override
    public String toString() {
        return " [idVanNan=" + idVanNan + ", idNhanVienSo=" + idNhanVienSo + ", ngayBatDau="
                + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + ", tinhTrang=" + tinhTrang + "]";
    }

    public TinhTrangXyLy() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public TinhTrangXyLy(String idVanNan, String idNhanVienSo, Date ngayBatDau, Date ngayKetThuc, String tinhTrang) {
        super();
        this.idVanNan = idVanNan;
        this.idNhanVienSo = idNhanVienSo;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.tinhTrang = tinhTrang;
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
    /**
     * @return the idNhanVienSo
     */
    public String getIdNhanVienSo() {
        return idNhanVienSo;
    }
    /**
     * @param idNhanVienSo the idNhanVienSo to set
     */
    public void setIdNhanVienSo(String idNhanVienSo) {
        this.idNhanVienSo = idNhanVienSo;
    }
    /**
     * @return the ngayBatDau
     */
    public Date getNgayBatDau() {
        return ngayBatDau;
    }
    /**
     * @param ngayBatDau the ngayBatDau to set
     */
    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    /**
     * @return the ngayKetThuc
     */
    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }
    /**
     * @param ngayKetThuc the ngayKetThuc to set
     */
    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    /**
     * @return the tinhTrang
     */
    public String getTinhTrang() {
        return tinhTrang;
    }
    /**
     * @param tinhTrang the tinhTrang to set
     */
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
    
}