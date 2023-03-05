package tech.devinhouse.pharmacymanagement.dataprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.pharmacymanagement.dataprovider.model.FarmaciaModel;

@Repository
public interface FarmaciaRepository extends JpaRepository<FarmaciaModel, Long> {
}
