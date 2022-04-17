package ru.otus.core.repository;

import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DataTemplateHibernate<T> implements DataTemplate<T> {

    private final Class<T> clazz;

    public DataTemplateHibernate(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Optional<T> findById(Session session, long id) {
        var entityGraph = getEntityGraph(session);
        var query = session.createQuery(String.format("from %s where id = " + id, clazz.getSimpleName()), clazz);
        query.applyGraph(entityGraph, GraphSemantic.FETCH);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<T> findByEntityField(Session session, String entityFieldName, Object entityFieldValue) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var entityGraph = getEntityGraph(session);
        var criteriaQuery = criteriaBuilder.createQuery(clazz);
        var root = criteriaQuery.from(clazz);
        criteriaQuery.select(root)
                     .where(criteriaBuilder.equal(root.get(entityFieldName), entityFieldValue));

        var query = session.createQuery(criteriaQuery);
        query.applyGraph(entityGraph, GraphSemantic.FETCH);
        return query.getResultList();
    }

    @Override
    public List<T> findAll(Session session) {
        var entityGraph = getEntityGraph(session);
        var query = session.createQuery(String.format("from %s", clazz.getSimpleName()), clazz);
        query.applyGraph(entityGraph, GraphSemantic.FETCH);
        return query.getResultList();
    }

    @Override
    public void insert(Session session, T object) {
        session.persist(object);
    }

    @Override
    public void update(Session session, T object) {
        session.merge(object);
    }

    private RootGraph<T> getEntityGraph(Session session) {
        var entityGraph = session.createEntityGraph(clazz);
        var fields = getFields();
        entityGraph.addAttributeNodes(getFieldsName(fields));
        return entityGraph;
    }

    private Field[] getFields() {
        return Arrays.stream(clazz.getDeclaredFields()).filter(getFieldByAnnotations()).toArray(Field[]::new);
    }

    private Predicate<Field> getFieldByAnnotations() {
        return field -> field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(OneToOne.class);
    }

    private String[] getFieldsName(Field[] fields) {
        return Arrays.stream(fields).map(Field::getName).toArray(String[]::new);
    }
}
