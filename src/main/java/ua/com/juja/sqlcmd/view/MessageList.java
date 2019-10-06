package ua.com.juja.sqlcmd.view;



public enum MessageList {

    DRIVER_NOT_FOUND("добавь jdbc драйвер"),
    NOT_CONNECTION("нет соединения для модели:"),

    CLEAR_TABLE("таблица %s очищена"),
    WRONG_CLEAR_TABLE("формат команды 'clear|tableName', а ты ввел:"),

    CREATE_TABLE_ENTRY("в таблицу %s запись добавлена"),
    WRONG_CREATE_TABLE_ENTRY("должно быть четное колличесво параметров," +
            " в формате 'create|tableName|column1|value1|column2|value2|...|columnN|valueN'," +
            " а есть:"),

    WRONG_FIND("формат команды 'find|tableName', а ты ввел:"),

    EXIT_PROGRAM("до встречи!"),

    UNSUPPORTED_COMMAND("несуществующая команда:"),



    WRONG("формат команды 'clear|tableName', а ты ввел:");




    private String textmessage;

    MessageList(String textmessage){
        this.textmessage = textmessage;
    }

    public String getMessage(){
        return  textmessage;
    }
}
