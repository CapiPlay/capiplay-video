package br.senai.sc.capiplayvideo.video.repository;

import br.senai.sc.capiplayvideo.categoria.model.entity.Categoria;
import br.senai.sc.capiplayvideo.video.model.entity.Video;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.model.projection.VideoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    Page<VideoMiniaturaProjection> findAllBy(Pageable pageable);

    Page<VideoMiniaturaProjection> findAllByCategoria(Categoria categoria, Pageable pageable);

    Optional<VideoProjection> findByUuid(String uuid);

    List<Video> findAllByShortsIsTrue();

    List<VideoMiniaturaProjection> findByPublicacaoAfter(LocalDate data);

    // ver o bagulho de somar pontuacao com o totalScore
    @Query(value = "SELECT *, MATCH(video.titulo) AGAINST(CONCAT('*', :searchTerm, '*') IN BOOLEAN MODE) * 3 +" +
            " (SELECT MAX(MATCH(categoria.categoria_string) AGAINST(CONCAT('*', :searchTerm, '*') IN BOOLEAN MODE) * 2)" +
            " FROM categoria WHERE video.categoria_id = categoria.id) +" +
            " (SELECT MAX(MATCH(tag.tag) AGAINST(CONCAT('*', :searchTerm, '*') IN BOOLEAN MODE)) " +
            "FROM tag, video_tags WHERE video_tags.tags_tag = tag.tag and video_tags.video_uuid = video.uuid)" +
            " TotalScore FROM video GROUP BY video.titulo HAVING TotalScore > 0 ORDER BY TotalScore DESC;",
            nativeQuery = true)
    List<VideoMiniaturaProjection> searchBy(String searchTerm);
}
