package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FileUploadEntity;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUploadEntity, Long> {

}
