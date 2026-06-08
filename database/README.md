initialize database with 
```bash
    initdb -D database/mydb
```

start with 
```bash
    pg_ctl -D database/mydb -l database/logfile -o "--unix_socket_directories='$PWD/database'" start
```

stop with 
```bash
    pg_ctl -D database/mydb stop
```

make sure to create your initial DB and user
```bash
    createdb test -h 127.0.0.1  
    createuser postgres -h 127.0.0.1
```

connect with
```bash
    psql -h 127.0.0.1 -U postgres
```

#TODO: move away from this and create a compose file that sets up postgres along with backend and frontend