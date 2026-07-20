package inventoryapp.app.dao;

import inventoryapp.app.model.Pelanggan;
import java.util.List;

public interface PelangganDAO {
    void insert(Pelanggan pelanggan);
    void update(Pelanggan pelanggan);
    void delete(int id);
    List<Pelanggan> getAll();
    Pelanggan getById(int id);
}
