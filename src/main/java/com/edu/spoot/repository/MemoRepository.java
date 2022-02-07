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

    @Query(value="select m from Memo m where m.mno > :mno", //@Query 어노테이션+파라메터+pageable을 이용한 쿼리
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

/*    @Query(value = "select m.mno, m.memoText, current_date Memo m where m.mno > :mno",  //Object[] 리턴
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

    @Query(value = "select * from Memo where mno > 20", nativeQuery = true)  //Native Sql 처리
    List<Object[]> getNativeResult();*/



}
