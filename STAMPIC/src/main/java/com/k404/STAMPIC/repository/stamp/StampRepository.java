package com.k404.STAMPIC.repository.stamp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.k404.STAMPIC.domain.stamp.Stamp;

@Repository
public interface StampRepository  extends JpaRepository<Stamp, Long> {
	long countByUserId(Long userId); // 해당 유저ID로 STAMP 찍힌 갯수 조회
	List<Stamp> findAttrIdByUserId(Long userId); // 해당 유저ID로 스탬프가 찍힌 지역들 조회
}
