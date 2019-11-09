package org.springdoc.demo.app2.repository;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.*;

@NoRepositoryBean
public abstract class HashMapRepository<T, ID> implements CrudRepository<T, ID> {

    Map<ID, T> entities = new HashMap<>();

    abstract <S extends T> ID getEntityId(S entity);

    private final BeanWrapper entityBeanInfo;

    public HashMapRepository(Class<T> clazz) {
        entityBeanInfo = new BeanWrapperImpl(clazz);
    }

    @Override
    public <S extends T> S save(S entity) {
        Assert.notNull(entity, "entity cannot be null");
        Assert.notNull(getEntityId(entity), "entity ID cannot be null");
        entities.put(getEntityId(entity), entity);
        return entity;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities, "entities cannot be null");
        List<S> result = new ArrayList<>();
        entities.forEach(entity -> result.add(save(entity)));
        return result;
    }

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    public List<T> findAll(Pageable pageable) {
        final List<T> result;
        final Sort sort = pageable.getSort();
        if (sort == null) {
            Comparator<T> comp = new Comparator<T>() {
                @Override
                public int compare(T t, T t1) {
                    int result = 0;
                    for (Sort.Order o : sort) {
                        final String prop = o.getProperty();
                        PropertyDescriptor propDesc = entityBeanInfo.getPropertyDescriptor(prop);
                        result = ((Comparable<T>) propDesc.createPropertyEditor(t).getValue())
                                .compareTo((T) propDesc.createPropertyEditor(t1).getValue());
                        if (o.isDescending()) {
                            result = -result;
                        }
                        if (result != 0) break;
                    }
                    return result;
                }
            };
            Set<T> set = new TreeSet<>(comp);
            set.addAll(entities.values());
            result = Collections.unmodifiableList(new ArrayList<>(set));
        } else {
            result = Collections.unmodifiableList(new ArrayList<>(entities.values()));
        }
        return result;
    }

    @Override
    public long count() {
        return entities.keySet().size();
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "entity cannot be null");
        deleteById(getEntityId(entity));
    }

    @Override
    public void deleteAll(Iterable<? extends T> entitiesToDelete) {
        Assert.notNull(entitiesToDelete, "entities cannot be null");
        entitiesToDelete.forEach(entity -> entities.remove(getEntityId(entity)));
    }

    @Override
    public void deleteAll() {
        entities.clear();
    }

    @Override
    public void deleteById(ID id) {
        Assert.notNull(id, "Id cannot be null");
        entities.remove(id);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        Assert.notNull(ids, "Ids cannot be null");
        List<T> result = new ArrayList<>();
        ids.forEach(id -> findById(id).ifPresent(result::add));
        return result;
    }

    @Override
    public boolean existsById(ID id) {
        Assert.notNull(id, "Id cannot be null");
        return entities.keySet().contains(id);
    }

    public T findOne(ID id) {
        Assert.notNull(id, "Id cannot be null");
        return entities.get(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(findOne(id));
    }
}
