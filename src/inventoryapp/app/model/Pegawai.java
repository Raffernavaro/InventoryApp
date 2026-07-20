package inventoryapp.app.model;

public class Pegawai {
    private int id;
    private String nama;
    private String username;
    private String password;
    private String status; // aktif, nonaktif

    public Pegawai() {
    }

    public Pegawai(int id, String nama, String username, String password, String status) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return nama;
    }
}
