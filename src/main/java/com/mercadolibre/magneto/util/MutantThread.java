package com.mercadolibre.magneto.util;

import com.mercadolibre.magneto.model.Mutant;
import com.mercadolibre.magneto.repositories.MutantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dgallego on 26/10/2017.
 */
public class MutantThread implements Runnable{

    public static final Logger logger = LoggerFactory.getLogger(MutantThread.class);

    private Mutant mutant;
    private MutantRepository mutantRepository;

    public MutantThread(Mutant mutant, MutantRepository mutantRepository){
        this.mutantRepository = mutantRepository;
        this.mutant = mutant;
    }

    @Override
    public void run() {
        mutantRepository.save(mutant);
        logger.warn("termino el hilo y grabo correctamente");

    }
}
