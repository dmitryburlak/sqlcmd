package ua.com.juja.sqlcmd.message;



public enum MessageList {
    COMMAND_SAMPLE("connect|namelist|postgres|root"),
    DRIVER_NOT_FOUND("нужно добавить jdbc драйвер"),
    NOT_CONNECTION("нет соединения для модели:"),
    NOT_CONNECTED_COMMAND("вы не можете пользоваться командой, %s, пока не подключитесь с помощью команды connect|databaseName|userName|password"),
    WRONG_PARAM_CONNECT("ожидается %s параметра, а есть: %s"),
    OK_PARAM_CONNECT("ок"),

    PROPERTIESFILE_NOT_FOUND("файл connect.properties не найден"),

    CLEAR_TABLE("таблица %s очищена"),
    WRONG_CLEAR_TABLE("формат команды 'clear|tableName', а ты ввел:"),

    CREATE_TABLE("таблица %s создана"),
    WRONG_CREATE_TABLE_PARAM("формат команды Create|tableName|column1(PK)|column2|...|columnN , а ты ввел: "),

    DROP_TABLE("таблица %s удалена"),
    WRONG_DROP_TABLE_PARAM("формат команды drop|tableName, а ты ввел: "),

    INSERT_TABLE_DATA("в таблицу %s запись добавлена"),
    WRONG_INSERT_TABLE_DATA("должно быть четное колличесво параметров," +
            " в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'," +
            " а есть:"),
    DELETE_TABLE_DATA("в таблицe %s из колонки %s запись %s удалена"),
    WRONG_DELETE_TABLE_DATA("формат команды delete|tableName|column|value, а ты ввел: "),

    UPDATE_TABLE_DATA("в таблице %s id %d в колонке %s запись обновлена"),
    WRONG_UPDATE_TABLE_DATA("формат команды update|tableName|id|columnN|newvalue , а ты ввел: "),

    WRONG_FIND("формат команды 'find|tableName', а ты ввел:"),

    EXIT_PROGRAM("до встречи!"),

    UNSUPPORTED_COMMAND("несуществующая команда:");


    private String textmessage;

    MessageList(String textmessage){
        this.textmessage = textmessage;
    }

    public String getMessage(){
        return  textmessage;
    }
}
