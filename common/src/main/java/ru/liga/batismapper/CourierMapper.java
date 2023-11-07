package ru.liga.batismapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import ru.liga.model.Courier;
import ru.liga.model.CourierStatus;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CourierMapper {
    @Select("select * from couriers")
    List<Courier> findAll();

    @Select("select * from couriers where id = #{id}")
    Optional<Courier> findCourierById(Long id);

    @Select("select * from couriers where status = #{status}")
    List<Courier> findByStatus(CourierStatus status);

    @Update("update couriers set status = #{status} where id = #{id}")
    void updateStatus(Long id, CourierStatus status);
}
