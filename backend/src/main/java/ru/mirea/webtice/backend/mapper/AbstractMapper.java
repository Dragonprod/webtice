package ru.mirea.webtice.backend.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Abstract mapper class
 * generic @param E - entity Class
 * generic @param D - dto Class
 */
public abstract class AbstractMapper<E, D> {
    public ModelMapper mapper;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    /**
     *
     * @param entityClass java class of Entity type ex. Page.class
     * @param dtoClass java class of DTO type ex. PageDto.class
     * @param mapper object of ModelMapper
     */
    public AbstractMapper(Class<E> entityClass, Class<D> dtoClass, ModelMapper mapper) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.mapper = mapper;
    }

    public E toEntity(D dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, entityClass);
    }

    public List<E> toEntity(Collection<D> dtos) {
        return Objects.isNull(dtos) ? null : dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<D> toDto(Collection<E> entities) {
        return Objects.isNull(entities) ? null : entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public D toDto(E entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, dtoClass);
    }

    public Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFieldsToDto(source, destination);
            return context.getDestination();
        };
    }

    public Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFieldsToEntity(source, destination);
            return context.getDestination();
        };
    }

    /**
     * Override this method if you need specific logic on converting (different fields in classes or smth like that)
     * and add post construct
     * ex:
     * <code>
     *     \@PostConstruct
     *     public void setupMapper() {
     *         mapper.createTypeMap(Page.class, PageDto.class)
     *                 .addMappings(m -> m.skip(PageDto::setProject)).setPostConverter(toDtoConverter());
     *     }
     *
     *     public void mapSpecificFieldsToDto(Page source, PageDto destination) {
     *         Project project = getProject(source);
     *         ProjectDtoShort dto = project == null ? null : mapper.map(project, ProjectDtoShort.class);
     *         destination.setProject(dto);
     *     }
     * </code>
     * @param source - Entity object
     * @param destination - DTO object
     */
    public void mapSpecificFieldsToDto(E source, D destination) {

    }

    /**
     * Override this method if you need specific logic on converting (different fields in classes or smth like that)
     * ex:
     * <code>
     *     \@PostConstruct
     *     public void setupMapper() {
     *         mapper.createTypeMap(PageDto.class, Page.class)
     *                 .addMappings(m -> m.skip(Page::setProject)).setPostConverter(toEntityConverter());
     *     }
     *     public void mapSpecificFieldsToEntity(PageDto source, Page destination) {
     *         destination.setProject(projectRepository.findById(source.getProject().getId()).orElse(null));
     *     }
     * </code>
     * @param source - DTO object
     * @param destination - Entity object
     */
    public void mapSpecificFieldsToEntity(D source, E destination) {

    }
}
