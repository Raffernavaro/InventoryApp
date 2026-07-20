package inventoryapp.app.model;

public class Barang {
    private int id;
    private String kode;
    private String nama;
    private int idKategori;
    private String satuan;
    private int stok;

    public Barang() {
    }

    public Barang(int id, String kode, String nama, int idKategori, String satuan, int stok) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.idKategori = idKategori;
        this.satuan = satuan;
        this.stok = stok;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }

    public String getSatuan() { return satuan; }
    public void setSatuan(String satuan) { this.satuan = satuan; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}
