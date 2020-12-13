INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO users
    (id, username, email, password) VALUES
    (1, 'testuser', 'testuser@gmail.com', '$2a$10$HyWO8Kqx.gaO2aclMWZLHOdxObF9KOdA/YzJtiUJ05tQuX1p3WhFK'),
    (2, 'testuser2', 'testuser2@gmail.com', '$2a$10$wv/XZe4bZKa3.kKrASkkuuR9LhFg6wfITsBNnFim2dg2LnYox43wa'),
    (3, 'testuser3', 'testuser3@gmail.com', '$2a$10$mzy9cc9NTubTK2Zo2Ym93elbJNBKCW/ZAiGcfW9kM3MCvakbyHcoW');

INSERT INTO user_roles
    (user_id, role_id) VALUES
    (1,1), (2,1), (3,1);

INSERT INTO user_details
    (id, user_id, height, weight, age, gender, k) VALUES
    (1, 1, 170, 80, 25, 'male', 1.5),
    (2, 2, 180, 100, 32, 'female', 1.1);

INSERT INTO goals
    (id, user_id, man_params_in_use, auto_kcal, auto_protein, auto_fat, auto_carbohydrate, man_kcal, man_protein, man_fat, man_carbohydrate) VALUES
    (1, 1, false, 2763.0, 176.4, 88.2, 315.9, 0.0, 0.0, 0.0, 0.0),
    (2, 2, false, 2330.24, 220.5, 110.25, 114.00, 0.0, 0.0, 0.0, 0.0);

INSERT INTO meals
    (id, user_id, date, kcal, protein, fat, carbohydrate) VALUES
    (1, 1, '2020-01-01', 1000, 25, 11.1, 75.4),
    (2, 2, '2020-01-01', 300, 6, 2, 20.1),
    (3, 1, '2020-01-01', 200, 2, 2, 2),
    (4, 1, '2020-01-02', 100, 1, 1, 1),
    (5, 1, '2020-01-02', 0, 0, 0, 0);

INSERT INTO foods
    (id, meal_id, kcal, protein, fat, carbohydrate, name) VALUES
    (1, 1, 800, 20, 9.1, 75, 'food1'),
    (2, 1, 200, 5, 2, 0.4, 'food2'),
    (3, 2, 100, 2, 1, 10, 'food3'),
    (4, 2, 100, 2, 0, 5, 'food4'),
    (5, 2, 100, 2, 1, 5.1, 'food5'),
--                                                                  this is a food only for deleting
    (1000, 3, 200, 2, 2, 2, 'ham'),
--                                                                  this is a food only for deleting
    (1001, 4, 100, 1, 1, 1, 'egg');

INSERT INTO days
    (id, user_id, date, kcal_consumed, protein_consumed, fat_consumed, carbohydrate_consumed, kcal_to_eat, protein_to_eat, fat_to_eat, carbohydrate_to_eat) VALUES
    (1, 1, '2020-01-01', 1100, 26, 12.1, 76.4, 2763.0, 176.4, 88.2, 315.9),
    (2, 2, '2020-01-01', 300, 6, 2, 20.1, 2330.24, 220.5, 110.25, 113.00),
    (3, 1, '2020-01-02', 100, 2, 2, 2, 2763.0, 176.4, 88.2, 315.9),

INSERT INTO activities
    (id, user_id, date, name, kcal_burnt) VALUES
    (1, 1, '2020-01-01', 'activity1', 123.45),
    (2, 1, '2020-01-01', 'activity2', 100);

