package texnobazar.texnobazar.mapper;

import org.mapstruct.Mapper;
import texnobazar.texnobazar.dto.CategoryDto;
import texnobazar.texnobazar.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, CategoryDto> {
}
