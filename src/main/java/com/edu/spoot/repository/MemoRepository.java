package com.edu.spoot.repository;

import com.edu.spoot.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    public List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    public Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    public void deleteMemoByMnoLessThan(Long num);

    @Query("select m from Memo m order by m.mno desc ")   //@Query 어노테이션을 이용한 쿼리
    List<Memo> getListDesc();

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno") //@Query 어노테이션+파라메터를 이용한 쿼리
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText );

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}") //@Query 어노테이션+파라메터를 이용한 쿼리2
    int updateMemoTextBean(@Param("param") Memo memo );
}
