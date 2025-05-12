package com.bookstore.DTO;

public class DocGiaDTO {
    private String MaDocGia;
    private String TenDocGia;
    private String DiaChi;
    private String SDT;
    private int TrangThai;
    private int status;

    public DocGiaDTO(String MaDocGia, String TenDocGia, String DiaChi, String SDT, int TrangThai, int status) {
        this.MaDocGia = MaDocGia;
        this.TenDocGia = TenDocGia;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.TrangThai = TrangThai;
        this.status = status;
    }

    public String getMaDocGia() {
        return MaDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        MaDocGia = maDocGia;
    }

    public String getTenDocGia() {
        return TenDocGia;
    }

    public void setTenDocGia(String tenDocGia) {
        TenDocGia = tenDocGia;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sDT) {
        SDT = sDT;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
