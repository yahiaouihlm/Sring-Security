
CREATE TABLE IF NOT EXISTS role (
        roleid SERIAL PRIMARY KEY , 
        rolename  VARCHAR(100),
        roledescription TEXT 
)

CREATE TABLE users (
    id Serial  , 
    name VARCHAR(40) NOT NULL,
    fname VARCHAR(40) NOT NULL,
    email VARCHAR(40) NOT NULL ,
    password VARCHAR(400) NOT NULL,
    birthday DATE,
    PRIMARY KEY  (id)

)


    CREATE TABLE IF NOT EXISTS  users_has_role (
        userid INT NOT NULL ,
        roleid INT NOT NULL, 
        PRIMARY KEY (userid ,  roleid),
        FOREIGN KEY  (userid) REFERENCES users(id),
        FOREIGN KEY  (roleid) REFERENCES role(roleid)
    )




