package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.ResistomeBaseModel;

public interface ResistomeBaseRepository {

    ResistomeBaseModel findByName(String name);
}
