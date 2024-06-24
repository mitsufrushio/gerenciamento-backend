package com.gerenciamento.processplans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerenciamento.api.domain.Cliente;
import com.gerenciamento.api.domain.Venda;
import com.gerenciamento.api.repository.ClienteRepository;
import com.gerenciamento.api.repository.VendaRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class VendaProcessPlan {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private VendaRepository vendaRepository;
	
	public void processPlan(File file) throws IOException {
		List<Venda> list = new ArrayList<>();
		try {
			FileInputStream arquivo = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				Venda venda = new Venda();
				if(row.getRowNum() != 0) {
					while(cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch(cell.getColumnIndex()) {
						case 0:
							String cpfCnpjUsu = cell.getCellType() == CellType.NUMERIC ?
									String.valueOf(new BigDecimal(cell.getNumericCellValue())) : cell.getStringCellValue();
							if(StringUtils.isNotEmpty(cpfCnpjUsu)) {
								Optional<Cliente> cliente = clienteRepository.findByCpfCnpj(cpfCnpjUsu) ;
								venda.setCliente(cliente.get());
							}
							break;
						case 1:
							venda.setData(cell.getCellType() == CellType.STRING ?
									this.convertDate(cell.getStringCellValue()) : cell.getDateCellValue());
							break;
						case 2:
							venda.setValor(cell.getCellType() == CellType.NUMERIC ?
									new BigDecimal(cell.getNumericCellValue()) : new BigDecimal(cell.getStringCellValue()));
							break;
						case 3:
							venda.setObs( cell.getCellType() == CellType.NUMERIC ?
									String.valueOf(new BigDecimal(cell.getNumericCellValue())) : cell.getStringCellValue());
							break;
						default:
							break;
						}
					}
				}
				if(venda.getCliente() != null) {
					list.add(venda);
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
	
	public void saveList(List<Venda> list) {
		AtomicInteger i = new AtomicInteger(0);
		list.stream().forEach(venda -> {
			try {
				vendaRepository.save(venda);
				i.getAndIncrement();
			} catch (Exception e) {
				System.out.println("Erro ao salvar venda Cliente = " + venda.getCliente().getNome() + " valor = " + venda.getValor());
				e.printStackTrace();
			}
		});
		if(i.get() > 0) {
			System.out.println("Total de Vendas salvas na base de dados = " + i.get());
		}
	}
	
	private Date convertDate(String dataString) throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(dataString);
			return date;			
		} catch (Exception e) {
			throw new Exception("Erro ao converter data");
		}
	}
}
