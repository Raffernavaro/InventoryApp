package inventoryapp.app.model;

public class BarangMasukDetail {
    private int id;
    private int idBarangMasuk;
    private int idBarang;
    private int jumlah;

    public BarangMasukDetail() {}

    public BarangMasukDetail(int id, int idBarangMasuk, int idBarang, int jumlah) {
        this.id = id;
        this.idBarangMasuk = idBarangMasuk;
        this.idBarang = idBarang;
        this.jumlah = jumlah;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdBarangMasuk() { return idBarangMasuk; }
    public void setIdBarangMasuk(int idBarangMasuk) { this.idBarangMasuk = idBarangMasuk; }
    public int getIdBarang() { return idBarang; }
    public void setIdBarang(int idBarang) { this.idBarang = idBarang; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
}
