package inventoryapp.app.model;

public class Supplier {
    private int id;
    private String kode;
    private String nama;
    private String alamat;
    private String noTelp;

    public Supplier() {
    }

    public Supplier(int id, String kode, String nama, String alamat, String noTelp) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
    
    @Override
    public String toString() {
        return nama;
    }
}
