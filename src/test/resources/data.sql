INSERT INTO users (username, password, age, display_name, nationality)
VALUES ('BorisCool1', '$2a$10$SGScJOEXacO2k86MJOqicuc9vt4nMTb9wKpI.5D1TfHqSMr.cQEHq', 35, 'Test Admin', 'Norway');

INSERT INTO admin_entity (id, monthly_salary, contract_start_date, contract_end_date, address)
VALUES (1, 4500.0, '2025-01-01', '2026-01-01', 'Oslo City');

INSERT INTO articles (article_title, image_url, content_text, author_id)
VALUES ('Seeded Article', 'http://example.com/image.jpg', 'Seeded article body.', 1);

INSERT INTO comments (text, user_id, article_id)
VALUES ('some text', 1, 1);

INSERT INTO officialnews (title, link, published_date, description, author)
VALUES ('some text', 'somelink' , '2025-01-01', 'some description', 'idk');
