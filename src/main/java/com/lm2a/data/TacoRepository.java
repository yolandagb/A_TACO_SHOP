package com.lm2a.data;

import org.springframework.data.repository.CrudRepository;

import com.lm2a.model.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {

}
