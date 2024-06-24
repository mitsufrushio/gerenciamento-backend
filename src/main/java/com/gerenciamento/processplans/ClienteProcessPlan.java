package com.gerenciamento.processplans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerenciamento.api.domain.Cliente;
import com.gerenciamento.api.service.ClienteService;

import io.micrometer.common.util.StringUtils;

@Service
public class ClienteProcessPlan {
	
	@Autowired
	ClienteService clienteService;
	
	public void processPlan(File file) throws IOException {
		List<Cliente> list = new ArrayList<>();
		try {
			FileInputStream arquivo = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				Cliente cliente = new Cliente();
				if(row.getRowNum() != 0) {
					while(cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch(cell.getColumnIndex()) {
						case 0:
							cliente.setNome(cell.getStringCellValue());
							break;
						case 1:
							cliente.setEmail(cell.getStringCellValue());
							break;
						case 2:
							cliente.setFone(cell.getCellType() == CellType.NUMERIC ?
									String.valueOf(new BigDecimal(cell.getNumericCellValue())) : cell.getStringCellValue());
							break;
						case 3:
							cliente.setCpfCnpj(cell.getCellType() == CellType.NUMERIC ?
									String.valueOf(new BigDecimal(cell.getNumericCellValue())) : cell.getStringCellValue());
							break;
						default:
							break;
						}						
					}
				}
				if(StringUtils.isNotEmpty(cliente.getNome())) {
					cliente.setDtEntrSis(new Date());
					list.add(cliente);
				}
			}
			arquivo.close();
			workbook.close();
			saveList(list);
		} catch(FileNotFoundException e) {
			throw new FileNotFoundException(String.format("Arquivo de Planilha não encontrado", e.getMessage()));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void saveList(List<Cliente> list) {
		AtomicInteger i = new AtomicInteger(0);
		list.stream().forEach(cliente -> {
			try {
				clienteService.save(cliente);
				i.getAndIncrement();
			} catch (Exception e) {
				System.out.println("Erro ao salvar Cliente CPF/CNPJ = " + cliente.getCpfCnpj());
				e.printStackTrace();
			}
		});
		if(i.get() > 0) {
			System.out.println("Total de Clientes salvos na base de dados = " + i.get());
		}
	}

}
