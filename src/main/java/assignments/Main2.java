package assignments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import csv_pojo.TransactionLog;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.List;

public class Main2 {
    static Logger logger = LoggerFactory.getLogger(Main2.class);

    public static void main(String[] args) throws IOException {
        List<TransactionLog> transactionLogs = Lists.newArrayList();
        Reader in = new FileReader(args[0]);
        try (CSVParser csvParser = new CSVParser(in, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            csvParser.forEach(csvRecord -> {
                final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
                final TransactionLog transactionLog = mapper.convertValue(csvRecord.toMap(), TransactionLog.class);
                transactionLogs.add(transactionLog);
            });
        }
        String monthYear = args[1];
        String [] strings = monthYear.split(" ");
        Double chargesForTheMonth = transactionLogs.stream()
                .filter(transactionLog -> !transactionLog.getDescription().equalsIgnoreCase("auto recharge for wallet"))
                .filter(transactionLog -> transactionLog.getDate().contains(strings[0]))
                .filter(transactionLog -> transactionLog.getDate().contains(strings[1]))
                .mapToDouble(TransactionLog::getAmount).sum();
        DecimalFormat df = new DecimalFormat("#0.######");
        logger.info("Charges For The Month {} = {}", monthYear,  df.format(chargesForTheMonth));
    }

}