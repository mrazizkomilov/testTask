package com.task.test.repository;

import com.task.test.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer>, JpaSpecificationExecutor {
}
