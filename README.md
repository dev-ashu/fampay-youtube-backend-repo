FamPayTest is a Web API built using Java 8, Spring Boot
There are two parts in the application
1. Exposes /youtube/video/details POST endpoint to fetch video details. Request body should contains details like below

Request :-
```json
{
    "draw": 140,
    "columns": [
        {
            "data": "title",
            "name": "",
            "searchable": true,
            "orderable": false,
            "search": {
                "value": "",
                "regex": false
            }
        },
        {
            "data": "description",
            "name": "",
            "searchable": true,
            "orderable": false,
            "search": {
                "value": "",
                "regex": false
            }
        },
        {
            "data": "thumbnailUrl",
            "name": "",
            "searchable": true,
            "orderable": false,
            "search": {
                "value": "",
                "regex": false
            }
        },
        {
            "data": "publishDatetime",
            "name": "",
            "searchable": true,
            "orderable": true,
            "search": {
                "value": "",
                "regex": false
            }
        }
    ],
    "order": [
        {
            "column": 3,
            "dir": "desc"
        }
    ],
    "start": 0,
    "length": 5,
    "search": {
        "value": "r",
        "regex": false
    }
} 
```
Response:
```json
{
    "draw": 1,
    "recordsTotal": 107,
    "recordsFiltered": 107,
    "data": [
        {
            "title": "Live Cricket Match | Monu Bankers XI vs Rathoretigers | 30-May-21 08:30 AM 10 overs | Individual mat",
            "description": "Watch Full Scorecard on CricHeroes: https://tinyurl.com/yfdjyssk Teams: Monu Bankers XI vs Rathoretigers Tournament: Individual match CricHeroes is world's ...",
            "thumbnailUrl": "https://i.ytimg.com/vi/4Ug0Qt-n52w/default.jpg",
            "publishDatetime": "2021/05/30 09:53:48"
        },
        {
            "title": "Live Cricket Match | HOLISTIC ROYALE vs HEROS OF MAHAKAL | 30-May-21 08:42 AM 10 overs | FSG PREMIER",
            "description": "Watch Full Scorecard on CricHeroes: https://tinyurl.com/yj8a5tky Round: Round One Teams: HOLISTIC ROYALE vs HEROS OF MAHAKAL Tournament: FSG ...",
            "thumbnailUrl": "https://i.ytimg.com/vi/ggr63jCw4Rw/default.jpg",
            "publishDatetime": "2021/05/30 09:52:10"
        },
        {
            "title": "BABAR CLASSY KNOCK | 2ND SEMI FINAL PSL 6 |GAMING UNIVERSE |CRICKET 19",
            "description": "BABAR CLASSY KNOCK | 2ND SEMI FINAL PSL 6 |GAMING UNIVERSE |CRICKET 19 I HOPE YOU WILL ENJOY THIS VIDEO PLEASE SUBSCRIBE TO MY ...",
            "thumbnailUrl": "https://i.ytimg.com/vi/3pMlTdtmSNU/default.jpg",
            "publishDatetime": "2021/05/30 09:51:19"
        },
        {
            "title": "cricket lavar",
            "description": "??????? ???? ???? ??? ??????? ???? ???? ??? ??????? ?????? ??????? ?????? ???? please my channel subscribe me and like me.",
            "thumbnailUrl": "https://i.ytimg.com/vi/3PwdKRLPu4U/default.jpg",
            "publishDatetime": "2021/05/30 09:50:52"
        },
        {
            "title": "Best cover drive players in the world | cricket best shots | cricket animation #shorts #cricket",
            "description": "",
            "thumbnailUrl": "https://i.ytimg.com/vi/ZFl2NtSwSXE/default.jpg",
            "publishDatetime": "2021/05/30 09:48:55"
        }
    ]
}
```
I have used above format because UI's jquery DataTable library communicates using above format.

2. A scheduler runs after every 10 seconds which call youtube api to fetch video details.
To call Youtube Data Api, it requires API Key, I have added list of keys in application.properties so that if one 
key has been exhausted application uses other key
API Query url and query params that are used to call Youtube Data API are also configurable since
we are fetching it from application.properties

I am using SQL server for database.
I have dockerized SQL server and Spring Boot application.

Please see below commands to run these two applications on docker

1. docker network creation

    docker network create fampay-net

2. SQL server installation

a) Go to tha sql folder of the project

b) Build docker image using below command

    docker build -t mssql .
 
c) create mssql container using below command

    docker run --net fampay-net -p 1401:1433 --name mssql -d mssql

3. Spring Boot application deployment

a) Go to the project's root directory

b) Build docker image using below command

    docker build -f Dockerfile -t fampay-spring-boot .

c) create spring boot application container using below command

    docker run --net fampay-net -p 8085:8085 --name fampay-spring-boot -d fampay-spring-boot
    
Spring Boot application will be accesible at localhost:8085
SQL server will be accessible at localhost:1401