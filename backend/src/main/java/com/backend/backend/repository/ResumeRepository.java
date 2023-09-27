package com.backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
