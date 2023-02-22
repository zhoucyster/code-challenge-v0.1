I provided 4 endpoints for user to interact with my server (port:8182)

1. /api/dowjones/add

   allow user to add new record to server using json format
2. /api/dowjones/query?stock=xx

   allow user to search by stock ticker
3. /api/dowjones/upload

   allow user to upload more than one record to server using json format
4. /api/dowjones/upload-file

   allow user to upload a csv file to server, server will skip the header line and start loading from second line to bottom.
   
Note:

For simplicity purpose, I used H2 in-memory database,
but it can be easily changed to any relational database in the future by modifying db settings in application.yml