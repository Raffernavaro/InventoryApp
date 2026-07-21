package inventoryapp.app.model;

public class Kategori {
    private int id;
    private String nama;
    private int noRak;

    public Kategori() {
    }

    public Kategori(int id, String nama, int noRak) {
        this.id = id;
        this.nama = nama;
        this.noRak = noRak;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNoRak() {
        return noRak;
    }

    public void setNoRak(int noRak) {
        this.noRak = noRak;
    }
    
    @Override
    public String toString() {
        return nama; // Useful for rendering in JComboBox later
    }
}
