package com.edu.spoot.repository;

import com.edu.spoot.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Commit;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test    //
    public void testClass() {
        System.out.println("111111111 :" + memoRepository.getClass().getName());
    }

    @Test  // 입력
    public void TestInsertDummies() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test   //조회
    @Transactional
    public void testSelect() {
        Long mno = 99L;

        Optional<Memo> result = Optional.of(memoRepository.getById(mno));
        System.out.println("===========================================");
        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test  //수정
    public void testUpdate() {
        Memo memo = Memo.builder().mno(99L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
    }

    @Test  //삭제
    public void testDelete(){
        Long mno = 99L;
        memoRepository.deleteById(mno);
    }

    @Test  //페이징
    public void testPageDefault() {
        Pageable pageable = (Pageable) PageRequest.of(3, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println("result : "+result);
        System.out.println("==============================================");
        System.out.println("Total Page :"+result.getTotalPages());  //전체페이지
        System.out.println("Total Count :"+result.getTotalElements());  //전체개수
        System.out.println("Page Number :"+result.getNumber());  //현재페이지 번호
        System.out.println("Page Size :"+result.getSize());  //페이지당 데이터갯수
        System.out.println("Has Next Page :"+result.hasNext());  //다음페이지 존재여부
        System.out.println("First Page :"+result.isFirst());  //시작페이지(0) 여부
        System.out.println("==============================================");
        for(Memo memo : result.getContent()){  // 내용
            System.out.println("content : " + memo);
        }
    }

    @Test  //정렬
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();   //sort 설정
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);  // sort1, sort2 연결
//        Pageable pageable = PageRequest.of(0, 10, sort1);
        Pageable pageable = PageRequest.of(0, 10, sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {   //내용
            System.out.println(memo);
        });
    }

    @Test  // 쿼리메소드 사용한 쿼리
    public void testQueryMethod(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        for(Memo memo : list) {
            System.out.println("result :" + memo);
        }
    }

    @Test  //쿼리메소드 + Pageable 결합
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(memo -> System.out.println(memo));
    }

    @Test  // 쿼리메소드 삭제
    @Transactional
    @Commit
    public void testDeleteQueryMethod(){
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    @Test  // @Query 어노테이션을 이용한 쿼리문
    public void testQueryAnnotation(){
        List<Memo> result = memoRepository.getListDesc();
        System.out.println(result);

    }

    @Test    //@Query 어노테이션+파라메터를 이용한 쿼리문
    public void testQueryAnnotationParameter(){
        int i = memoRepository.updateMemoText(12L,"1234kkk");
    }

//    @Test    //@Query 어노테이션+파라메터를 이용한 쿼리문2
//    public void testQueryAnnotationParameterBean(){
//        List<Memo> memo = (List<Memo>) memoRepository.getById(13L);
//        System.out.println(memo);
//        int i = memoRepository.updateMemoTextBean((Memo) memo);
//    }

}
