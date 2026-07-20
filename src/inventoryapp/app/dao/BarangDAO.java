package inventoryapp.app.dao;

import inventoryapp.app.model.Barang;
import java.util.List;

public interface BarangDAO {
    void insert(Barang barang);
    void update(Barang barang);
    void delete(int id);
    List<Barang> getAll();
    Barang getById(int id);
}
