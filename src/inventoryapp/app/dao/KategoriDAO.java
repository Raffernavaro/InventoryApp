package inventoryapp.app.dao;

import inventoryapp.app.model.Kategori;
import java.util.List;

public interface KategoriDAO {
    void insert(Kategori kategori);
    void update(Kategori kategori);
    void delete(int id);
    List<Kategori> getAll();
    Kategori getById(int id);
}
