package tech.devinhouse.pharmacymanagement.dataprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.pharmacymanagement.dataprovider.model.MedicamentoModel;

@Repository
public interface MedicamentoRepository extends JpaRepository<MedicamentoModel, Long> {
}
