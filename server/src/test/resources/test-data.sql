INSERT INTO users (name, email ) VALUES ('Иван', 'ivan@mail.com');
INSERT INTO users (name, email ) VALUES ('Игорь', 'igor@mail.com');
INSERT INTO users (name, email ) VALUES ('Петр', 'petr@mail.com');

INSERT INTO requests (description, requestor_id, created) VALUES ('Описание первой вещи', 1, CURRENT_TIMESTAMP);
INSERT INTO requests (description, requestor_id, created) VALUES ('Описание второй вещи ', 1, CURRENT_TIMESTAMP);

INSERT INTO items (name, description, is_available, owner_id, request_id) VALUES ('Название 1', 'Описание первой вещи', true, 1, 2);
INSERT INTO items (name, description, is_available, owner_id, request_id) VALUES ('Название 2', 'Описание второй вещи', true, 2, 1);

INSERT INTO bookings (start_moment, end_moment, item_id, booker_id, status) VALUES (CURRENT_DATE,  CURRENT_DATE, 1, 2, 'APPROVED');
INSERT INTO bookings (start_moment, end_moment, item_id, booker_id, status) VALUES (CURRENT_DATE,  CURRENT_DATE, 2, 1, 'WAITING');
INSERT INTO bookings (start_moment, end_moment, item_id, booker_id, status) VALUES ('2050-12-10 18:00:00',  '2050-12-30 18:00:00', 1, 2, 'APPROVED');

INSERT INTO comments (text, item_id, author_id, created) VALUES ('Текст комментария 1', 1L, 2L, CURRENT_DATE);
INSERT INTO comments (text, item_id, author_id, created) VALUES ('Текст комментария 2', 2L, 1L, CURRENT_DATE);
