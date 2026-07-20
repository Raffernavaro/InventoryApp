package inventoryapp.app.dao;

import inventoryapp.app.model.Pegawai;
import java.util.List;

public interface PegawaiDAO {
    void insert(Pegawai pegawai);
    void update(Pegawai pegawai);
    void delete(int id);
    List<Pegawai> getAll();
    Pegawai getById(int id);
    Pegawai login(String username, String password);
}
