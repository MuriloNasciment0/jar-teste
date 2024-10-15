package sptech.school;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import sptech.school.client.S3Provider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ETL {
    public void ConectarS3(String arquivo) {
        // Usa o cliente configurado no S3Provider
        S3Provider s3Provider = new S3Provider();
        S3Client conexS3 = s3Provider.getS3Client();

        String bucketName = "s3-bucket-aevusec2";

        // Adiciona log para verificar o arquivo e o bucket
        System.out.println("Tentando acessar o bucket: " + bucketName);
        System.out.println("Chave do arquivo: " + arquivo);

        try (InputStream arquivoS3 = conexS3.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(arquivo)
                .build())) {
            List<PesquisaFiltrada> PesquisaExtraida = ExtrairPesquisa(arquivo, arquivoS3);

            for (PesquisaFiltrada pesquisa : PesquisaExtraida) {
                InserirBanco(pesquisa);
            }
        } catch (IOException | S3Exception e) {
            System.err.println("Erro ao fazer download dos arquivos: " + e.getMessage());
        }
    }

    public List<PesquisaFiltrada> ExtrairPesquisa(String nomeArquivo, InputStream arquivo){
        try {
            Workbook arquivoExcel;
            if(nomeArquivo.endsWith(".xlsx")){
                arquivoExcel = new XSSFWorkbook(arquivo);
            } else {
                arquivoExcel = new HSSFWorkbook(arquivo);
            }

            Sheet base = arquivoExcel.getSheetAt(3);

            List<PesquisaFiltrada> pesquisasExtraidas = new ArrayList<>();

            for(Row linha : base){
                if(linha.getRowNum() == 0){
                    continue;
                }
                PesquisaFiltrada pesquisa = new PesquisaFiltrada();
                pesquisa.setBanheiro(linha.getCell(2).getNumericCellValue());
                //Cotinuar a adiconar as notas dos serviços;

                pesquisasExtraidas.add(pesquisa);
            }
            arquivoExcel.close();
            return pesquisasExtraidas;
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void InserirBanco(PesquisaFiltrada pesquisa){
        ConexBanco conectar = new ConexBanco();
        JdbcTemplate conec = conectar.getConexaoBanco();
        String inserirBanco = "INSET INTO pesquisa VALUES(?, ?, ?, ?)";

        conec.update(inserirBanco, pesquisa.getBanheiro(), pesquisa.getBagagem(), pesquisa.getFila(), pesquisa.getTransporte());
    }
}





