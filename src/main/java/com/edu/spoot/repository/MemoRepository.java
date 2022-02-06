package com.edu.spoot.repository;

import com.edu.spoot.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    public List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    public Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    public void deleteMemoByMnoLessThan(Long num);
}
