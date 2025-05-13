package com.bookstore.DTO;

public class DocGiaDTO {
    private String maDocGia;
    private String TenDocGia;
    private String diachi;
    private String SDT;
    private String TrangThai;
    private String Status;

    public DocGiaDTO(String maDocGia, String TenDocGia, String diachi, String SDT, String TrangThai, String Status) {
        this.maDocGia = maDocGia;
        this.TenDocGia = TenDocGia;
        this.diachi = diachi;
        this.SDT = SDT;
        this.TrangThai = TrangThai;
        this.Status = Status;
    }

    public String getMaDocGia() {
        return maDocGia;
    }

    public String getTenDocGia() {
        return TenDocGia;
    }

    public String getdiachi() {
        return diachi;
    }

    public String getSDT() {
        return SDT;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public String getStatus() {
        return Status;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    public void setTenDocGia(String TenDocGia) {
        this.TenDocGia = TenDocGia;
    }

    public void setdiaChi(String diachi) {
        this.diachi = diachi;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public void settrangthai(String trangthai) {
        this.TrangThai = trangthai;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    @Override
    public String toString() {
        return "DocGiaDTO{" +
                "maDocGia='" + maDocGia + '\'' +
                ", TenDocGia='" + TenDocGia + '\'' +
                ", diachi='" + diachi + '\'' +
                ", SDT='" + SDT + '\'' +
                ", TrangThai='" + TrangThai + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}