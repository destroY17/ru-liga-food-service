package ru.liga.batismapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import ru.liga.model.Courier;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CourierMapper {
    @Select("select * from couriers")
    List<Courier> findAll();
    @Select("select * from couriers where id = #{id}")
    Optional<Courier> findCourierById(Long id);
}
