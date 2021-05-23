package com.example.eventappfinal.database;

public class Content {
    public static final String DATABASE_NAME = "db_event";
    public static final int DATABASE_VERSION = 1;

    //user table
    public static final String TABLE_USER = "tb_user";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_MAJOR = "major";
    public static final String USER_DESCRIPTION = "description";
    public static final String USER_FOTO = "foto";

    //note table
    public static final String TABLE_NOTES="tb_mynotes";
    public static final String NOTES_ID="id";
    public static final String NOTES_EVENT_ID="event_id";
    public static final String NOTES_DESCRIPTION="description";
    public static final String NOTES_EMAIL="email";

    //event table
    public static final String TABLE_EVENT="tb_event";
    public static final String EVENT_ID="id";
    public static final String EVENT_NAME="name";
    public static final String EVENT_CATEGORY="category";
    public static final String EVENT_DATE="date";
    public static final String EVENT_CONTENT="content";
    public static final String EVENT_DURATION="duration";
    public static final String EVENT_CAPACITY="capacity";
    public static final String EVENT_DESCRIPTION="description";
    public static final String EVENT_EMAIL="email";

    //join table
    public static final String TABLE_JOIN="tb_join";
    public static final String JOIN_ID="id";
    public static final String JOIN_ID_EVENT="IdEvent";
    public static final String JOIN_EMAIL="email";

