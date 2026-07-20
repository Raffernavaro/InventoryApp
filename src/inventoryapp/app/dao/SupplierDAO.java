package inventoryapp.app.dao;

import inventoryapp.app.model.Supplier;
import java.util.List;

public interface SupplierDAO {
    void insert(Supplier supplier);
    void update(Supplier supplier);
    void delete(int id);
    List<Supplier> getAll();
    Supplier getById(int id);
}
