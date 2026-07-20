package inventoryapp.app.model;

import java.sql.Date;
import java.util.List;

public class BarangMasuk {
    private int id;
    private Date tgl;
    private int idPetugas;
    private int idSupplier;
    
    private List<BarangMasukDetail> details;

    public BarangMasuk() {}

    public BarangMasuk(int id, Date tgl, int idPetugas, int idSupplier) {
        this.id = id;
        this.tgl = tgl;
        this.idPetugas = idPetugas;
        this.idSupplier = idSupplier;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getTgl() { return tgl; }
    public void setTgl(Date tgl) { this.tgl = tgl; }
    public int getIdPetugas() { return idPetugas; }
    public void setIdPetugas(int idPetugas) { this.idPetugas = idPetugas; }
    public int getIdSupplier() { return idSupplier; }
    public void setIdSupplier(int idSupplier) { this.idSupplier = idSupplier; }
    public List<BarangMasukDetail> getDetails() { return details; }
    public void setDetails(List<BarangMasukDetail> details) { this.details = details; }
}