    public static final String insert1 = "INSERT INTO tb_event (name, category, date, content, duration, capacity, description, email) " +
            "VALUES " +
            "( 'Math Basic','One Time Event','23 May 2021','Science','2','10','In the class we will learning about foundation of math, so dont worry that you dont understand about math','alamsadvika@gmail.com')," +
            "( 'Match Advanced','Weekly Event','23 January 2021','Science','2','10','In this class we will learn more deeply about Math. The level of this class is little bit higher than basic','alamsadvika@gmail.com')," +
            "( 'Math Intermediate','One Time Event','23 October 2021','Science','2','10','In the class we will learning about foundation of math, so dont worry that you dont understand about math','alamsadvika@gmail.com')," +
            "( 'Network Fundamental','Weekly Event','23 June 2021','Technical','2','10','We will learning about network fundamental from scratch, so dont worry that you cant get along in this class','alamsadvika@gmail.com')," +
            "( 'Network Advanced','Seminar','12 December 2021','Science','2','10','In the network advanced class we will learn deeply about routing','alamsadvika@gmail.com')," +
            "( 'Network Intermediate','One Time Event','23 January 2022','Technical','2','10','In this class we will learn more deeply about network, and it is MPLS','alamsadvika@gmail.com')," +
            "( 'Calculus Fundamental','One Time Event','23 February 2022','Science','2','10','We will learn about calculus fundamental and learn from basic','alamsadvika@gmail.com')," +
            "( 'Calculus Intermediate','One Time Event','12 March 2022','Science','2','10','In this class we will learn about calculus in intermediate level','alamsadvika@gmail.com')," +
            "( 'Calculus Advanced','One Time Event','23 April 2022','Science','2','10','We will learn calculus in advanced level, so prepare yourself for this amazing class','alamsadvika@gmail.com')," +
            "( 'Calculus Professional','One Time Event','23 May 2022','Science','2','10','You want to be professional in calculus?, come here to our class, we guarantee you can be master in calculus','alamsadvika@gmail.com')," +
            "( 'Database Basic','Monthly Event','23 October 2021','Science','2','10','We will learn about database from basic','alamsadvika@gmail.com')," +
            "( 'Database Intermediate','Weekly','12 September 2021','Science','2','10','We will learn about database in higher level than fundamental','alamsadvika@gmail.com')," +
            "( 'Database Advanced','Seminar','25 August 2021','Science','2','10','We will learn more advanced about database','alamsadvika@gmail.com')," +
            "( 'Database Professional','Seminar','11 September 2021','Science','2','10','You are ready to become database engineer in big company?, come here and join this event','alamsadvika@gmail.com')," +
            "( 'Web Programming Fundamental','One Time Event','4 January 2022','Science','2','10','We will introduce about web programming for beginner','alamsadvika@gmail.com')," +
            "( 'Web Programming Intermediate','Monthly Event','23 May 2021','Science','2','10','In this class we will learn more deeply about web programming','alamsadvika@gmail.com')," +
            "( 'Web Programming Advanced','Weekly Event','12 August 2021','Science','2','10','We will learn about advanced in web programming, so prepare yourself','alamsadvika@gmail.com')," +
            "( 'Web Programming Professional','Monthly Event','11 November 2022','Science','2','10','If you ready to work in the company like Alibaba or Shopee, you need to learn about web programming professional here','alamsadvika@gmail.com')," +
            "( 'Android Fundamental','Monthly Event','4 October 2021','Science','2','10','In this class we will learn about fundamental of android','alamsadvika@gmail.com')," +
            "( 'Android Intermediate','Weekly Event','28 August 2021','Science','2','10','Want to be more intermediate in android?, come here in this class, we have someone who can teach you','alamsadvika@gmail.com')," +
            "( 'Android Advanced','Monthly Event','20 July 2021','Science','2','10','You want to learn more deeply about android but doesn have money?, come to my class, you can learn more deeply about android free','alamsadvika@gmail.com')," +
            "( 'IT Basic','One Time Event','21 June 2021','Science','2','10','A lot of people want to learn about basic of Information Technology but cant find it, come to my class to learn more about basic of IT','alamsadvika@gmail.com')," +
            "( 'ARM Processor','One Time Event','23 September 2021','Technology','2','10','Why ARM processor getting more popular nowdays?, come here and find out','vpuspitan@gmail.com')," +
            "( 'x86 Processor','One Time Event','4 August 2021','Technology','2','10','You want to know more what is x86 processor? come here and find out that this is really close with us','vpuspitan@gmail.com')," +
            "( 'Computer Management','Seminar','6 July 2021','Technology','2','10','You want to manage IT device in your company easily?, come here and find out','vpuspitan@gmail.com')," +
            "( 'Modern Business','Seminar','8 November 2021','Business','2','10','Along side with the development of era, business also evolve in the new way. Come to this event and find out','vpuspitan@gmail.com')," +
            "( 'Tradisional Business','One Time Event','4 September 2021','Business','2','10','Sometime old time is the best, Lets learn business from traditional ways','vpuspitan@gmail.com')," +
            "( 'Debian Linux','One Time Event','8 July 2021','Technology','2','10','Lets start with open source OS, and lets learn about Debian Linux','vpuspitan@gmail.com')," +
            "( 'Fedora Linux','Seminar','3 December 2021','Technology','2','10','Lets use open source OS, come to this event and learn together about fedora linux','vpuspitan@gmail.com')," +
            "( 'Docker ','One Time Event','4 June 2021','Technology','2','10','Lets learn about new technology, Docker, say goodbye to virtualization OS','vpuspitan@gmail.com')," +
            "( 'Kubernetes','One Time Event','20 June 2021','Technology','2','10','Do you want learn about kubernetes ? come here to this event','vpuspitan@gmail.com')," +
            "( 'IOS Programming','One Time Event','4 August 2021','Technology','2','10','Lets learn about IOS and build your own apps','vpuspitan@gmail.com')," +
            "( 'Javascript Fundamental','Weekly Event','23 May 2021','Technology','2','10','Lets learn together about javascript with a lot of friends','vpuspitan@gmail.com')," +
            "( 'React Native','One Time Event','3 September 2021','Technology','2','10','Lets learn together about what is react native, the function and demand nowdays','vpuspitan@gmail.com')," +
            "( 'IOT Fundamental','Monthly Event','3 August 2021','Technology','2','10','For nowdays, IOT is getting more and more popular, lets learn together and dont let yourself behind another','vpuspitan@gmail.com');";

}
