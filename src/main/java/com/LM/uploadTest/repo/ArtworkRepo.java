package com.LM.uploadTest.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LM.uploadTest.entity.Artwork;

public interface ArtworkRepo extends JpaRepository<Artwork, Long> {

}
