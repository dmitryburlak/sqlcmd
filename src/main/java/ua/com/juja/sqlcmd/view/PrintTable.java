package ua.com.juja.sqlcmd.view;

import ua.com.juja.sqlcmd.model.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrintTable {
    private static View view = new Console();

    public static void printTableData(List<DataSet> tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
    }

    private static void printRow(DataSet row) {
        List<Object> values = row.getValue();
        PrintParameters printParameters = new PrintParameters(values).printData();
        String resultId = printParameters.getResultId();
        String resultColumns = printParameters.getResultColumns();
        String separatorId = printParameters.getSeparatorId();
        String separator = printParameters.getSeparator();
        view.write(resultId.concat(resultColumns));
        view.write(separatorId.concat(separator));
    }

    public static void printHeader(Set<String> tableColumns) {
        List<Object> tableColumnsList = new ArrayList<>(tableColumns);
        PrintParameters printParameters = new PrintParameters(tableColumnsList).printData();
        String resultId = printParameters.getResultId();
        String resultColumns = printParameters.getResultColumns();
        String separatorId = printParameters.getSeparatorId();
        String separator = printParameters.getSeparator();
        view.write(separatorId.concat(separator));
        view.write(resultId.concat(resultColumns));
        view.write(separatorId.concat(separator));
    }

    private static class PrintParameters {
        private List<Object> values;
        private String resultId = "";
        private String resultColumns = "";
        private String separatorId = "+---+";
        private String separator = "";
        private String separatorLong = "---------------+";

        public PrintParameters(List<Object> values) {
            this.values = values;
        }

        public String getResultId() {
            return resultId;
        }

        public String getResultColumns() {
            return resultColumns;
        }

        public String getSeparatorId() {
            return separatorId;
        }

        public String getSeparator() {
            return separator;
        }

        public String getSeparatorLong() {
            return separatorLong;
        }

        public PrintParameters printData() {
            resultId = getResultId();
            resultColumns = getResultColumns();
            separatorId = getSeparatorId();
            separator = getSeparator();
            separatorLong = getSeparatorLong();
            for (int index = 0; index < values.size(); index++) {
                if (index == 0) {
                    resultId = String.format("|%3s|", values.get(index));
                } else {
                    separator = separator.concat(separatorLong);
                    resultColumns = resultColumns.concat(String.format("%15s|", values.get(index)));
                }
            }
            return this;
        }

    }
}
