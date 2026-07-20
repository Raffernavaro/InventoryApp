package inventoryapp.app.model;

public class BarangKeluarDetail {
    private int id;
    private int idBarangKeluar;
    private int idBarang;
    private int jumlah;

    public BarangKeluarDetail() {}

    public BarangKeluarDetail(int id, int idBarangKeluar, int idBarang, int jumlah) {
        this.id = id;
        this.idBarangKeluar = idBarangKeluar;
        this.idBarang = idBarang;
        this.jumlah = jumlah;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdBarangKeluar() { return idBarangKeluar; }
    public void setIdBarangKeluar(int idBarangKeluar) { this.idBarangKeluar = idBarangKeluar; }
    public int getIdBarang() { return idBarang; }
    public void setIdBarang(int idBarang) { this.idBarang = idBarang; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
}
