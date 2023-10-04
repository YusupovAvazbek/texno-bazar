package texnobazar.texnobazar.mapper;

public interface BaseMapper <E,D>{
    D toDto(E e);
    E toEntity(D d);
}
