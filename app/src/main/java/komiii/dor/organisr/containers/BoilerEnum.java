package komiii.dor.organisr.containers;

public enum BoilerEnum {

    connectionURL ("jdbc:mysql://den1.mysql6.gear.host/organisr"),
    connectionUsername("organisr"),
    connectionPassword ("Rn0rp~wEpWp?"),

    mainQuery("SELECT * FROM quotes ORDER BY RAND(date(convert_tz(current_time(),'+00:00','+08:00'))) LIMIT 1"),
    mainSecondQuery("SELECT * FROM events where date = date(convert_tz(current_time(),'+00:00','+08:00')) and id not in (SELECT event_id FROM events_cancels where cancel_date = date(convert_tz(current_time(),'+00:00','+08:00'))) and hour>=time(convert_tz(current_time(),'+00:00','+08:00')) ORDER BY HOUR LIMIT 3"),
    mainThirdQuery("SELECT * FROM PRODUCTS WHERE QUANTITY > 0 ORDER BY CATEGORY LIMIT 3"),
    mainFourthQuery("SELECT * FROM Reminders LIMIT 3"),
    mainFifthQuery("SELECT * FROM goals where progress<goal LIMIT 3"),
    mainSixthQuery("SELECT * FROM todos where date=curdate() and checked=0 limit 3"),

    reminderQuery("SELECT * FROM Reminders ORDER BY TYPE"),
    reminderDeleteQuery("DELETE FROM Reminders WHERE ID ="),

    shoppingBasketQuery("SELECT * FROM PRODUCTS WHERE QUANTITY > 0 ORDER BY CATEGORY"),
    shoppingBasketDoneFirstQuerry("UPDATE PRODUCTS SET QUANTITY = 0 WHERE (`id` = "),
    shoppingBasketDoneSecondQuerry("DELETE FROM PRODUCTS WHERE (`id` = "),

    shoppingProductListQuery("SELECT * FROM PRODUCTS WHERE LIST_WISHLIST=0 ORDER BY CATEGORY, NAME"),
    shoppingWishListQuery("SELECT * FROM PRODUCTS WHERE LIST_WISHLIST=1"),

    goalsRefreshQuery("SELECT * FROM GOALS ORDER BY TYPE"),
    goalDeleteQuery("DELETE FROM goals WHERE ID ="),

    calendarFirstQuery("SELECT * FROM events order by hour"),
    calendarSecondQuery("SELECT * FROM events_cancels"),

    todosTodayQuery("SELECT * FROM TODOS WHERE DATE=CURDATE()"),
    todosTomorrowQuery("SELECT * FROM TODOS WHERE DATE=ADDDATE(CURDATE(),1)"),
    ;
    private String content;

    public static final int URGENCY_LEVEL0 = 3;
    public static final int URGENCY_LEVEL1 = 6;
    public static final int URGENCY_LEVEL2 = 10;
    public static final int URGENCY_LEVEL3 = 16;
    public static final int URGENCY_LEVEL4 = 22;

    public static final int URGENCY_LEVELA = 1;
    public static final int URGENCY_LEVELB = 3;
    public static final int URGENCY_LEVELC = 5;
    public static final int URGENCY_LEVELD = 7;
    public static final int URGENCY_LEVELE = 9;

    BoilerEnum(String c){
        content = c;
    }

    public String getContent(){
        return content;
    }
}
