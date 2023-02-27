package tech.devinhouse.pharmacymanagement.dataprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.pharmacymanagement.dataprovider.entity.MedicamentoEntity;

@Repository
public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, Long> {
}
