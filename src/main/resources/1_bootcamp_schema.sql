
CREATE TABLE users (
                       id SERIAL PRIMARY KEY ,
                       name VARCHAR ,
                       email VARCHAR UNIQUE NOT NULL,
                       password VARCHAR NOT NULL,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE thread (
                        thread_id SERIAL PRIMARY KEY,
                        name VARCHAR,
                        user_id INTEGER NOT NULL REFERENCES users(id),
                        start_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                        end_time TIMESTAMP WITH TIME ZONE
);

CREATE TABLE messages (
                          message_id SERIAL PRIMARY KEY,
                          thread_id INTEGER NOT NULL REFERENCES thread(thread_id) ON DELETE CASCADE,
                          sender_is_user BOOLEAN NOT NULL,
                          message_text TEXT NOT NULL,
                          completion_model TEXT NOT NULL,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);



insert into thread(thread_id, name, user_id) values (3,'test',1);