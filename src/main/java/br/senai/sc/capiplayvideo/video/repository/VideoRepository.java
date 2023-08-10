package br.senai.sc.capiplayvideo.video.repository;

import br.senai.sc.capiplayvideo.categoria.model.entity.Categoria;
import br.senai.sc.capiplayvideo.video.model.entity.Video;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.model.projection.VideoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    List<VideoMiniaturaProjection> findAllBy(Pageable pageable);

    List<VideoMiniaturaProjection> findAllByCategoria_categoriaString(String categoria, Pageable pageable);

    Optional<VideoProjection> findByUuid(String uuid);

    List<Video> findAllByShortsIsTrue();

    List<VideoMiniaturaProjection> findByPublicacaoAfter(LocalDate data);

    @Query(value = "SELECT *, MATCH(video.titulo) AGAINST(CONCAT('*', :searchTerm, '*') IN BOOLEAN MODE) * 3000 +" +
            " (SELECT MAX(MATCH(categoria.categoria_string) AGAINST(CONCAT('*', :searchTerm, '*') IN BOOLEAN MODE) * 2000)" +
            " FROM categoria WHERE video.categoria_id = categoria.id) +" +
            " (SELECT MAX(MATCH(tag.tag) AGAINST(CONCAT('*', :searchTerm, '*') IN BOOLEAN MODE) * 1000) " +
            "FROM tag, video_tags WHERE video_tags.tags_tag = tag.tag and video_tags.video_uuid = video.uuid) + (video.pontuacao * 4)" +
            " TotalScore FROM video GROUP BY video.titulo;",
            nativeQuery = true)
    List<VideoMiniaturaProjection> searchBy(String searchTerm);

    @Query(value = "SELECT *, MATCH(video.titulo) AGAINST(CONCAT('*', :searchTerm, '*') IN BOOLEAN MODE) * 3 +" +
            "(SELECT MAX(MATCH(categoria.categoria_string) AGAINST(CONCAT('*', :searchTerm , '*') IN BOOLEAN MODE) * 2)" +
            "FROM categoria WHERE video.categoria_id = categoria.id) +" +
            "(SELECT MAX(MATCH(tag.tag) AGAINST(CONCAT('*', :searchTerm, '*') IN BOOLEAN MODE)) " +
            "FROM tag, video_tags WHERE video_tags.tags_tag = tag.tag and video_tags.video_uuid = video.uuid)" +
            "TotalScore FROM video " +
            "WHERE (:filtroDia = FALSE OR DATE(video.publicacao) = CURRENT_DATE() AND DATE(video.publicacao) >= DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY) AND MONTH(video.publicacao) = MONTH(CURRENT_DATE()) AND YEAR(video.publicacao) = YEAR(CURRENT_DATE())) " +
            "AND (:filtroSemana = FALSE OR DATE(video.publicacao) >= DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY) AND MONTH(video.publicacao) = MONTH(CURRENT_DATE()) AND YEAR(video.publicacao) = YEAR(CURRENT_DATE())) " +
            "AND (:filtroMes = FALSE OR MONTH(video.publicacao) = MONTH(CURRENT_DATE()) AND YEAR(video.publicacao) = YEAR(CURRENT_DATE())) " +
            "AND (:filtroAno = FALSE OR YEAR(video.publicacao) = YEAR(CURRENT_DATE())) " +
            "AND (:filtroMenosDe5Min = FALSE OR video.duracao < 300) " +
            "AND (:filtroEntre5E20Min = FALSE OR (video.duracao >= 300 AND video.duracao <= 1200)) " +
            "AND (:filtroMaisDe20Min = FALSE OR video.duracao > 1200) " +
            "AND (:filtroVideo = FALSE OR video.shorts = '0') " +
            "AND (:filtroShorts = FALSE OR video.shorts = '1') " +
            "GROUP BY video.titulo HAVING TotalScore > 0 ORDER BY TotalScore DESC;",
            nativeQuery = true)
    List<VideoMiniaturaProjection> searchByFiltro(
            @Param("searchTerm") String searchTerm,
            @Param("filtroDia") Boolean filtroDia,
            @Param("filtroSemana") Boolean filtroSemana,
            @Param("filtroMes") Boolean filtroMes,
            @Param("filtroAno") Boolean filtroAno,
            @Param("filtroMenosDe5Min") Boolean filtroMenosDe5Min,
            @Param("filtroEntre5E20Min") Boolean filtroEntre5E20Min,
            @Param("filtroMaisDe20Min") Boolean filtroMaisDe20Min,
            @Param("filtroVideo") Boolean filtroVideo,
            @Param("filtroShorts") Boolean filtroShorts,
            Pageable pageable);
}
