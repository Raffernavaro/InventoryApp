package inventoryapp.app.model;

import java.sql.Timestamp;

public class RiwayatTransaksi {
    private Timestamp tanggal;
    private String jenisTransaksi;
    private String kodeBarang;
    private String namaBarang;
    private String kategori;
    private String satuan;
    private int jumlah;
    private String relasi;

    public RiwayatTransaksi() {
    }

    public RiwayatTransaksi(Timestamp tanggal, String jenisTransaksi, String kodeBarang, String namaBarang, String kategori, String satuan, int jumlah, String relasi) {
        this.tanggal = tanggal;
        this.jenisTransaksi = jenisTransaksi;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.satuan = satuan;
        this.jumlah = jumlah;
        this.relasi = relasi;
    }

    public Timestamp getTanggal() {
        return tanggal;
    }

    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getRelasi() {
        return relasi;
    }

    public void setRelasi(String relasi) {
        this.relasi = relasi;
    }
}
