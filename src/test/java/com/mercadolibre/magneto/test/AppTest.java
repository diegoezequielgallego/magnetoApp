package com.mercadolibre.magneto.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.mercadolibre.magneto.SpringBootCRUDApp;
import com.mercadolibre.magneto.configuration.JpaConfiguration;
import com.mercadolibre.magneto.dto.CountDTO;
import com.mercadolibre.magneto.repositories.MutantRepository;
import com.mercadolibre.magneto.service.MutantService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ SpringBootCRUDApp.class, JpaConfiguration.class })
public class AppTest {
	
	@Autowired
	MutantService mutantService;

	@Autowired
	MutantRepository mutantRepository;


	@Test
	public void testIsMutant() throws Exception {
		List<String> aux;
		//mutante
		aux = Arrays.asList("ATGGGG","CATTGA","TTATGA","TGAAGA","CCCCTA","TCACTG");
		Assert.isTrue(mutantService.isMutant(aux));
		
		//humano
		aux = Arrays.asList("ATGG","CATT","TTAT","TGAA");
		Assert.isTrue(!mutantService.isMutant(aux));
		
		//mutante
		aux = Arrays.asList("ATGGACT","CATTAAA","TTATACA","TGCAACT","TTACAAT","ATGGCCT","AACTACT");
		Assert.isTrue(mutantService.isMutant(aux));
		
		//mutante
		aux = Arrays.asList("ATGGGG","CATTAA","TTAAAC","TGCAAC","TTACAA");
		Assert.isTrue(mutantService.isMutant(aux));
		
		//dejo un sleep de 3 segundos asi le doy tiempo a que guarde en los thread paralelos 
		Thread.sleep(3000);
		
		CountDTO count = mutantService.getStats();
		Assert.isTrue(count.getCountHumanDna().equals(1L));
		Assert.isTrue(count.getCountMutantDna().equals(3L));
	}
	
	@Test
	public void bulkTest() throws Exception {
		
	}
	
	private List<String> generateRandom(){
		
	}
	

}
