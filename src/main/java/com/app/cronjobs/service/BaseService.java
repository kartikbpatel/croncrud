package com.app.cronjobs.service;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseService<T, ID> {

    private JpaRepository<T, ID> repository;

    public BaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

}
