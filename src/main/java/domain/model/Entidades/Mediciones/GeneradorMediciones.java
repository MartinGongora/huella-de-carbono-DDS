package domain.model.Entidades.Mediciones;

import domain.model.Entidades.Organizacion;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GeneradorMediciones {

    private String excelPath;
    private FileInputStream excelStream;
    private List<Medicion> emisiones;
    private LocalDateTime localDate;

    public GeneradorMediciones(String excelPath) throws FileNotFoundException {
        this.excelPath = excelPath;
        this.excelStream = new FileInputStream(excelPath);
        this.emisiones = new ArrayList<>();
        this.localDate = LocalDateTime.now();
    }

    public void procesarExcel(Organizacion organizacion) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(excelStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getLastRowNum();

        for(int r=0;r<=rows;r++) {
            Actividad actividad = Actividad.valueOf(sheet.getRow(r).getCell(0).getStringCellValue());
            Medicion medicion = new Medicion();
            medicion.setOrganizacion(organizacion);
            medicion.setActividad(actividad);

            DataFormatter dataFormatter = new DataFormatter();
            String fecha = dataFormatter.formatCellValue(sheet.getRow(r).getCell(4));

            Periodicidad periodo = Periodicidad.valueOf(sheet.getRow(r).getCell(3).getStringCellValue());
            medicion.setPeriodicidad(periodo);
            if(periodo == Periodicidad.ANUAL){
                medicion.setAnio((int) sheet.getRow(r).getCell(4).getNumericCellValue());
                medicion.setMes(0);
            } else {
                medicion.setAnio(Integer.valueOf(fecha.substring(3,7)));
                medicion.setMes(Integer.valueOf(fecha.substring(0,2)));
            }

            if (actividad != Actividad.LOGISTICA) {
                ConsumoDirecto tipoDeConsumo = new ConsumoDirecto(Tipo.valueOf(sheet.getRow(r).getCell(1).getStringCellValue()));
                tipoDeConsumo.setValor((double) sheet.getRow(r).getCell(2).getNumericCellValue());
                medicion.setTipoDeConsumo(tipoDeConsumo);
            } else {
                ConsumoLogistica tipoDeConsumo = new ConsumoLogistica(sheet.getRow(r).getCell(2).getStringCellValue(),
                        sheet.getRow(r+1).getCell(2).getStringCellValue(),
                        sheet.getRow(r+2).getCell(2).getNumericCellValue(),
                        sheet.getRow(r+3).getCell(2).getNumericCellValue());
                medicion.setTipoDeConsumo(tipoDeConsumo);
                r += 3;
            }
            organizacion.setMediciones(medicion);
        };
    }
}
