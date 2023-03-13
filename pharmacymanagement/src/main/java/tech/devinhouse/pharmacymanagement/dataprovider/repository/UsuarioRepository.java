package tech.devinhouse.pharmacymanagement.dataprovider.repository;

import org.springframework.data.jpa.repository.Query;
import tech.devinhouse.pharmacymanagement.dataprovider.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    @Query("SELECT u from usuario u where u.email=?1")
    UsuarioModel findUserByLogin(String login);
}
