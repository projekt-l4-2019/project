package pl.example.repository;

import org.springframework.data.repository.CrudRepository;
import pl.example.models.AdminEntity;

public interface AdminRepository extends CrudRepository<AdminEntity, Long> {
}
