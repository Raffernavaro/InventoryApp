package inventoryapp.app.model;

import java.sql.Date;
import java.util.List;

public class BarangKeluar {
    private int id;
    private Date tgl;
    private int idPetugas;
    private int idPelanggan;
    
    private List<BarangKeluarDetail> details;

    public BarangKeluar() {}

    public BarangKeluar(int id, Date tgl, int idPetugas, int idPelanggan) {
        this.id = id;
        this.tgl = tgl;
        this.idPetugas = idPetugas;
        this.idPelanggan = idPelanggan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getTgl() { return tgl; }
    public void setTgl(Date tgl) { this.tgl = tgl; }
    public int getIdPetugas() { return idPetugas; }
    public void setIdPetugas(int idPetugas) { this.idPetugas = idPetugas; }
    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }
    public List<BarangKeluarDetail> getDetails() { return details; }
    public void setDetails(List<BarangKeluarDetail> details) { this.details = details; }
}
