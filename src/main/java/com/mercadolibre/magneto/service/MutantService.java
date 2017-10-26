package com.mercadolibre.magneto.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mercadolibre.magneto.dto.RowDna;
import com.mercadolibre.magneto.model.Mutant;
import com.mercadolibre.magneto.repositories.MutantRepository;

@Service
@Scope("singleton")
public class MutantService {

	@Autowired
	MutantRepository mutantRepository;
	
	public List<Mutant> getAllMutants(){
		return mutantRepository.findAll();
	}
	
	public boolean isMutant(String[] dna, String mutantName) {

		RowDna rowDna;
		List<RowDna> rowDnaList = new ArrayList<>();
		StringBuffer stb = new StringBuffer();
		
		// simulo la matriz en un objeto Row que dentro tiene una lista de string
		// simulando las columnas
		for (String dnaLine : dna) {
			rowDna = new RowDna();
			rowDna.setDnaLine(Arrays.asList(dnaLine.split("(?!^)")));
			rowDnaList.add(rowDna);
			stb.append(dnaLine);
			stb.append("-");
		}

		boolean mutantResult = analizeMatrix(rowDnaList);
		
		Mutant mutant = new Mutant();
		mutant.setDna(stb.toString());
		mutant.setName(mutantName);
		mutant.setIsMutant(mutantResult ? 1 : 0);
		
		mutantRepository.save(mutant);
		
		return mutantResult;

	}

	private boolean analizeMatrix(List<RowDna> rowDnaList) {
		int rowPos;
		int columPos;
		int mathDnaMutant = 0;

		// LEO LA MATRIZ DE IZQ A DERECHA Y DE ARRIBA HACIA ABAJO
		//
		rowPos = 0;
		for (RowDna auxDna : rowDnaList) {
			columPos = 0;

			for (String nitroBase : auxDna.getDnaLine()) {

				if (mathDnaMutant == 2) {
					return true;
				}

				// Esta validacion lo hago por si esta en la columna 4 de 6
				// nunca va a tener 4 campos continuos hacia adelante y evitar procesamiento
				// innecesario
				if ((columPos + 3) <= auxDna.getDnaLine().size()) {
					// BUSCAR DE MANERA HORIZONTAL DE IZQ A DERECHA
					if (searchHorizontal(auxDna, nitroBase, columPos, 0)) {
						System.out.println("es mutante magneto!! " + nitroBase);
						mathDnaMutant++;
					}
				}

				// Esta validación la hago para estar seguro que quedan 4 items para buscar
				// de arriba hacia abajo y evitar procesamiento innecesario
				if (rowPos + 3 <= auxDna.getDnaLine().size()) {
					// BUSCAR DE MANERA VERTICAL DE ARRIBA HACIA ABAJO
					if (searchVertical(rowDnaList, nitroBase, rowPos, columPos, 0)) {
						System.out.println("es mutante charles!! " + nitroBase);
						mathDnaMutant++;
					}
				}

				// Esta validación la hago para estar seguro que quedan 4 items para buscar
				// tanto de arriba hacia abajo como de izq a derecha y evitar procesamiento
				// innecesario
				if ((columPos + 3 <= auxDna.getDnaLine().size()) && (rowPos + 3 <= auxDna.getDnaLine().size())) {
					// BUSCAR DE MANERA OBLICUA DE ARRIBA HACIA ABAJO Y DE IZQ A DERECHA
					if (searchObliquePositive(rowDnaList, nitroBase, rowPos, columPos, 0)) {
						System.out.println("es mutante wolwerine!! " + nitroBase);
						mathDnaMutant++;
					}
				}

				// Esta validación la hago para estar seguro que quedan 4 items para buscar
				// tanto de arriba hacia abajo como de derecha a izq y evitar procesamiento
				// innecesario
				if ((columPos - 3 >= 0) && (rowPos + 3 <= auxDna.getDnaLine().size())) {
					// BUSCAR DE MANERA OBLICUA DE ARRIBA HACIA ABAJO Y DE DERECHA A IZQ
					if (searchObliqueNegative(rowDnaList, nitroBase, rowPos, columPos, 0)) {
						System.out.println("es mutante wilson!! " + nitroBase);
						mathDnaMutant++;
					}
				}

				columPos++;
			}
			rowPos++;
		}
		return false;

	}

	private boolean searchHorizontal(RowDna auxDna, String nitroBase, int columPos, int matchCount) {

		if (columPos == auxDna.getDnaLine().size()) {
			return false;
		}

		if (nitroBase.equals(auxDna.getDnaLine().get(columPos++))) {
			matchCount++;
			if (matchCount == 4) {
				return true;
			}
			return searchHorizontal(auxDna, nitroBase, columPos, matchCount);
		}

		return false;
	}

	private boolean searchVertical(List<RowDna> rowDnaList, String nitroBase, int rowPos, int columPos,
			int matchCount) {

		if (rowPos == rowDnaList.size()) {
			return false;
		}

		if (nitroBase.equals(rowDnaList.get(rowPos++).getDnaLine().get(columPos))) {
			matchCount++;
			if (matchCount == 4) {
				return true;
			}
			return searchVertical(rowDnaList, nitroBase, rowPos, columPos, matchCount);
		}

		return false;
	}

	private boolean searchObliquePositive(List<RowDna> rowDnaList, String nitroBase, int rowPos, int columPos,
			int matchCount) {

		if (rowPos == rowDnaList.size()) {
			return false;
		}

		if (columPos == rowDnaList.size()) {
			return false;
		}

		if (nitroBase.equals(rowDnaList.get(rowPos++).getDnaLine().get(columPos++))) {
			matchCount++;
			if (matchCount == 4) {
				return true;
			}
			return searchObliquePositive(rowDnaList, nitroBase, rowPos, columPos, matchCount);
		}

		return false;
	}

	private boolean searchObliqueNegative(List<RowDna> rowDnaList, String nitroBase, int rowPos, int columPos,
			int matchCount) {

		if (rowPos == rowDnaList.size()) {
			return false;
		}

		if (nitroBase.equals(rowDnaList.get(rowPos++).getDnaLine().get(columPos--))) {
			matchCount++;
			if (matchCount == 4) {
				return true;
			}
			return searchObliqueNegative(rowDnaList, nitroBase, rowPos, columPos, matchCount);
		}

		return false;
	}

}
